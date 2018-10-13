/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sec_asgn_test_guiapp_v2.pkg0;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author Bingusara
 */
public class politix implements plugin{

    private boolean restartFunc=false;                                          // To identify wheather restart button is pressed
    private boolean reloadFunc=false;                                           // To identify wheather reload button is pressed
    private int totalCount =0;
    private int correctCount =0;
    private int wrongCount =0;
    private int[] results = new int[3];
    

    private Object monitor = new Object();
    
    @Override
    public int[] runQuiz() {
        
        //creating objects from classes which implement question method
        saq sa = new saq();                                                     // This will be later replaced by dynamic class loading.
        mcq mc = new mcq();                                                     // This will be later replaced by dynamic class loading.
 
        
        // Create a short answer question
        question q1 = sa.makeShortAnswerQuestion(
                "How many years does the current president can stay in power?", // Question
                "5" // Answer 
                ,15); // Time Allocated to question in seconds
        
        System.out.println(Thread.currentThread().getName());
         
        // Create a multi-choice question.
        question q2 = mc.makeMultiChoiceQuestion("What is the name of the current president?", // Question of the mcq
                                                  new String[]{"Mahinda", "Sirisena", "Chandrika","Ranil", "Sajith"},  // The Multiple choices
                                                  1,  // Index of correct answer ("Silly mid off")      
                                                  7 ); // Time Allocated to question in seconds
        // Create a multi-choice question.
         question q3 = mc.makeMultiChoiceQuestion("What is the Name of current Ruling Party?", // Question of the mcq
                                                  new String[]{"UNP", "SLFP", "JVP", "BBS", "FP"},  // The Multiple choices
                                                  0,  // Index of correct answer ("Sangakkar")      
                                                  6); // Time Allocated to question in seconds
         
        JFrame pluginFrame = new JFrame("politix");                             // Defining a JFrame to hold the GUI elements
        pluginFrame.setSize(550, 400);                                          // Size of the form (Width px/Height px)
        pluginFrame.setResizable(true);                                         // form can't be resized now
        pluginFrame.setLayout(null);                                            // Layout is set to null for ease of use.
        pluginFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);                    // Closes application on click
        
        JButton restart = new JButton("Restart");                               // Defines a button to restart the quiz
        restart.setBounds(425, 15, 100, 30);                                    // Setting up Restart button's location
        restart.setActionCommand("restartClick");                               // Setting up a name for the action performed by restart button
        pluginFrame.add(restart);                                               // Adding the restart button to the frame
        
        JButton reload = new JButton("Reload");                                 // Defines a button to reload the quiz
        reload.setBounds(425, 55, 100, 30);                                     // Setting up reload button's location
        reload.setActionCommand("reloadClick");                                 // Setting up a name for the action performed by reload button
        pluginFrame.add(reload);                                                // Adding the reload button to the frame

        JLabel nextQuesLbl = new JLabel("Next Question Preview...");            // Just a label to let the user know this is the next question
        nextQuesLbl.setBounds(15, 250, 150, 30);                                // Setting up nextQuesLbl's location
        pluginFrame.add(nextQuesLbl);                                           // Adding nextQuesLbl to the frame
                
        QuestionDisplay qd = new QuestionDisplay(q1,q2,q3,pluginFrame,restart,reload,nextQuesLbl);      // Displaying the Question 
        Thread questionDisplayer = new Thread(qd);                              // Creating new thread to display question in a timed manner
        questionDisplayer.setName("questionDisplayer");                         // Name is set for Debug Purposes
        questionDisplayer.start();                                              // Starting the the question displayer Thread
            
        theEventHandler eventHandler = new theEventHandler(questionDisplayer,pluginFrame,restart,reload,nextQuesLbl);  // Defining the Event Handler
        reload.addActionListener(eventHandler);                                 // Confining events to a single event handler (reload button)
        restart.addActionListener(eventHandler);                                 // Confining events to a single event handler (restart button)

    //    questionDisplayer.interrupt();                                          // Finishing off the questionDisplayer Thread
      
            synchronized(monitor)
            {
                    try {
                        monitor.wait();
                    } catch (InterruptedException ex) {
                        System.out.println("Unexpected error occured for the main thread");
                        Logger.getLogger(politix.class.getName()).log(Level.SEVERE, null, ex);
                    }
                 return results;
            }  

    }

   
    private class QuestionDisplay implements Runnable
    {
        // Defining logic elements in QuestionDisplay class
        List<question> list = new ArrayList<>();                                // A list to hold all the questions
        
        // Defining GUI elements holders
        private JFrame pluginFrame;
        private JButton restart;
        private JButton reload;
        private JLabel nextQuesLbl;
        
        ArrayList<Boolean> answerValidityList =new ArrayList<Boolean>();  
       
        public QuestionDisplay (question Q1,question Q2,question Q3,JFrame PluginFrame, JButton Restart, JButton Reload, JLabel NextQuesLbl)
        { 
            //Adding Questions to the list
            list.add(Q1);
            list.add(Q2);
            list.add(Q3);    
            
            //GUI element holders
            restart = Restart;
            reload = Reload;
            nextQuesLbl = NextQuesLbl;
            pluginFrame = PluginFrame;
        }
        
        @Override
        //questionDisplayer Thread runs this...
        public void run() {
                
            try {      
                for (int i = 0; i < list.size(); i++) 
                {
                    answerValidityList.add(list.get(i).invoke(pluginFrame));    // invoking the question & sending it answer to answerValidityList            
                    pluginFrame.getContentPane().removeAll();                   // Remove all elements from the frame
                    frameRepopulator(pluginFrame,restart,reload,nextQuesLbl);   // Populate the frame again                 
                }
                            
               JOptionPane.showMessageDialog(null, "End of Questions, Exiting Plugin..."); // Once the thread exits the loop (No more questions), A message is given
               pluginFrame.setVisible(false);                                   // Plugin frame's visibility is hidden
               
               totalCount = answerValidityList.size();
               for (int j = 0; j < answerValidityList.size(); j++) 
               {
                   if (answerValidityList.get(j))
                   {                       
                       correctCount++;
                       System.out.println("correctCount "+correctCount);
                   }
                   else
                   {
                       wrongCount++;
                       System.out.println("wrongCount "+wrongCount);
                   }
               }
               
               results[0]=correctCount;
               results[1]=wrongCount;
               results[2]=totalCount;
               
               synchronized(monitor)
               {
                   monitor.notify();
               }
               
            } catch (InterruptedException ex) 
            {      
                 
                if (restartFunc == true)                                        //If the restart button is clicked this flag will be active
                {
       
                  Logger.getLogger(politix.class.getName()).log(Level.SEVERE, null, ex);
                  pluginFrame.getContentPane().removeAll();                     // Removes all elements from the frame
                  frameRepopulator(pluginFrame,restart,reload,nextQuesLbl);     // Repopulates the frame
                  restartFunc = false;                                          // Restting the "restartFunc" flag
                  
                  correctCount=0;
                  wrongCount =0;
                  totalCount = 0;
                  answerValidityList.clear();
                            
                  this.run();                                                   // Running the questionDisplayer thread again
                }
                
                if (reloadFunc == true)                                         //If the reload button is clicked this flag will be active
                {
                  pluginFrame.getContentPane().removeAll();                     // Removes all elements from the frame
                  reloadFunc = false;                                           // Restting the "reloadFunc" flag
                  
                  correctCount=0;
                  wrongCount =0;
                  totalCount = 0;
                  answerValidityList.clear();
        
                  pluginFrame.setVisible(false);                                // Plugin frame's visibility is hidden 
                }
                
                System.out.println("Unknown Interruption occured for questionDisplayer thread");
                Logger.getLogger(saq.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(ex.getCause());
              
            }       
        }
    }

    
// Used to repopulate the frame once everything is cleared    
     private void frameRepopulator (JFrame pluginFrame, JButton restart , JButton reload, JLabel NextQuesLbl)
        {
            // Re-populating the frame
            pluginFrame.add(restart);
            pluginFrame.add(reload);
            pluginFrame.add(NextQuesLbl);
        }
    
     private class theEventHandler implements ActionListener {

         // QuestionSetter thread holder
         private Thread questionSetter;
         
         // Holders for GUI elements
         private JFrame pluginFrame;
         private JButton restart;
         private JButton reload;
         private JLabel nextQuesLbl;
         
         public theEventHandler( Thread QuestionSetter,JFrame PluginFrame,JButton Restart,JButton Reload, JLabel NextQuesLbl) 
         {    
             questionSetter = QuestionSetter;
             nextQuesLbl = NextQuesLbl;
             reload = Reload;
             restart = Restart;
             pluginFrame = PluginFrame;
         }

        @Override
        public void actionPerformed(ActionEvent e){

            if (e.getActionCommand().equals("restartClick"))                    // checks if restart button is clicked
            {   
                 JOptionPane.showMessageDialog(null, "Restarting the quiz..."); // A message given to the user  
                 restartFunc = true;                                            // Setting the flag of restartFunc
                 questionSetter.interrupt();                                    // Interrupting questionSetter thread  
            }
            if (e.getActionCommand().equals("reloadClick"))                     // checks if reload button is clicked
            {
                JOptionPane.showMessageDialog(null, "Reload another quiz...");  // A message given to the user  
                reloadFunc = true;                                              // Setting the flag of reloadFunc
                questionSetter.interrupt();                                     // Interrupting questionSetter thread
            }
        }
    }    
}
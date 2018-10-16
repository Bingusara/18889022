/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package quizapplication;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
public class Politix implements Quiz{

    private boolean restartFunc=false;                                          // To identify wheather restart button is pressed
    private boolean reloadFunc=false;                                           // To identify wheather reload button is pressed
    private int totalCount =0;							// To store the total count of questions
    private int correctCount =0;						// To store the correct count of questions
    private int wrongCount =0;							// To store the wrong count of questions
    private int[] results = new int[3];						// To put all the above integer variables and pass it to the main application
    

    private Object monitor = new Object();
    
    @Override
    public int[] runQuiz(Object questionLoader) {
        
        //creating objects from classes which implement question method
        try {
        
            String mcqClassName = "MCQ";
            String saqClassName = "SAQ";

            // These 3 parts load the pluginloader class's loadQuestionPlugin by reflection. It uses the plugin loader object sent by the main class
            Object saqObj1 = new Object();
            saqObj1 = questionLoader.getClass().getMethod("loadQuestionPlugin",String.class).invoke(questionLoader, saqClassName);
          
            Object mcqObj1 = new Object();
            mcqObj1 = questionLoader.getClass().getMethod("loadQuestionPlugin",String.class).invoke(questionLoader, mcqClassName);

            Object mcqObj2 = new Object();
            mcqObj2 = questionLoader.getClass().getMethod("loadQuestionPlugin",String.class).invoke(questionLoader, mcqClassName);

            
            // Create a short answer question's data & add it to a object array
            Object[] saq1ArgsArr = new Object[3];                               // It can be intialized as 3 because the plugin writer knows how many arguments are taken in by makeShortAnswerQuestion method
            String saq1QuestionArg = "How many years does the current president can stay in power?";  // Defining the question
            String saq1AnswerArg = "5";                                         // Defining the Answer
            Integer saq1AllocatedTime =  new Integer(15);                       // Allocating a time period to enter the answer
            saq1ArgsArr[0] = saq1QuestionArg;                                   // The first argument is the question, so its in the 0th index in the argument array
            saq1ArgsArr[1] = saq1AnswerArg;                                     // The second argument is the Answer, so its in the 1st index in the argument array
            saq1ArgsArr[2] = saq1AllocatedTime;                                 // The third argument is the Time allocated, so its in the 2nd index in the argument array
            
            Method makeShortAnswerQuestion = saqObj1.getClass().getMethod("makeShortAnswerQuestion",String.class,String.class, Integer.TYPE);  
            Question q1 = (Question) makeShortAnswerQuestion.invoke(saqObj1, saq1ArgsArr); 

            // Create a multiple choice question's data & add it to a object array     
            Object[] mcq1ArgsArr = new Object[4];                               // It can be intialized as 4 because the plugin writer knows how many arguments are taken in by makeMultiChoiceQuestion method
            String mcq1QuestionArg = "What is the name of the current president?";  // Defining the question
            String[] mcq1ChoicesArg = {"Mahinda", "Sirisena", "Chandrika","Ranil", "Sajith"};   // Defining the Choices for the mcq
            Integer mcq1AnswerArg =  new Integer(1);                           // Defining the correct choice index
            Integer mcq1AllocatedTime =  new Integer(7);                        // Allocating a time period to enter the answer
            mcq1ArgsArr[0] = mcq1QuestionArg;                                   // The first argument is the question, so its in the 0th index in the argument array
            mcq1ArgsArr[1] = mcq1ChoicesArg;                                    // The second argument is the choices array, so its in the 1st index in the argument array
            mcq1ArgsArr[2] = mcq1AnswerArg;                                     // The third argument is the Answer index, so its in the 2nd index in the argument array
            mcq1ArgsArr[3] = mcq1AllocatedTime;                                 // The fourth argument is the time allocated, so its in the 3nd index in the argument array
            
            Method makeMultiChoiceQuestion = mcqObj1.getClass().getMethod("makeMultiChoiceQuestion",new Class[]{String.class,String[].class, Integer.TYPE,Integer.TYPE});  
            Question q2 = (Question) makeMultiChoiceQuestion.invoke(mcqObj1, mcq1ArgsArr); 
            
            
            // Create a multiple choice question's data & add it to a object array     
            Object[] mcq2ArgsArr = new Object[4];                               // It can be intialized as 4 because the plugin writer knows how many arguments are taken in by makeMultiChoiceQuestion method
            String mcq2QuestionArg = "What is the Name of current Ruling Party?";  // Defining the question
            String[] mcq2ChoicesArg = {"UNP", "SLFP", "JVP", "BBS", "FP"};      // Defining the Choices for the mcq
            Integer mcq2AnswerArg =  new Integer(0);                            // Defining the correct choice index
            Integer mcq2AllocatedTime =  new Integer(7);                        // Allocating a time period to enter the answer
            mcq2ArgsArr[0] = mcq2QuestionArg;                                   // The first argument is the question, so its in the 0th index in the argument array
            mcq2ArgsArr[1] = mcq2ChoicesArg;                                    // The second argument is the choices array, so its in the 1st index in the argument array
            mcq2ArgsArr[2] = mcq2AnswerArg;                                     // The third argument is the Answer index, so its in the 2nd index in the argument array
            mcq2ArgsArr[3] = mcq2AllocatedTime;                                 // The fourth argument is the time allocated, so its in the 3nd index in the argument array
          
            Question q3 = (Question) makeMultiChoiceQuestion.invoke(mcqObj2, mcq2ArgsArr); 
         
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
        
        JLabel nextQues = new JLabel();                                         // A label show next question
        nextQues.setBounds(15, 300, 350, 30);                                   // Setting up nextQues's location
        pluginFrame.add(nextQues);                                              // Adding nextQues to the frame

        JLabel quesNo = new JLabel();                                           // A label show question no
        quesNo.setBounds(5, 10, 10, 30);                                        // Setting up question no's location
        pluginFrame.add(quesNo);                                                // Adding question no to the frame
                
        
        QuestionDisplay qd = new QuestionDisplay(q1,q2,q3,pluginFrame,restart,reload,nextQuesLbl, nextQues,quesNo);      // Displaying the Question 
        Thread questionDisplayer = new Thread(qd);                              // Creating new thread to display question in a timed manner
        questionDisplayer.setName("questionDisplayer");                         // Name is set for Debug Purposes
        questionDisplayer.start();                                              // Starting the the question displayer Thread
            
        theEventHandler eventHandler = new theEventHandler(questionDisplayer,pluginFrame,restart,reload,nextQuesLbl);  // Defining the Event Handler
        reload.addActionListener(eventHandler);                                 // Confining events to a single event handler (reload button)
        restart.addActionListener(eventHandler);                                // Confining events to a single event handler (restart button)

   
    
    } catch (NoSuchMethodException ex) {
        // Error Code 001
        JOptionPane.showMessageDialog(null, "Sorry this plugin encountered an Internal Error, Please contact the Developer! \n Error Code: 001"); 
        Logger.getLogger(Politix.class.getName()).log(Level.SEVERE, null, ex);
    } catch (SecurityException ex) {
        // Error Code 002
        JOptionPane.showMessageDialog(null, "Sorry this plugin encountered an Internal Error, Please contact the Developer! \n Error Code: 002");
        Logger.getLogger(Politix.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
        // Error Code 003
        JOptionPane.showMessageDialog(null, "Sorry this plugin encountered an Internal Error, Please contact the Developer! \n Error Code: 003");
        Logger.getLogger(Politix.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IllegalArgumentException ex) {
        // Error Code 004
        JOptionPane.showMessageDialog(null, "Sorry this plugin encountered an Internal Error, Please contact the Developer!\n Error Code: 004");
        Logger.getLogger(Politix.class.getName()).log(Level.SEVERE, null, ex);
    } catch (InvocationTargetException ex) {
        // Error Code 005
        JOptionPane.showMessageDialog(null, "Sorry this plugin encountered an Internal Error, Please contact the Developer!\n Error Code: 005");        
        Logger.getLogger(Politix.class.getName()).log(Level.SEVERE, null, ex);
    }

        synchronized(monitor)
        {
                try {
                    monitor.wait();
                } catch (InterruptedException ex) {
                    // Error Code 006
                    JOptionPane.showMessageDialog(null, "Sorry this plugin encountered an Internal Error, Please contact the Developer!\n Error Code: 006");        
                    System.out.println("Unexpected error occured for the main thread");
                    Logger.getLogger(Politix.class.getName()).log(Level.SEVERE, null, ex);
                }
             return results;
        }   
    }

   
    private class QuestionDisplay implements Runnable
    {
        // Defining logic elements in QuestionDisplay class
        List<Question> list = new ArrayList<>();                                // A list to hold all the questions
        
        // Defining GUI elements holders
        private JFrame pluginFrame;
        private JButton restart;
        private JButton reload;
        private JLabel nextQuesLbl;
        private JLabel nextQues;
        private JLabel quesNo;
        
        ArrayList<Boolean> answerValidityList =new ArrayList<Boolean>();  
       
        public QuestionDisplay (Question Q1,Question Q2,Question Q3,JFrame PluginFrame, JButton Restart, JButton Reload, JLabel NextQuesLbl,JLabel NextQues, JLabel QuesNo)
        { 
            //Adding Questions to the list
            list.add(Q1);
            list.add(Q2);
            list.add(Q3);    
            
            //GUI element holders
            restart = Restart;
            reload = Reload;
            nextQuesLbl = NextQuesLbl;
            nextQues = NextQues;
            quesNo = QuesNo;
            pluginFrame = PluginFrame;
            
        }
        
        @Override
        //questionDisplayer Thread runs this...
        public void run() {
                
            try {      
                for (int i = 0; i < list.size(); i++) 
                {
                    if((i+1)<list.size())
                    {
                        nextQues.setText(list.get(i+1).getQuestion());          // Displays the next question
                    }
                    else
                    {
                        nextQues.setText("No Further Questions...");                    
                    }
                    quesNo.setText(String.valueOf(i+1)+".");                    // Displays the no of question

                    answerValidityList.add(list.get(i).invoke(pluginFrame));    // invoking the question & sending it answer to answerValidityList            

                    pluginFrame.getContentPane().removeAll();                   // Remove all elements from the frame
                    frameRepopulator(pluginFrame,restart,reload,nextQuesLbl,nextQues,quesNo);   // Populate the frame again                 
                }
                            
               JOptionPane.showMessageDialog(null, "End of Questions, Exiting Plugin..."); // Once the thread exits the loop (No more questions), A message is given
               pluginFrame.setVisible(false);                                   // Plugin frame's visibility is hidden
               
               // Getting a the no of correct/wrong and total questions
               totalCount = answerValidityList.size();
               for (int j = 0; j < answerValidityList.size(); j++) 
               {
                   if (answerValidityList.get(j))
                   {                       
                       correctCount++;     
                   }
                   else
                   {
                       wrongCount++;
                   }
               }
               
               // Setting up the results array
               results[0]=correctCount;                                     
               results[1]=wrongCount;
               results[2]=totalCount;
               
               synchronized(monitor)
               {
                   monitor.notify();                                            // Informs the classHandler Thread
               }
               
            } catch (InterruptedException ex) 
            {      
                 
                if (restartFunc == true)                                        //If the restart button is clicked this flag will be active
                {
                  System.out.println("Restarting Exception");
                  Logger.getLogger(Politix.class.getName()).log(Level.SEVERE, null, ex);
                  pluginFrame.getContentPane().removeAll();                     // Removes all elements from the frame
                  frameRepopulator(pluginFrame,restart,reload,nextQuesLbl,nextQues,quesNo);     // Repopulates the frame
                  restartFunc = false;                                          // Restting the "restartFunc" flag
                  
                  correctCount=0;                                               // Reset the correctCount
                  wrongCount =0;                                                // Reset the wrongCount
                  totalCount = 0;                                               // Reset the totalCount
                  answerValidityList.clear();                                   // Reset the answerValidity List 
                            
                  this.run();                                                   // Running the questionDisplayer thread again
                }
                
                if (reloadFunc == true)                                         //If the reload button is clicked this flag will be active
                {
                  System.out.println("Reloading Exception");
                  Logger.getLogger(Politix.class.getName()).log(Level.SEVERE, null, ex);
                  pluginFrame.getContentPane().removeAll();                     // Removes all elements from the frame
                  reloadFunc = false;                                           // Restting the "reloadFunc" flag
                  
                  correctCount=0;                                               // Reset the correctCount
                  wrongCount =0;                                                // Reset the wrongCount
                  totalCount = 0;                                               // Reset the totalCount
                  answerValidityList.clear();                                   // Reset the answerValidity List 
                                    
                  pluginFrame.setVisible(false);                                // Plugin frame's visibility is hidden 
                }
                
                // Error Code 007        
                System.out.println("Error code  : Unknown Interruption occured for questionDisplayer thread");
                Logger.getLogger(Politix.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(ex.getCause());
            }       
        }
    }

    
// Used to repopulate the frame once everything is cleared    
     private void frameRepopulator (JFrame pluginFrame, JButton restart , JButton reload, JLabel NextQuesLbl,JLabel NextQues, JLabel QuesNo)
     {
            // Re-populating the frame
            pluginFrame.add(restart);
            pluginFrame.add(reload);
            pluginFrame.add(NextQuesLbl);
            pluginFrame.add(NextQues);
            pluginFrame.add(QuesNo);
     }
    
     private class theEventHandler implements ActionListener {

         // QuestionSetter thread holder
         private Thread questionDisplayer;
         
         // Holders for GUI elements
         private JFrame pluginFrame;
         private JButton restart;
         private JButton reload;
         private JLabel nextQuesLbl;
         
         public theEventHandler( Thread QuestionDisplayer,JFrame PluginFrame,JButton Restart,JButton Reload, JLabel NextQuesLbl) 
         {    
             questionDisplayer = QuestionDisplayer;
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
                 questionDisplayer.interrupt();                                 // Interrupting questionSetter thread  
            }
            if (e.getActionCommand().equals("reloadClick"))                     // checks if reload button is clicked
            {
                JOptionPane.showMessageDialog(null, "Reload another quiz...");  // A message given to the user  
                reloadFunc = true;                                              // Setting the flag of reloadFunc
                questionDisplayer.interrupt();                                  // Interrupting questionSetter thread
            }
        }
    }    
}
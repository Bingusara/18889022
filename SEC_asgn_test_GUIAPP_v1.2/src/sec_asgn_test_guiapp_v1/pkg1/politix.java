/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sec_asgn_test_guiapp_v1.pkg1;



import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Bingusara
 */
public class politix implements plugin{

    boolean loader = false;
    
    @Override
    public void runQuiz() {
        
        saq sa = new saq();
        mcq mc = new mcq();
        
        // Create a short answer question
        question q1 = sa.makeShortAnswerQuestion(
                "How many overs in a one day match", // Question
                "50"); // Answer
        
        
        // Create a multi-choice question.
        question q2 = mc.makeMultiChoiceQuestion("Which of the following is NOT a valid fielding position?", // Question of the mcq
                                                  new String[]{"Silly mid off", "Short leg", "Catcher","Backward point", "Cow corner"},  // The Multiple choices
                                                  0);  // Index of correct answer ("Silly mid off")      
        
        // Create a multi-choice question.
         question q3 = mc.makeMultiChoiceQuestion("What is the Name of Best Sri Lankan Captain?", // Question of the mcq
                                                  new String[]{"Sangakkar", "Arjuna", "Sanath","Murali", "Mahela"},  // The Multiple choices
                                                  0);  // Index of correct answer ("Sangakkar")      
        
        
        JFrame pluginFrame = new JFrame();       // Defining a JFrame to hold the GUI elements
        pluginFrame.setSize(450,300);            // Size of the form (Width px/Height px)
        pluginFrame.setResizable(true);          // form can't be resized now
        pluginFrame.setLayout(null);             // Layout is set to null for ease of use.

        QuestionDisplay qd = new QuestionDisplay(q1,q2,q3,pluginFrame);         // Displaying the Question 
        Thread questionDisplayer = new Thread(qd);                              // Creating new thread to display question in a timed manner
        
        questionDisplayer.start();                                              // Starting the the question displayer Thread
    }

    private class QuestionDisplay implements Runnable
    {
       
        List<question> list = new ArrayList<>();
        private JFrame pluginFrame;
        
        public QuestionDisplay (question Q1,question Q2,question Q3 ,JFrame PluginFrame)
        { 
            list.add(Q1);
            list.add(Q2);
            list.add(Q3);
            pluginFrame = PluginFrame;
        }
        
        @Override
        public void run() {
                
            try {      
    
                    for (int i = 0; i < list.size(); i++) 
                    {
                        list.get(i).invoke(pluginFrame);                            // Setting up the 1st question
                        pluginFrame.revalidate();
                        pluginFrame.repaint();                                      // Displaying the 2nd question after clearing off everyting that was on the frame previously
                        pluginFrame.setVisible(true);
                        Thread.sleep(5000);                                         //  Timer to user to enter the answer
                        pluginFrame.getContentPane().removeAll();                   // Remove all elements from the frame
                    }
                
            JOptionPane.showMessageDialog(null, "End of Questions, Exiting Plugin...");
            pluginFrame.setVisible(false);


            } catch (InterruptedException ex) {                
                JOptionPane.showMessageDialog(null, "questionDisplayer Thread got interrupted");
                Logger.getLogger(politix.class.getName()).log(Level.SEVERE, null, ex);
            }       
        }
    }
}
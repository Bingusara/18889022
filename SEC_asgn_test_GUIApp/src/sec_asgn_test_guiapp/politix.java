/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sec_asgn_test_guiapp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 *
 * @author Bingusara
 */
public class politix implements plugin {
    
    
    // This plugin has politix related questions.
    
    //constructor
    public politix()
    {
      
    }
    
    mcq All_MCQ[] = new mcq[2];                                                     // Defining no of mcq objects in the All_MCQ array

    String[][] choises = {{"1.Mahinda", "2.Maithree", "3.Ranil", "4.Chandrika"},    // A 2D string to store the choises for each question
                          {"1.225", "2.200", "3.125", "4.175"}};
    
    String[] questions = {"Whats the name of the president in Sri lanka?",          // A String array to store the questions
                            "Whats the no of parliment seats in S.L.?"};
    
    int[] answers =  {1,0};                                                         // An int array to store the answer. (index)
    
    ButtonGroup BG_radioButtons = new ButtonGroup();                                // A button group to group the Radio Buttons
        
    @Override
    public void invoke() {
        
        JFrame politixJF = new JFrame();       
        politixJF.setSize(475,200);           // Size of the form
        politixJF.setResizable(false);        // form can't be resized now 
        politixJF.setLayout(null);            // Setting up the Layout
        
        
        // Setting up the elements in the JFrame

        JButton submit = new JButton("Submit");                         // The button to once the user has selected a choise
        submit.setBounds(325, 110, 100, 30);
        submit.setActionCommand("Submited");
        
        JLabel questionLbl = new JLabel("");                            // label to store the question
        questionLbl.setBounds(20,10,370,30);

       
        JRadioButton[] RButtons = new JRadioButton[choises[(choises.length)-1].length];
  
        //Adding all the elements to the JFrame
        politixJF.add(submit);
        politixJF.add(questionLbl);     
        
        // Setting up an event handler for user's inputs
        theEventHandler eventHandler = new theEventHandler(questionLbl, politixJF, RButtons ); 
          
        questionSetter(0,questionLbl,politixJF,RButtons);               // The intial question is setup here, This is due to a problem in Radio Button text update.
        politixJF.setVisible(true);
        submit.addActionListener(eventHandler);
         
    }
    
    private void questionSetter (int noOfQues,JLabel questionLbl, JFrame politixJF, JRadioButton[] RButtons)
    {
        
            All_MCQ[noOfQues] = new mcq();                              // Instantiating the mcq objects
    
            All_MCQ[noOfQues].setQuestion(questions[noOfQues]);         // Setting up the questions the mcq object
                questionLbl.setText(All_MCQ[noOfQues].getQuestion());   // Passing it to the JLabel
    
            All_MCQ[noOfQues].setChoises(choises[noOfQues]);            // Setting up the choises in the mcq object, Note the 2D Array
                

                if(noOfQues==0)
                {
                    for (int choiseNo=0; choiseNo<choises[noOfQues].length; choiseNo++)
                    {                
                        RButtons[choiseNo] = new JRadioButton();                        // Instantiating Radio Buttons                                      
                        RButtons[choiseNo].setText(choises[noOfQues][choiseNo]);       // Setting up text for the Radio Buttons
                        BG_radioButtons.add(RButtons[choiseNo]);                       // Assigning the nutton group for the Radio Buttons
                        RButtons[choiseNo].setBounds(20,40+(choiseNo*20),300,30);      // Setting up the location of Radio Buttons
                        politixJF.add(RButtons[choiseNo]);                             // Adding the Radio Buttons to the JFrame                    
                    }
                }
                else                                                                    // After the intial creation of Radio buttons im simply using the Button group to make changes to the radio buttons
                {
                    int choiseNo =0;
                    for (Enumeration<AbstractButton> RButton = BG_radioButtons.getElements(); RButton.hasMoreElements();)       // Found though a stack overflow link, its included in references 
                    {
                        AbstractButton button = RButton.nextElement();                    // Found though a stack overflow link, its included in references  
                        button.setText(choises[noOfQues][choiseNo]);                      // Setting the text for the next itration of choises
                        BG_radioButtons.clearSelection();                                 // Clearing the selection of radio buttons
                        choiseNo++;
                    }  
                }
                
            All_MCQ[noOfQues].setAnswer(answers[noOfQues]);                              // Setting up the answers for the MCQ
    }
    
    
    private class theEventHandler implements ActionListener{

        
        // Passing the elements from the upper class to theEventHandler class
        private JLabel questionLbl;
        private JFrame politixJF;
        private JRadioButton[] RButtons;
       
        // These are required for the algorithm in this class
        private int selected_choise = 0;
        private boolean BG_RButton_Checked = false;    
        private int question_counter = 0;
        
        // The constructor accepts the elements passed from the above class
        public theEventHandler(JLabel QuestionLbl,JFrame PolitixJF,JRadioButton[] rButtons )
        {
           questionLbl=  QuestionLbl;
           politixJF = PolitixJF;
           RButtons = Arrays.copyOf(rButtons, rButtons.length);             
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
   
                 BG_RButton_Checked =false;                                                 // Keeping track whether a Radio button is selected or not
       
                for (Enumeration<AbstractButton> RButton = BG_radioButtons.getElements(); RButton.hasMoreElements();) 
                {
                    AbstractButton button = RButton.nextElement();

                    // If a Radio button is selected, here it checks whether the answer is right or wrong
                    if (button.isSelected()) 
                    {
                         JOptionPane.showMessageDialog(null, "You selected "+button.getText());             // prompting user of his/her selection     
                         
                         selected_choise = All_MCQ[question_counter].getAnswer();                           // copying the correct answer
       
                         if(button.getText().equals(choises[question_counter][selected_choise]))            // Cheking the answer
                         {
                             JOptionPane.showMessageDialog(null, "Your Answer is correct");                                                          
                         }
                         else if (!button.getText().equals(choises[question_counter][selected_choise]))
                         {
                             JOptionPane.showMessageDialog(null, "Your Answer is wrong");
                         }
                         
                         BG_RButton_Checked = true;                                                         // Keeping track whether a Radio button is selected or not
                    }
                }
                
                if(!BG_RButton_Checked)
                {
                   JOptionPane.showMessageDialog(null, "No Button is selected, Its considered wrong");
                }

                // calling the question setter and setting up the rest of the questions
                if (question_counter+1 < All_MCQ.length)
                {
                    questionSetter((question_counter+1),questionLbl,politixJF,RButtons);
                    politixJF.setVisible(true);
                    question_counter++;
                }
                else                                                                            // closiing  the plugin when question are over
                {
                    JOptionPane.showMessageDialog(null, "End of Questions, Exiting Plugin...");  
                    politixJF.setVisible(false);      
                } 
        }
    }
    
    
    
}



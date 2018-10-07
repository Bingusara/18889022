/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sec_asgn_test_guiapp_v1.pkg1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Bingusara
 */
public class saq implements question{
    
    // saq object data holders
    private String question;
    private String ans;
    
    // GUI elements
    private JFrame pluginFrame;
     
    public saq() {

    }
    
    // Returning an saq object with question interface with the details
    public question makeShortAnswerQuestion(String Question, String Answer)
    {
        saq sa = new saq();    
        sa.question = Question;
        sa.ans = Answer;
        return sa;
    }

    @Override
    public void invoke(JFrame PluginFrame) 
    {
        // Setting up GUI elements
        JLabel questionLabel = new JLabel();
        questionLabel.setText(question);
        questionLabel.setBounds(10, 10, 250, 30);

        JTextField ansTF = new JTextField();
        ansTF.setBounds(100, 100, 200, 30);
        ansTF.setActionCommand("ansTF_Entered");
        
        JButton submit = new JButton ("Submit");
        submit.setBounds(200, 200, 100, 30);
        submit.setActionCommand("submitClicked");
        submit.setEnabled(false);
        
        pluginFrame = PluginFrame;

        pluginFrame.add(ansTF);
        pluginFrame.add(questionLabel);
        pluginFrame.add(submit);
        
        // Setting up the event handler for the submit button an the answer text box
        theEventHandler eventHandler = new theEventHandler(questionLabel, ansTF,submit); 
        submit.addActionListener(eventHandler);
        ansTF.addActionListener(eventHandler);
   
    }
    
    private class theEventHandler implements ActionListener{

        // Passing the elements from the upper class to theEventHandler class
        private JLabel questionLbl;
        private JTextField ansLabel;
        private JButton submit;
       
        public theEventHandler( JLabel QuestionLabel, JTextField AnsLabel, JButton Submit)
        {
           questionLbl=  QuestionLabel;
           ansLabel = AnsLabel;
            submit= Submit;
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
   
            if(e.getActionCommand().equals("submitClicked"))                    // checking what event was fired
            {
                if(ansLabel.getText().equals(ans))                              // checking the answer
                {
                    JOptionPane.showMessageDialog(null, "Your Answer is correct");

                }
                else
                {
                   JOptionPane.showMessageDialog(null, "Your Answer is wrong");   
                }
            }
            
            if(e.getActionCommand().equals("ansTF_Entered"))                    // Enabling the submit button after user enters some text in the answer textarea.
            {
                submit.setEnabled(true);
            }
            else
            {           
             submit.setVisible(true);
            }
        }
    }
}

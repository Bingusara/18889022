/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sec_asgn_test_guiapp_v1.pkg1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Enumeration;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

/**
 *
 * @author Bingusara
 */
public class mcq implements question {

    // Data holders for the mcq object
    private String question;
    private String[] choices;
    private int ans;
    
    // GUI related elements
    private JLabel questionLabel = new JLabel();
//    private JButton submit = new JButton("Submit");
    private ButtonGroup BG_radioButtons = new ButtonGroup();    

 
    
    public mcq() {

    }

    // Returning an mcq object with question interface with the details
    public question makeMultiChoiceQuestion(String Question, String[] Choises, int Answer) {
        mcq mc = new mcq();
        mc.question = Question;
        mc.choices = Arrays.copyOf(Choises, Choises.length);
        mc.ans = Answer;
        return mc;
    }
    
    @Override
    public void invoke(JFrame PluginFrame)
    {
        questionLabel.setText(question);                                        //Setting up the question label with the question
        questionLabel.setBounds(10, 10, 250, 30);                               // positioning the question label
        PluginFrame.add(questionLabel);                                         // Adding the question label to the frame

        JRadioButton[] RButtons = new JRadioButton[choices.length];             // Defining radio buttons, They are instantiated later

        JButton Submit = new JButton("Submit");
        Submit.setBounds(200, 200, 100, 30);
        Submit.setActionCommand("submitClicked");                               // Setting up name to the action for the "submit" button, since we have a seperate Event Handle class
        Submit.setEnabled(false);                                               // Initially the button is grey, since no answer is selected
        PluginFrame.add(Submit);

        theEventHandler eventHandler = new theEventHandler(Submit);             // Defining the Event Handler
        Submit.addActionListener(eventHandler);                                 // Confining events to a single event handler
     
        for (int choiceNo = 0; choiceNo < choices.length; choiceNo++) 
        {
            RButtons[choiceNo] = new JRadioButton();                              // Instantiating Radio Buttons                                      
            RButtons[choiceNo].setText(choices[choiceNo]);                        // Setting up text for the Radio Buttons
            BG_radioButtons.add(RButtons[choiceNo]);                              // Assigning the bnutton group for the Radio Buttons
            RButtons[choiceNo].setBounds(20, 40 + (choiceNo * 25), 300, 30);      // Setting up the location of Radio Buttons
            RButtons[choiceNo].setActionCommand("radioSubmitted");                // Setting up action listner for the radio button
            RButtons[choiceNo].addActionListener(eventHandler);

            PluginFrame.add(RButtons[choiceNo]);
        }

      
    }
    
    
    private class theEventHandler implements ActionListener {

        private JButton submit;
        
        public theEventHandler(JButton Submit) {
            
            submit = Submit;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getActionCommand().equals("submitClicked"))                   // checks submit button is clicked
            {
                // The following code segement was found in stack overflow, Its in the references.
                // It allows to retirve the radio button data through  the button group
                for (Enumeration<AbstractButton> RButton = BG_radioButtons.getElements(); RButton.hasMoreElements();) 
                {
                    AbstractButton Rbutton = RButton.nextElement();
                    
                    // Checking wheather button is selected
                    if (Rbutton.isSelected()) 
                    {
                        if (Rbutton.getText().equals(choices[ans]))             // Checking for the correct answer
                        {
                            JOptionPane.showMessageDialog(null, "Your Answer is correct");
                        } else 
                        {
                            JOptionPane.showMessageDialog(null, "Your Answer is wrong");
                        }
                    }
                }
            }

            if (e.getActionCommand().equals("radioSubmitted"))                  // Checking wheather radio button was clicked
            {
                submit.setEnabled(true);                                        // Enabling the submit button
            }
        }
    }
}

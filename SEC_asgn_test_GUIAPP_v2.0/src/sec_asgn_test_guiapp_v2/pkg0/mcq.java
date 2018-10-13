/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sec_asgn_test_guiapp_v2.pkg0;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private int qTime;
    private Object monitor = new Object();
    
    // GUI related elements
    private JLabel questionLabel = new JLabel();                                // A Label to hold the question
    private ButtonGroup BG_radioButtons = new ButtonGroup();                    // Button group to gold the JRadio Buttons

    // Logic elements
    private boolean answered = false;                                           // A flag to store whether user has answered
    private boolean answerValidity = false;                                     // A flag to store whether the answer is correct
    
    public mcq() {

    }

    // Returning an mcq object with question interface with the details
    public question makeMultiChoiceQuestion(String Question, String[] Choises, int Answer, int QTime) {
        mcq mc = new mcq();
        mc.question = Question;
        mc.choices = Arrays.copyOf(Choises, Choises.length);
        mc.ans = Answer;
        mc.qTime=QTime;
        return mc;
    }
    
    @Override
    public boolean invoke(JFrame PluginFrame) throws InterruptedException
    {
        questionLabel.setText(question);                                        //Setting up the question label with the question
        questionLabel.setBounds(10, 10, 350, 30);                               // positioning the question label
        PluginFrame.add(questionLabel);                                         // Adding the question label to the frame

        JRadioButton[] RButtons = new JRadioButton[choices.length];             // Defining radio buttons, They are instantiated later

        JButton Submit = new JButton("Submit");                                 // Defining a Submit button to submit the answer to the question
        Submit.setBounds(425, 200, 100, 30);                                    // positioning the submit button
        Submit.setActionCommand("submitClicked");                               // Setting up name to the action for the "submit" button, since we have a seperate Event Handle class
        Submit.setEnabled(false);                                               // Initially the button is greyed out, since no answer is selected
        PluginFrame.add(Submit);

        theEventHandler eventHandler = new theEventHandler(Submit);             // Defining the Event Handler
        Submit.addActionListener(eventHandler);                                 // Confining events to a single event handler
     
        for (int choiceNo = 0; choiceNo < choices.length; choiceNo++) 
        {
            RButtons[choiceNo] = new JRadioButton();                            // Instantiating Radio Buttons                                      
            RButtons[choiceNo].setText(choices[choiceNo]);                      // Setting up text for the Radio Buttons
            BG_radioButtons.add(RButtons[choiceNo]);                            // Assigning the bnutton group for the Radio Buttons
            RButtons[choiceNo].setBounds(20, 40 + (choiceNo * 25), 300, 30);    // Setting up the location of Radio Buttons
            RButtons[choiceNo].setActionCommand("radioSubmitted");              // Setting up action listner for the radio button
            RButtons[choiceNo].addActionListener(eventHandler);                 // Adding an action listner to the radio buttons  

            PluginFrame.add(RButtons[choiceNo]);                                // Adding the radio button to the plufing frame
        }

        
        // Defining a and setting up a label to show the "Remaining Time"
        JLabel timerNoteLbl = new JLabel("Remaining Time : ");
        timerNoteLbl.setBounds(400, 100, 100, 30);
        PluginFrame.add(timerNoteLbl);       

        // Defining a and setting up a label to actualy show the remaining time        
        JLabel timerLbl = new JLabel();
        timerLbl.setBounds(500, 100, 100, 30);
        PluginFrame.add(timerLbl);       

        // A thread is created to show the count down of the remaining time
        TimerClass tc = new TimerClass(PluginFrame,timerLbl);
        Thread timer = new Thread(tc);                              
        timer.start(); 
        
        PluginFrame.revalidate();                                               // Its important to revalidate whats in the frame, because we dont know wheather this is the first time or a latter time
        PluginFrame.repaint();                                                  // Same comment as above line
        PluginFrame.setVisible(true);                                           // Showing the frame to the user
      
        
        // A monitor is used to wait for the user's answer
        synchronized(monitor)
        {  
            monitor.wait((qTime+1)*1000);                                       // Monitor expires when the count down expires
        }
        if(!answered)                                                           // A flag is used to check wheather the user actually answerd the question
        {
             if (answerValidity) {
                JOptionPane.showMessageDialog(null, "Your Answer is correct & Time out"); // Time out notice if the user didnt answer in time, selected answer is verified here
             } else {
                JOptionPane.showMessageDialog(null, "Your Answer is wrong & Time out");  // Time out notice if the user didnt answer in time, selected answer is verified here. If there's no answer its still wrong.
             }
        }

        return answerValidity;                                                  // Return a boolean notifying whether the answer for this quesiton is right or wrong
    
    }
    

// This is where the timer thread's Run() method is defined , it just sleeps for 1 second and updates the current time    
    private class TimerClass implements Runnable
    {
        private JFrame pluginFrame;
        private JLabel timerLbl;
        
        public TimerClass (JFrame PluginFrame,JLabel TimerLbl)
        { 
            pluginFrame = PluginFrame;
            timerLbl = TimerLbl;
        }
        
        @Override
        public void run() {
            for (int i=qTime;i>=0;i--)
            {
                timerLbl.setText(String.valueOf(i));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(saq.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    
    // This is where all the actions are handled.
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
                for (Enumeration<AbstractButton> RButton = BG_radioButtons.getElements(); RButton.hasMoreElements();)   // Obtained from stack overflow, Its in Refereces
                {
                    AbstractButton Rbutton = RButton.nextElement();             // Obtained from stack overflow, Its in Refereces
                    
                    // Checking wheather button is selected
                    if (Rbutton.isSelected()) 
                    {
                        if (Rbutton.getText().equals(choices[ans]))             
                        {
                            JOptionPane.showMessageDialog(null, "Your Answer is correct");
                            answered = true;                                    // Has the user provided an answer? Yes
                            answerValidity = true;                              // Is the answer correct? Yes
                            synchronized (monitor) {   
                                monitor.notify();                               // Asking the questionDisplayer Thread to stop waiting
                            }
                            
                        } else
                        {
                            JOptionPane.showMessageDialog(null, "Your Answer is wrong");
                            answered = true;                                    // Has the user provided an answer? Yes
                            answerValidity = false;                             // Is the answer correct? Yes
                             synchronized (monitor) {
                                monitor.notify();                               // Asking the questionDisplayer Thread to stop waiting
                            }
                            
                        }
                    }
                }
            }

            if (e.getActionCommand().equals("radioSubmitted"))                  // Checking wheather radio button was clicked
            {
                submit.setEnabled(true);                                        // Enabling the submit button
                
                for (Enumeration<AbstractButton> RButton = BG_radioButtons.getElements(); RButton.hasMoreElements();) 
                {
                    AbstractButton Rbutton = RButton.nextElement();
                    
                    // Checking wheather button is selected
                    if (Rbutton.isSelected()) 
                    {
                        if (Rbutton.getText().equals(choices[ans]))             // Checking for the correct answer
                        {
                            answerValidity = true;                              // Is the selected answer correct? Yes
                        } else
                        {
                            answerValidity = false;                             // Is the selected answer correct? No
                        }
                    }
                }              
            }
        }
    }
    
    
}

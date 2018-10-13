/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sec_asgn_test_guiapp_v2.pkg0;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private int qTime;
    
    // GUI elements
    private JFrame pluginFrame;
    
    // Logic elements
    private Object monitor = new Object();                                      // To be used as a monitor
    private boolean answered = false;                                           // A flag to store whether user has answered
    private boolean answerValidity = false;                                     // A flag to store whether the answer is correct
        
    public saq() {
      

    }
    
    // Returning an saq object with question interface with the details
    public question makeShortAnswerQuestion(String Question, String Answer , int QTime)
    {
        saq sa = new saq();    
        sa.question = Question;
        sa.ans = Answer;
        sa.qTime = QTime;
        return sa;
    }
    
    @Override
    public boolean invoke(JFrame PluginFrame) throws InterruptedException 
    {
        // Setting up GUI elements
        JLabel questionLabel = new JLabel();
        questionLabel.setText(question);
        questionLabel.setBounds(15, 10, 400, 30);

        JTextField ansTF = new JTextField();
        ansTF.setBounds(25, 50, 200, 90);
        
        JButton submit = new JButton ("Submit");
        submit.setBounds(425, 200, 100, 30);
        submit.setActionCommand("submitClicked");
        submit.setEnabled(false);
        
        JLabel timerNoteLbl = new JLabel("Remaining Time : ");
        timerNoteLbl.setBounds(400, 100, 100, 30);
                
        JLabel timerLbl = new JLabel();
        timerLbl.setBounds(500, 100, 100, 30);

        pluginFrame = PluginFrame;
        pluginFrame.add(ansTF);
        pluginFrame.add(questionLabel);
        pluginFrame.add(submit);
        pluginFrame.add(timerNoteLbl);
        pluginFrame.add(timerLbl);

        
        // Setting up the event handler for the submit button an the answer text box
        theEventHandler eventHandler = new theEventHandler(questionLabel, ansTF,submit); 
        submit.addActionListener(eventHandler);
   
        // Adding a focus listner to the Text area so that the submit button is enable only when Text area is clicked
        ansTF.addFocusListener(new FocusListener() {
        
            @Override
            public void focusGained(FocusEvent e) {
                submit.setEnabled(true);
            }

            // Submit button is enable only if the use enters some value 
            @Override
            public void focusLost(FocusEvent e) {
                if(ansTF.getText().equals(""))
                {
                    submit.setEnabled(false);
                }
                else
                {
                    submit.setEnabled(true);
                }
            }
        });

        // Timer thread to show the remaining time
        TimerClass tc = new TimerClass(pluginFrame,timerLbl);
        Thread timer = new Thread(tc);                              
        timer.start(); 
     
        pluginFrame.revalidate();                                               // Displaying the 2nd and later questions after clearing off everyting that was on the frame previously
        pluginFrame.repaint();                                                  // Displaying the 2nd and later questions after clearing off everyting that was on the frame previously
        pluginFrame.setVisible(true);                                           // Making the plugin frame visible
                             
        // A monitor is used to wait for the user's answer
        synchronized(monitor)
        {         
            monitor.wait(qTime*1000);                                           // Monitor expires when the count down expires              
        }

        if(!answered)                                                           // A flag is used to check wheather the user actually answerd the question
        {
             if (ansTF.getText().equals(ans)) {
                JOptionPane.showMessageDialog(null, "Your Answer is correct & Time out"); // Time out notice if the user didnt answer in time, selected answer is verified here
             } else {
                JOptionPane.showMessageDialog(null, "Your Answer is wrong & Time out");  // Time out notice if the user didnt answer in time, selected answer is verified here. If there's no answer its still wrong.
             }
        }

          return answerValidity;                                                // returning the vlidity of the answer

    }

    // This is where the timer thread's Run() method is defined , it just sleeps for 1 second and updates the current time   
    private class TimerClass implements Runnable
    {
        private JFrame pluginFrame;
        private JLabel timer;
        
        public TimerClass (JFrame PluginFrame,JLabel Timer)
        { 
            pluginFrame = PluginFrame;
            timer = Timer;
        }
        
        @Override
        public void run() {
            for (int i=qTime;i>=0;i--)
            {
                timer.setText(String.valueOf(i));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                Logger.getLogger(saq.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    // All events are handled here
    private class theEventHandler implements ActionListener{

        // Passing the elements from the upper class to theEventHandler class
        private JLabel questionLbl;
        private JTextField ansLabel;
        private JButton submit;

        public theEventHandler( JLabel QuestionLabel, JTextField AnsLabel, JButton Submit)
        {
            questionLbl = QuestionLabel;
            ansLabel = AnsLabel;
            submit = Submit;
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
   
            if(e.getActionCommand().equals("submitClicked"))                    // checking what event was fired
            {
                if(ansLabel.getText().equals(ans))                              // checking the answer
                {
                    JOptionPane.showMessageDialog(null, "Your Answer is correct"); 
                    answered = true;                                            // Has the user provided an Answer? Yes
                    answerValidity = true;                                      // Is the provided answer correct? Yes
                    synchronized (monitor) {                                    
                        monitor.notify();                                       // Wakes up the monitor as an aswer is provided
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Your Answer is wrong");
                    answered = true;                                            // Has the user provided an Answer? Yes
                    answerValidity = false;                                     // Is the provided answer correct? No
                    synchronized (monitor) 
                    {
                        monitor.notify();                                       // Wakes up the monitor as an aswer is provideds
                    }
                 
                }
            }
        }
    }
}

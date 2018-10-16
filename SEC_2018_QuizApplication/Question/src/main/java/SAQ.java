/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package quizapplication;

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
import javax.swing.SwingUtilities;


/**
 *
 * @author Bingusara
 */
public class SAQ implements Question{
    
    // saq object data holders
    private String question;
    private String ans;
    private int qTime;
    
    // GUI elements
    private JFrame pluginFrame;
    JLabel questionLabel = new JLabel();
    JButton submit = new JButton("Submit");
    JLabel timerNoteLbl = new JLabel("Remaining Time : ");
    JTextField ansTF = new JTextField();
    
    // Logic elements
    private Object monitor = new Object();                                      // To be used as a monitor
    private boolean answered = false;                                           // A flag to store whether user has answered
    private boolean answerValidity = false;                                     // A flag to store whether the answer is correct
        
    public SAQ() {
      

    }
    
    // Returning an saq object with question interface with the details
    public Question makeShortAnswerQuestion(String Question, String Answer,int QTime)
    {
        SAQ sa = new SAQ();    
        sa.question = Question;
        sa.ans = Answer;
        sa.qTime = QTime;
        return sa;
    }
    
    @Override
    public boolean invoke(JFrame PluginFrame) throws InterruptedException
    {
        // If GUI tends to freeze on this SwingUtilities.invokeLater can be added
        
        SwingUtilities.invokeLater(() -> {                                      // This for demostration purpose only. I added this since the plugin writer may create question type which may require heavy processing. (This example doesnt actully need it)

        // Setting up GUI elements
        questionLabel.setText(question);
        questionLabel.setBounds(20, 10, 400, 30);

        ansTF.setBounds(25, 50, 200, 90);        

        submit.setBounds(425, 200, 100, 30);
        submit.setActionCommand("submitClicked");
        submit.setEnabled(false);

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
        theEventHandler eventHandler = new theEventHandler(ansTF,submit); 
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
        TimerClass tc = new TimerClass(timerLbl);
        Thread timer = new Thread(tc);                              
        timer.start(); 
     
        pluginFrame.revalidate();                                               // Displaying the 2nd and later questions after clearing off everyting that was on the frame previously
        pluginFrame.repaint();                                                  // Displaying the 2nd and later questions after clearing off everyting that was on the frame previously
        pluginFrame.setVisible(true);                                           // Making the plugin frame visible
               
        });                                                                      // End of Swing Utiltities Invoke Later
      
        
        // A monitor is used to wait for the user's answer
        synchronized(monitor)
        {   
                monitor.wait(qTime*1000);                                       // Monitor expires when the count down expires              
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

    @Override
    public String getQuestion ()
    {
        return question;                                                        // Returns the question of this object as a string
    }
    
    // This is where the timer thread's Run() method is defined , it just sleeps for 1 second and updates the current time   
    private class TimerClass implements Runnable
    {
        private JLabel timer;
        
        public TimerClass (JLabel Timer)
        { 
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
                Logger.getLogger(SAQ.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    // All events are handled here
    private class theEventHandler implements ActionListener{

        // Passing the elements from the upper class to theEventHandler class
        private JTextField ansLabel;
        private JButton submit;

        public theEventHandler( JTextField AnsLabel, JButton Submit)
        {
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

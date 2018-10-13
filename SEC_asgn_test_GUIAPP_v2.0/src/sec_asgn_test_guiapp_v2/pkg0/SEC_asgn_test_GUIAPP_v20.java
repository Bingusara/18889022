/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sec_asgn_test_guiapp_v2.pkg0;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Bingusara
 */
public class SEC_asgn_test_GUIAPP_v20 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
       

        JFrame MainFrame = new JFrame("Quiz Application");   

        MainFrame.setSize(400,200);                                             // Size of the form
        MainFrame.setResizable(false);                                          // form can't be resized now
        MainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);                      //Exits the whole application, when close button is clicked      
        MainFrame.setLayout(null);                                              // Setting up the Layout
               
        // Setting up the elements in the JFrame
        JLabel title = new JLabel("Welcome to the Quiz App v2.0");
        title.setBounds(105,10,175,30);
        
        JLabel enterYourLbl = new JLabel("Please enter your plugin name...");
        enterYourLbl.setBounds(20,60,200,30);
          
        JTextField pluginNameTextField = new JTextField();
        pluginNameTextField.setBounds(20,100,200,30);
        
        JButton proceed = new JButton("Proceed");
        proceed.setBounds(250, 100, 100, 30);
        proceed.setActionCommand("Clicked");
        
        //Adding all the elements to the JFrame
        MainFrame.add(proceed);
        MainFrame.add(title);
        MainFrame.add(enterYourLbl);
        MainFrame.add(pluginNameTextField);
        
        MainFrame.setVisible(true);                                             // There will be problem in updating the elements, if this visibility isn't set at the very end of the code

        proceed.addActionListener(new ActionListener(){                         //Setting up an action lisnter for the "Proceed" button click
                public void actionPerformed(ActionEvent e) {
                    politix p = new politix();                                  // These parts will be later replaced with dynamic class loader
                   
                    Thread resultsCatcher = new Thread( new Runnable()
                    {
                        @Override
                        public void run()
                        {
                         int[] results = Arrays.copyOf(p.runQuiz(), 3);              // calling the plugins method & copying the result to a int array
                  
                            while (results[2]  == 0)
                            {
                                try {
                                    Thread.currentThread().sleep(2000);

                                } catch (InterruptedException ex) {
                                    System.out.println("Interrupted error occured in the resultsCatcher thread");
                                    Logger.getLogger(SEC_asgn_test_GUIAPP_v20.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }

                            JOptionPane.showMessageDialog(null, "You have got " + results[0] + " correct & " + results[1] + " wrong out of " + results[2] + " questions");  // A message given to the user  
               
                        }
                        
                    });
                    
                    resultsCatcher.start();
                    
                }
        });
   
        
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sec_asgn_test_guiapp;

import java.awt.FlowLayout;
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
public class SEC_asgn_test_GUIApp extends JFrame{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        JFrame MainFrame = new JFrame("Quiz Application");   

        MainFrame.setSize(400,200);           // Size of the form
        MainFrame.setResizable(true);        // form can't be resized now
        MainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE); //Exits the whole application, when close button is clicked      
        MainFrame.setLayout(null);          // Setting up the Layout
        
        
        // Setting up the elements in the JFrame
        JLabel title = new JLabel("Welcome to the Quiz App");
        title.setBounds(125,10,150,30);
        
        JButton proceed = new JButton("Proceed");
        proceed.setBounds(250, 100, 100, 30);
        proceed.setActionCommand("Clicked");
        proceed.addActionListener(new ActionListener(){                 //Setting up an action lisnter for the button click
                public void actionPerformed(ActionEvent e) {
                    
                    politix p = new politix();
                    p.invoke();
                    
                }
        });

        JLabel enterYourLbl = new JLabel("Please enter your plugin name...");
        enterYourLbl.setBounds(20,60,200,30);
          
        JTextField pluginNameTextField = new JTextField();
        pluginNameTextField.setBounds(20,100,200,30);

        //Adding all the elements to the JFrame
        MainFrame.add(proceed);
        MainFrame.add(title);
        MainFrame.add(enterYourLbl);
        MainFrame.add(pluginNameTextField);
        
        MainFrame.setVisible(true);     // There will be problem in updating the elements, if this visibility isn't set at the very end
    }
     
}

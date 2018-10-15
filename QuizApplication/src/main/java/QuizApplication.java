/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package quizapplication;

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
public class QuizApplication {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        QuizApplication mainObj = new QuizApplication();                        // Creating an object of QuizApplication
        
        JFrame MainFrame = new JFrame("Quiz Application");                      // Creating the main Frame of the application

        MainFrame.setSize(400,200);                                             // Size of the form
        MainFrame.setResizable(false);                                          // form can't be resized now
        MainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);                      // Exits the whole application, when close button is clicked      
        MainFrame.setLayout(null);                                              // Setting up the Layout
               
        // Setting up the elements in the JFrame
        JLabel title = new JLabel("Welcome to the Quiz Application");
        title.setBounds(105,10,200,30);
        
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
        
        PluginLoader quizLoader = mainObj.givePluginLoaderObj();                // An object from plugin loader to load objects from classes which implement Quiz Interface
        PluginLoader questionLoader = mainObj.givePluginLoaderObj();            // An object from plugin loader to load objects from classes which implement Question Interface

        
        proceed.addActionListener(new ActionListener(){                         //Setting up an action lisnter for the "Proceed" button click
     
            public void actionPerformed(ActionEvent e) {
       
                // Setting up the class handler thread
                // This thread is responsible for loading the Quiz/Question Plugins and displaying the final result 
                Thread classHandler = new Thread( new Runnable()
                {      
                    Politix p = new Politix();
                    @Override
                    public void run()
                    {
                       
                        try
                        {
                          Quiz quizObj = quizLoader.loadQuizPlugin((pluginNameTextField.getText()));      // Loading an object from a class that implements Quiz interface which matches the text entered into the TextArea
                          int[] results = Arrays.copyOf(quizObj.runQuiz(questionLoader), 3);              // calling the runQuiz method from the loaded plugin object & copying the result to a int array. Note that another plugin object "QuestionLoader" is sent into the runQuiz()
                           
                            // This part continously check wheather the results array is populated. (More specifically wheather the total count given by the quiz is copied to the last index of results array)
                            while (results[2]  == 0)
                            {
                                try {
                                    Thread.currentThread().sleep(2000);         // Sleeps for 2 secods and runs the while loop

                                } catch (InterruptedException ex) {
                                    // Error Code 008
                                    JOptionPane.showMessageDialog(null, "Sorry this plugin encountered an Internal Error, Please contact the Developer!\n Error Code: 008");        
                                    System.out.println("Interrupted error occured in the resultsCatcher thread");
                                    Logger.getLogger(QuizApplication.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }

                            // A Final results message given to the user
                            JOptionPane.showMessageDialog(null, "You got " + results[0] + " correct & " + results[1] + " wrong \n         Out of " + results[2] + " questions");   
                        }
                        catch (ClassNotFoundException ex)
                        {
                           // If the user entered plugin is not a valid Quiz plugin
                           JOptionPane.showMessageDialog(null, "Sorry this plugin was not found!");   
                           System.out.println("Problem in loading the class");
                           Logger.getLogger(QuizApplication.class.getName()).log(Level.SEVERE, null, ex);
                           System.out.println(ex.getCause());
                        }
                    }

                });

                classHandler.setName("classHandler");
                classHandler.start();
                    
                }
        });
        
    }
    
    // A method to load and return a PluginLoader object  
    public PluginLoader givePluginLoaderObj ()
    {
        PluginLoader pluginload = new PluginLoader();
        return pluginload;
    }
    
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sec_asgn_test_cmdapp;

import java.util.Scanner;

/**
 *
 * @author Bingusara
 */
public class SEC_asgn_test_CmdApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       
        // These parts are hard coded
        System.out.println("Hello there, welcome to the plugin application...\n");
        System.out.print("Please Enter the name of the plugin you wanna load : ");
        Scanner plug_ans = new Scanner(System.in);
        String Plug_Ans = plug_ans.nextLine();
        System.out.println("Loading plugin " + Plug_Ans+"...\n");
        
        // Actual code in the main java file.        
        politix pol = new politix();
        question q[] = pol.invoke();
               
    }
}

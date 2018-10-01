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
public class politix implements plugin{
    
    
    // This plugin has politix related questions.
    
    public politix()
    {
        
    }
    
    mcq All_MCQ[] = new mcq[3];

    String[][] choises = {{"1.Mahinda", "2.Maithree", "3.Ranil", "4.Chandrika"},
                          {"1.225", "2.200", "3.125", "4.175"}};
    
    String[] questions = {"Whats the name of the president in Sri lanka?",
                            "Whats the no of parliment seats in S.L.?"};
    
    int[] answers =  {1,0};
        
        
    public question[] invoke() {
  
        for (int noOfQues=0; noOfQues<2 ; noOfQues++)
        {
            All_MCQ[noOfQues] = new mcq();
            All_MCQ[noOfQues].setQuestion(questions[noOfQues]);
                System.out.println(All_MCQ[noOfQues].getQuestion());
          
            All_MCQ[noOfQues].setChoises(choises[noOfQues]);
                for (int choiseNo=0; choiseNo<choises[noOfQues].length; choiseNo++)
                {
                    System.out.println(All_MCQ[noOfQues].getChoises()[choiseNo]);
                }
          
            All_MCQ[noOfQues].setAnswer(answers[noOfQues]);
            
            
            Scanner input = new Scanner(System.in);
            int user_answer = input.nextInt();  
            
            if ((user_answer-1) == All_MCQ[noOfQues].getAnswer())
            {
                System.out.println("Correct");            
            }
            else
            {
                System.out.println("wrong");
            }     
        }
        
        return All_MCQ;
    }
}

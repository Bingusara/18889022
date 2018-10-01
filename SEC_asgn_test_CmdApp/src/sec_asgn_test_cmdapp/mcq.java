/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sec_asgn_test_cmdapp;

import java.util.Arrays;

/**
 *
 * @author Bingusara
 */
public class mcq implements question {

    private String question;
    private String choices[];
    private int ans;
    private boolean processed = true;
    

    public mcq() {

    }

    public void setQuestion(String Question) {

        question = Question;
    }

    public void setAnswer(int Ans) {

        ans = Ans;
    }

    public void setChoises(String Choises[]) {
        choices = Arrays.copyOf(Choises, Choises.length);

        // the above method of copying arrays can be simply coded as follows
        /* 
            for (int i=0 , Choises.length; i++)
            {
                choises[i] = Choises[i];
            }
         */
    }
    
    public void setProcessed(){
        
        processed = false;
    }

    public String getQuestion() {

        return (question);
    }

    public int getAnswer() {

        return (ans);
    }

    public String[] getChoises() {
        
        return (choices);
    }
    
    public boolean getProcessed(){
        
        return (processed);
    }
    
    

}

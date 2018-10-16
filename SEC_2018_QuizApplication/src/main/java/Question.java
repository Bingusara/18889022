/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package quizapplication;



import javax.swing.JFrame;

/**
 *
 * @author Bingusara
 */
public interface Question {
    
    public boolean invoke(JFrame jFrame) throws InterruptedException;           // This is to invoke or display the question
    public String getQuestion();                                                // This to get the preview of the next question
}

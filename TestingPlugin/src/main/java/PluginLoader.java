/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package sec_asgn_test_guiapp_v3.pkg0;

import java.nio.file.*;

/**
 *
 * @author Bingusara
 */
public class PluginLoader extends ClassLoader{
    
    
        public Quiz loadQuizPlugin(String fname) throws ClassNotFoundException
        {

		try
		{
			return (Quiz)PluginLoader.class.getClassLoader().loadClass(fname).newInstance();
		}
		catch(ClassNotFoundException | IllegalAccessException | InstantiationException e)
		{
			throw new ClassNotFoundException(String.format("Could not load '%s': %s", fname, e.getMessage()),e);
		}
	}
        
        public Question loadQuestionPlugin(String fname) throws ClassNotFoundException
        {

		try
		{
			return (Question)PluginLoader.class.getClassLoader().loadClass(fname).newInstance();
		}
		catch(ClassNotFoundException | IllegalAccessException | InstantiationException e)
		{
			throw new ClassNotFoundException(String.format("Could not load '%s': %s", fname, e.getMessage()),e);
		}
	}
        
        public void printer ()
        {
            System.out.println("Yooooooooooooooooooooo yoooooooooooooooooooooooooooo");
        }

    
}

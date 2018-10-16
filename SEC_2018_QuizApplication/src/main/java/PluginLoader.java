/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package quizapplication;


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


    
}

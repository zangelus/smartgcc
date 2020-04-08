/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartgcc;

import javafx.stage.Stage;

/**
 *
 * @author zange
 */
public class SingletonApp {
    
    private static SingletonApp instance = null; 
    
    private SingletonApp() 
    { 
    } 
    
    public static SingletonApp getInstance() 
    { 
        if (instance == null) 
            instance = new SingletonApp(); 
  
        return instance; 
    } 
    
    public Stage stage;
    public boolean CheckBox100_1;
    public boolean CheckBox100_2;
    public boolean CheckBox300_1;
    public String TextField100_2;

}

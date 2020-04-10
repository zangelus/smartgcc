/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartgcc;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Dictionary;
import java.util.Properties;
import javafx.stage.Stage;

/**
 *
 * @author zange
 */
public class SingletonApp {
    
    String path =  System.getProperty("user.dir");
    String fileName = path + "\\options.properties";
    public Properties prop = new Properties();
    String True = "true";
    String notDefined = "no_def";
    
    private static SingletonApp instance = null; 

    public Stage stage;
    
    private SingletonApp() 
    { 
    } 
    
    public static SingletonApp getInstance() 
    { 
        if (instance == null) 
            instance = new SingletonApp(); 
  
        return instance; 
    } 
    
    public Boolean Load(){
        try (InputStream input = new FileInputStream(fileName)) {

            prop.load(input);
            return true;

        } catch (IOException ex) {
            return false;
        }
    }
    
    public Boolean Save(){
        try (OutputStream output = new FileOutputStream(fileName)) {

            prop.store(output, null);
            return true;
        } 
        catch (IOException io) {
            
            return false;
        }
    }
}

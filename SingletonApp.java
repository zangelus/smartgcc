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
import java.util.Properties;
import javafx.stage.Stage;

/**
 *
 * @author zange
 */
public class SingletonApp {
    
    String path =  System.getProperty("user.dir");
    String fileName = path + "\\options.properties";
    Properties prop = new Properties();
    String True = "true";
    
    private static SingletonApp instance = null; 
    
    public Stage stage;
    
    public boolean CheckBox100_1;
    public boolean CheckBox100_2;
    public boolean CheckBox300_1;
    public String  TextField100_2;
    
    private SingletonApp() 
    { 
    } 
    
    public static SingletonApp getInstance() 
    { 
        if (instance == null) 
            instance = new SingletonApp(); 
  
        return instance; 
    } 
    
    public boolean LoadSettings() {

        try (InputStream input = new FileInputStream(fileName)) {

            prop.load(input);

            CheckBox100_1     = prop.getProperty("CheckBox100_1").equals(True);
            CheckBox100_2     = prop.getProperty("CheckBox100_2").equals(True);
            CheckBox300_1     = prop.getProperty("CheckBox300_1").equals(True);
            TextField100_2    = prop.getProperty("TextField100_2");
            
            return true;

        } catch (IOException ex) {
            
            return false;
        }
    }
    
    public boolean SaveSetting(String key, String value) {

        try (OutputStream output = new FileOutputStream(fileName)) {

            prop.setProperty(key, value);
            prop.store(output, null);
            
            return true;
        } 
        catch (IOException io) {
            
            return false;
        }
    }
    
    public boolean SaveAllSettings() {

        try (OutputStream output = new FileOutputStream(fileName)) {

            SingletonApp.getInstance();
            SingletonApp s  =  SingletonApp.getInstance();
            
            prop.setProperty("CheckBox100_1", s.CheckBox100_1?"true":"false");
            prop.setProperty("CheckBox100_2", s.CheckBox100_2?"true":"false");
            prop.setProperty("CheckBox300_1", s.CheckBox300_1?"true":"false");
            prop.setProperty("TextField100_2", s.TextField100_2);

            prop.store(output, null);
            
            return true;
        } 
        catch (IOException io) {
            
            return false;
        }
    }
}

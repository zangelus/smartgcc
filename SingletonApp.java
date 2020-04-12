/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartgcc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import static java.lang.System.getProperty;
import java.nio.file.Files;
import java.util.Properties;
import javafx.stage.Stage;

/**
 *
 * @author zange
 */
public class SingletonApp {
    
    //page 1 - compiling
    public final String OP_S_OUTPUT_FILE_NAME       = "OP_S_OUTPUT_FILE_NAME";
    public final String OP_B_OUTPUT_FILE_NAME       = "OP_B_OUTPUT_FILE_NAME";
    public final String OP_B_CREATE_OBJECT_FILE     = "OP_B_CREATE_OBJECT_FILE";
    
    //page 3 - debugging
    public final String OP_B_WARNING_PROFILE        = "OP_B_WARNING_PROFILE";
    public final String OP_S_WARNING_PROFILE        = "OP_S_WARNING_PROFILE";
    
    //page 5 - developper
    public final String OP_B_ENABLE_VERBOSE         = "OP_B_ENABLE_VERBOSE";
    
    //aditional
    public final String OP_S_LAST_PATH_OPENED       = "OP_S_LAST_PATH_OPENED";
    
    public String LAST_PATH_OPENED = ""; 
    public String LAST_GCC_COMMAND = ""; 

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
    
    
    public boolean getBoolValue (String Key){
        try{
            return prop.getProperty(Key).equals(True);
        }
        catch (Exception e){
            System.out.println("key was not found in porperties\n");
            throw e;
        }
    }
    
    public Boolean setBoolValue (String Key, Boolean value){
        try{
            prop.setProperty(Key,value?"true":"false");
            return true;
        }
        catch (Exception e){
            System.out.println("problems setting the key/value pair, try again\n");
            return false;
        }
    }
    
    public String getTextValue (String Key){
        try{
            return prop.getProperty(Key);
        }
        catch (Exception e){
            System.out.println("key was not found in porperties\n");
            throw e;
        }
    }
    public Boolean setTextValue (String Key, String value){
        try{
            prop.setProperty(Key,value);
            return true;
        }
        catch (Exception e){
            System.out.println("problems setting the key/value pair, try again\n");
            return false;
        }
    }
    public Integer getIntValue (String Key){
        try{
            return  Integer.parseInt(prop.getProperty(Key));
        }
        catch (Exception e){
            System.out.println("key was not found in porperties\n");
            throw e;
        }
    }
    public Boolean setIntegerValue (String Key, Integer value){
        try{
            prop.setProperty(Key,Integer.toString(value));
            return true;
        }
        catch (Exception e){
            System.out.println("problems setting the key/value pair, try again\n");
            return false;
        }
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
    
    public void defaultSettings(){

        File f  = new File(fileName);
        f.delete();

        //page 1
        prop.setProperty(OP_B_CREATE_OBJECT_FILE        , "false");
        prop.setProperty(OP_B_OUTPUT_FILE_NAME          , "false");
        prop.setProperty(OP_S_OUTPUT_FILE_NAME          , "");
        
        //page 3
        prop.setProperty(OP_B_WARNING_PROFILE           , "false");
        prop.setProperty(OP_S_WARNING_PROFILE           , "Default");
        
        //page 5
        prop.setProperty(OP_B_ENABLE_VERBOSE            , "false");
        
        Save();
    }
    
    public void sanityCheck(){
        File f  = new File(fileName);
        if(!f.exists()){
            defaultSettings();
        }
    }
}

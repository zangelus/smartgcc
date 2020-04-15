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
import java.util.Observable;
import java.util.Properties;
import javafx.stage.Stage;

/**
 *
 * @author zange
 */
public class SingletonApp extends Observable{
    
    //page 1 - compiling
    public final String OP_S_OUTPUT_FILE_NAME       = "OP_S_OUTPUT_FILE_NAME";
    public final String OP_B_OUTPUT_FILE_NAME       = "OP_B_OUTPUT_FILE_NAME";
    public final String OP_B_CREATE_OBJECT_FILE     = "OP_B_CREATE_OBJECT_FILE";
    
    //page 3 - debugging
    public final String OP_B_WARNING_PROFILE        = "OP_B_WARNING_PROFILE";
    public final String OP_S_WARNING_PROFILE        = "OP_S_WARNING_PROFILE";
    
    //page 4 - code generation
    public final String OP_B_V_checkBox400_1          = "OP_B_V_checkBox400_1";
    public final String OP_B_V_checkBox400_2          = "OP_B_V_checkBox400_2";
    public final String OP_B_V_checkBox400_3          = "OP_B_V_checkBox400_3";
    public final String OP_B_V_checkBox400_4          = "OP_B_V_checkBox400_4";
    
    public final String OP_B_D_checkBox400_1          = "OP_B_D_checkBox400_1";
    public final String OP_B_D_checkBox400_2          = "OP_B_D_checkBox400_2";
    public final String OP_B_D_checkBox400_3          = "OP_B_D_checkBox400_3";
    public final String OP_B_D_checkBox400_4          = "OP_B_D_checkBox400_4";
    
    //page 5 - developper
    public final String OP_B_V_checkBox500_1          = "OP_B_V_checkBox500_1";
    public final String OP_B_V_checkBox500_2          = "OP_B_V_checkBox500_2";
    public final String OP_B_V_checkBox500_3          = "OP_B_V_checkBox500_3";
    public final String OP_B_V_checkBox500_4          = "OP_B_V_checkBox500_4";

    public final String OP_B_D_checkBox500_1          = "OP_B_D_checkBox500_1";
    public final String OP_B_D_checkBox500_2          = "OP_B_D_checkBox500_2";
    public final String OP_B_D_checkBox500_3          = "OP_B_D_checkBox500_3";
    public final String OP_B_D_checkBox500_4          = "OP_B_D_checkBox500_4";
    

    //aditional
    public final String OP_S_LAST_PATH_OPENED       = "OP_S_LAST_PATH_OPENED";
    public final String OP_S_PROFILE                = "OP_S_PROFILE";
    
    
    //others
    public String LAST_PATH_OPENED                  = ""; 
    public String LAST_GCC_COMMAND                  = "";
    public String CURRENT_OPEN_PROJECT              = "";
    public Boolean isProjectOpen                    = false;
    public final String NO_PROJECT_IS_OPEN          = "No project is open. Open a project first!";
    public Boolean isOpeningUserOptions    = false;
    

    //Const
    public String cb300_1_option1 = "Inhibit all warning messages";
    public String cb300_1_option2 = "Make all warnings into errors";
    public String cb300_1_option3 = "Enable language specific warnings";
    public String cb300_1_option4 = "Enable extra warnings";
    
    String path =  System.getProperty("user.dir");
    String defaultProjctFile = path + "\\options.properties";
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
    
    public Boolean Load(String fileName){
        try (InputStream input = new FileInputStream(fileName)) {

            prop.load(input);
            return true;

        } catch (IOException ex) {
            return false;
        }
    }
    
    public Boolean Save(String fileName){
        try (OutputStream output = new FileOutputStream(fileName)) {

            prop.store(output, null);
            return true;
        } 
        catch (IOException io) {
            
            return false;
        }
    }
    /*
    public Boolean Load(){
        try (InputStream input = new FileInputStream(defaultProjctFile)) {

            prop.load(input);
            return true;

        } catch (IOException ex) {
            return false;
        }
    }
    
    public Boolean Save(){
        try (OutputStream output = new FileOutputStream(defaultProjctFile)) {

            prop.store(output, null);
            return true;
        } 
        catch (IOException io) {
            
            return false;
        }
    }
    */
    
    public void defaultSettings(){

        File f  = new File(CURRENT_OPEN_PROJECT);
        f.delete();

        //page 1
        prop.setProperty(OP_B_CREATE_OBJECT_FILE        , "false");
        prop.setProperty(OP_B_OUTPUT_FILE_NAME          , "false");
        prop.setProperty(OP_S_OUTPUT_FILE_NAME          , "");
        
        //page 3
        prop.setProperty(OP_B_WARNING_PROFILE           , "false");
        prop.setProperty(OP_S_WARNING_PROFILE           , "");
        
        //page 4
        prop.setProperty(OP_B_V_checkBox400_1            , "false");
        prop.setProperty(OP_B_V_checkBox400_2            , "false");
        prop.setProperty(OP_B_V_checkBox400_3            , "false");
        prop.setProperty(OP_B_V_checkBox400_4            , "false");
        prop.setProperty(OP_B_D_checkBox400_1            , "false");
        prop.setProperty(OP_B_D_checkBox400_2            , "false");
        prop.setProperty(OP_B_D_checkBox400_3            , "false");
        prop.setProperty(OP_B_D_checkBox400_4            , "false");
        
        //page 5
        prop.setProperty(OP_B_V_checkBox500_1            , "false");
        prop.setProperty(OP_B_V_checkBox500_2            , "false");
        prop.setProperty(OP_B_V_checkBox500_3            , "false");
        prop.setProperty(OP_B_V_checkBox500_4            , "false");
        prop.setProperty(OP_B_D_checkBox500_1            , "false");
        prop.setProperty(OP_B_D_checkBox500_2            , "false");
        prop.setProperty(OP_B_D_checkBox500_3            , "false");
        prop.setProperty(OP_B_D_checkBox500_4            , "false");

        //Additional
        prop.setProperty(OP_S_LAST_PATH_OPENED          , "");
        prop.setProperty(OP_S_PROFILE                   , "");

        Save(CURRENT_OPEN_PROJECT);
    }
    
    public Boolean profileExist(){
        
        File f  = new File(CURRENT_OPEN_PROJECT);
        if(!f.exists()){
            defaultSettings();
            return false;
        }
        return true;
    }

    public void setNews() {
        setChanged();
        notifyObservers();
    }
    
    
}

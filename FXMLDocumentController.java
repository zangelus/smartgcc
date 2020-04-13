/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartgcc;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author zange
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private TextField textfield1;
    @FXML
    private TextField textfield2;
    @FXML
    private ListView<String> listview2;
    @FXML
    private MenuBar menuBar;
    
    SingletonApp s = SingletonApp.getInstance();
    private String mainFilePath;
    @FXML
    private Label label1;
    @FXML
    private AnchorPane anchor1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        if(s.isProjectOpen){
            
            anchor1.setVisible(true);
            //load current project in properties
            s.Load(s.CURRENT_OPEN_PROJECT);
            
            //updateUI
            UpdateUI(s.CURRENT_OPEN_PROJECT);
        }
        else{
            anchor1.setVisible(false);
        }
    }    


    @FXML
    private void handleBtnBrowse(ActionEvent event) {
        browse();
    }
    
    @FXML
    private void handleBtnBuild(ActionEvent event) {
        build();
    }

    private void build(){
        
        String[] cmd1 = getGccCommand();
        s.LAST_GCC_COMMAND = getStringGccCommand(cmd1);
        textfield1.setText(s.LAST_GCC_COMMAND);

        listview2.getItems().add("Start: Build a project using g++");

        GccCommand gccCmd = new GccCommand();
        int retValue = gccCmd.ExecutedCommand1(cmd1);

        listview2.getItems().add("ExitValue: " + retValue);
        listview2.getItems().add(gccCmd.GetOutput());
        
        if(retValue != 0){
            listview2.getItems().add(gccCmd.GetError());
        }
        
        System.out.println("ExitValue: " + retValue);
    }

    private void browse(){
        textfield2.clear();
        
        FileChooser fc = new FileChooser();
        if(!s.LAST_PATH_OPENED.equals(""))
        {
            File f = new File(s.LAST_PATH_OPENED);
            if(f.exists()){
               fc.setInitialDirectory(new File(f.getParent())); 
            }
        }
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("C++ File", "*.cpp"),
                new FileChooser.ExtensionFilter("C File", "*.c") 
        );
        
        File sf = fc.showOpenDialog(null);
        if(sf != null){
            s.LAST_PATH_OPENED = sf.getAbsolutePath();
            textfield2.setText(s.LAST_PATH_OPENED);
            textfield1.setText(getStringGccCommand(getGccCommand()));
            s.isProjectOpen = true;
        }
        else{
            System.out.println("file not valid");
        } 
    }
     
    private String getSourceDirectory(boolean bin) {
        
        
        if(!s.LAST_PATH_OPENED.equals("")){
            
            File mainFile = new File(s.LAST_PATH_OPENED);
            
            String absolutePath = mainFile.getAbsolutePath();
            String parentDirectory = mainFile.getParent() + (bin?"\\bin":"\\debug");

            File directory = new File(parentDirectory);
            if (! directory.exists()){
                directory.mkdir();
            }
            return parentDirectory + "\\";
        }
        return null;
        
    }
    
    private String getNameOfMainFile() {

        if(!s.LAST_PATH_OPENED.equals("")){
            
            File mainFile = new File(s.LAST_PATH_OPENED);
            return mainFile.getName().replaceFirst("[.][^.]+$", "");
        }
        return null;
        
    }

    private String[] getGccCommand() {
        
        ArrayList<String> comm  = new ArrayList<>(); 
        String pathToBin = getSourceDirectory(true);
        if(pathToBin==null) return null;
        
        String outputName;
        
        comm.add("g++");
        
        if(s.getBoolValue(s.OP_B_ENABLE_VERBOSE)){
            comm.add("-v");
        }
        
        if(s.getBoolValue(s.OP_B_WARNING_PROFILE)){
            
            if (s.cb300_1_option1.equals(s.getTextValue(s.OP_S_WARNING_PROFILE))) {
                comm.add("-w");
            }
            else if (s.cb300_1_option2.equals(s.getTextValue(s.OP_S_WARNING_PROFILE))) {
                comm.add("-Werror");
            }
            else if (s.cb300_1_option3.equals(s.getTextValue(s.OP_S_WARNING_PROFILE))) {
                comm.add("-Wall");
            }
            else if (s.cb300_1_option4.equals(s.getTextValue(s.OP_S_WARNING_PROFILE))) {
                comm.add("-Wextra");
            } 
        }
        
        if(s.getBoolValue(s.OP_B_CREATE_OBJECT_FILE) && 
           !"".equals(s.getTextValue(s.OP_S_OUTPUT_FILE_NAME)))
        {
            comm.add("-c");
        }
        if(s.getBoolValue(s.OP_B_OUTPUT_FILE_NAME) && 
           !"".equals(s.getTextValue(s.OP_S_OUTPUT_FILE_NAME)))
        {
            outputName = s.getTextValue(s.OP_S_OUTPUT_FILE_NAME);
        }
        else{
            //use the same name as the main.c file.
            outputName = getNameOfMainFile();
        } 
        
        comm.add("-o");
        pathToBin = pathToBin + outputName;
        comm.add(pathToBin);
        
        comm.add(s.LAST_PATH_OPENED);

        String[] cmd1 = new String[comm.size()];
        int i=0;
        
        for(Object o : comm.toArray())
        {
            cmd1[i++]= (String)o;
        }
        
        return cmd1;
    }
    
    public String getStringGccCommand(String[] cmds){
        
        String txtCommand="";
        String WS = " ";
        
        if(cmds==null) return "";
        
        for(String s1 : cmds){
            txtCommand += s1 + WS;
        }
        System.out.print("gcc command: " + txtCommand);  
        
        return txtCommand;
    }

    @FXML
    private void handleMainManu(ActionEvent event) {
        
        MenuItem mItem = (MenuItem) event.getSource();
        
        String menuItemName = mItem.getText();
        
        if ("User Options".equalsIgnoreCase(menuItemName)) {
            try{
                if(s.LAST_PATH_OPENED.equals("")){
                    generateAlert(s.NO_PROJECT_IS_OPEN);
                    return;
                }
                Parent root = FXMLLoader.load(getClass().getResource("UserOptions.fxml"));
                Scene scene = new Scene(root);
                //Stage window = (Stage) menuBar.getScene().getWindow();
                Stage window = s.stage;
                window.setScene(scene);
                window.show();
            }
            catch (Exception e){
                System.out.println("Error opening FXML window");
            }
        }
        else if("Build".equalsIgnoreCase(menuItemName)){
            build();
        } 
        else if("Set Default Options".equalsIgnoreCase(menuItemName)){
            s.defaultSettings();
        }
        else if("New Project".equalsIgnoreCase(menuItemName)){
            CreteNewProject();
        }
        else if("Open Project".equalsIgnoreCase(menuItemName)){
            
            File projectFile = Browse(FileType.PROPERTY);
            LoadNewProject(projectFile);
        }    
        else if("Save Project As".equalsIgnoreCase(menuItemName)){
            SaveProject();
        } 
        else if("Close".equalsIgnoreCase(menuItemName)){
            listview2.getItems().add("Closing the app");
            Platform.exit();
        } 
        else if("Change User Profile".equalsIgnoreCase(menuItemName)){
            try{
                Parent root = FXMLLoader.load(getClass().getResource("UserSelection.fxml"));
                Scene scene = new Scene(root);
                Stage window = s.stage;
                window.setScene(scene);
                window.show();
            }
            catch (Exception e){
                System.out.println("Error opening FXML window");
            }
        }
    }

    private void CreteNewProject() {
        
        //get the path to the main.cpp
        File f1 = Browse(FileType.CPP);
        
        if (f1 != null) {
            s.LAST_PATH_OPENED = f1.getAbsolutePath();

            //load defaults settings into properties
            s.Load(s.defaultProjctFile);

            //set desired settings in the new project
            s.setTextValue(s.OP_S_LAST_PATH_OPENED, s.LAST_PATH_OPENED);

            //start dialog to save the project in a property file
            File projectFile = SaveProject();
            LoadNewProject(projectFile);
        }
    }

    private Boolean LoadNewProject(File projectFile) {

        if (projectFile != null) {
            //Keep project file in memory
            s.CURRENT_OPEN_PROJECT = projectFile.getAbsolutePath();

            //Load the project in the UI
            if (UpdateUI(s.CURRENT_OPEN_PROJECT)) {
                s.isProjectOpen = true;
            }
            return true;
        }
        return false;
    }
    
    private enum FileType{
        CPP,
        PROPERTY
    }
    
    private File Browse(FileType type){

        FileChooser fc = new FileChooser();
        
        if(type==FileType.CPP){
            fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("C++ File", "*.cpp"),
                new FileChooser.ExtensionFilter("C File", "*.c") 
            );
        }
        else if(type==FileType.PROPERTY){
            fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Settings File", "*.properties")
            );
        }
        
        return fc.showOpenDialog(null);
    }
    
    private Boolean UpdateUI(String projectFile){
 
        if(projectFile != null){
            
            anchor1.setVisible(true);
            s.Load(projectFile);
            
            s.LAST_PATH_OPENED = s.prop.getProperty(s.OP_S_LAST_PATH_OPENED);
            
            textfield2.setText(s.LAST_PATH_OPENED);
            textfield1.setText(getStringGccCommand(getGccCommand()));
            label1.setText(projectFile);
            
            s.LAST_GCC_COMMAND = getStringGccCommand(getGccCommand());
            textfield1.setText(s.LAST_GCC_COMMAND);
            
            return true;
        }
        else{
            return false;
        } 
    }
    /*
    private void LoadProject() {
        
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Settings File", "*.properties")
        );
        
        File sf = fc.showOpenDialog(null);
        if(sf != null){
            
            s.Load(sf.getAbsolutePath());
            s.LAST_PATH_OPENED = s.prop.getProperty(s.OP_S_LAST_PATH_OPENED);
            textfield2.setText(s.LAST_PATH_OPENED);
            textfield1.setText(getStringGccCommand(getGccCommand()));
            label1.setText(sf.getName());
            
            s.isProjectOpen = true;
            s.CURRENT_OPEN_PROJECT = sf.getAbsolutePath();
        }
        else{
            System.out.println("file not valid");
        } 
        
        textfield2.setText(s.LAST_PATH_OPENED);
        s.LAST_GCC_COMMAND = getStringGccCommand(getGccCommand());
        textfield1.setText(s.LAST_GCC_COMMAND);
    }
*/
    private File SaveProject() {
        
        File f = null;
        
        if(s.LAST_PATH_OPENED.equals("")){
            generateAlert(s.NO_PROJECT_IS_OPEN);
            return f;
        }
        s.prop.setProperty(s.OP_S_LAST_PATH_OPENED, s.LAST_PATH_OPENED);
        
        FileChooser fileChooser = new FileChooser();

        //Set extension filter for text files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Property files (*.properties)", "*.properties");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        f = fileChooser.showSaveDialog(null);
        
        if (f != null) {
            s.Save(f.getAbsolutePath());
            return f;
        }
        
        return f;
    }
    
    private Alert generateAlert(String msg){
       Alert alert = new Alert(AlertType.WARNING, msg, ButtonType.OK);
       alert.showAndWait(); 
       return alert;
    }


    


}

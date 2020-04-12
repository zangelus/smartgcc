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
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
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

        
//    private Menu menuCompile;
//    private Menu menuLink;
//    private Menu menuDebug;
//    private Menu menuCodeGeneration;
//    private Menu menuDeveloperOptions;
//   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        s.Load();
        textfield2.setText(s.LAST_PATH_OPENED);
        s.LAST_GCC_COMMAND = getStringGccCommand(getGccCommand());
        textfield1.setText(s.LAST_GCC_COMMAND);
        
//        menuCompile.setVisible(true);
//        menuLink.setVisible(true);
//        menuDebug.setVisible(true);
//        menuCodeGeneration.setVisible(true);
//        menuDeveloperOptions.setVisible(true);
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
        }
        else{
            System.out.println("file not valid");
        } 
    }
    
    @FXML
    private void handleBtnBuild(ActionEvent event) {
        build();
    }

    @FXML
    private void handleBtnCancel(ActionEvent event) {
        listview2.getItems().add("Closing the app");
        Platform.exit();
    }

    private void handleMenuUserOptions(ActionEvent event) throws IOException {

        MenuItem mItem = (MenuItem) event.getSource();
        String menuItemName = mItem.getText();
        
        if ("user options".equalsIgnoreCase(menuItemName)) {
            
            Parent root = FXMLLoader.load(getClass().getResource("UserOptions.fxml"));
            Scene scene = new Scene(root);
            Stage window = (Stage) menuBar.getScene().getWindow();
            window.setScene(scene);
            window.show();
            
        } else if("build".equalsIgnoreCase(menuItemName)){
            build();
        } 
        else if("Set Default Options".equalsIgnoreCase(menuItemName)){
            s.defaultSettings();
        }  
    }
    
    private void build(){
        
        String[] cmd1 = getGccCommand();
        s.LAST_GCC_COMMAND = getStringGccCommand(cmd1);
        textfield1.setText(s.LAST_GCC_COMMAND);

        listview2.getItems().add("Start: Build a project using g++");

   
//        String[] cmd = new String[4];
//        cmd[0] = "g++";
//        cmd[1] = absolutePath;
//        cmd[2] = "-o";
//        cmd[3] = executableName;
//        
//        GccCommand gccCmd = new GccCommand();
//        int retValue = gccCmd.ExecutedCommand1(cmd);

        GccCommand gccCmd = new GccCommand();
        int retValue = gccCmd.ExecutedCommand1(cmd1);

        listview2.getItems().add("ExitValue: " + retValue);
        listview2.getItems().add(gccCmd.GetOutput());
        
        if(retValue != 0){
            listview2.getItems().add(gccCmd.GetError());
        }
        
        System.out.println("ExitValue: " + retValue);
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
        
        if(s.getBoolValue(s.OP_B_CREATE_OBJECT_FILE) && 
           !"".equals(s.getTextValue(s.OP_S_OUTPUT_FILE_NAME)))
        {
            comm.add("-c");
        }
        if(s.getBoolValue(s.OP_B_OUTPUT_FILE_NAME) && 
           !"".equals(s.getTextValue(s.OP_S_OUTPUT_FILE_NAME)))
        {
            comm.add("-o");
            outputName = s.getTextValue(s.OP_S_OUTPUT_FILE_NAME);
        }
        else{
            //use the same name as the main.c file.
            outputName = getNameOfMainFile();
        }   
        
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
        else if("Open Project".equalsIgnoreCase(menuItemName)){
            browse();
        }
        else if("Set Default Options".equalsIgnoreCase(menuItemName)){
            s.defaultSettings();
        } 
        else if("Select User type".equalsIgnoreCase(menuItemName)){
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
}

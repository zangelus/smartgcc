/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartgcc;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

/**
 *
 * @author zange
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    @FXML
    private Button button;
    @FXML
    private ListView<String> listview1;
    @FXML
    private TextField textfield1;
    
    private File MainFile;
    @FXML
    private ListView<String> listview2;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleBtnBrowse(ActionEvent event) {
        
        listview1.getItems().clear();
        
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("C File", "*.c"),new FileChooser.ExtensionFilter("C++ File", "*.cpp"));
        
        File sf = fc.showOpenDialog(null);
        if(sf != null){
            MainFile = sf;
            listview1.getItems().add(sf.getName());
            listview1.getItems().add(sf.getAbsolutePath());
        }
        else{
            System.out.println("file not valid");
        }
    }

    @FXML
    private void handleBtnBuild(ActionEvent event) {
        listview2.getItems().add("Start: Build a project using g++");

        String absolutePath = MainFile.getAbsolutePath();
        String parentDirectory = MainFile.getParent();
        String executableName = parentDirectory + "\\";

        if (textfield1.getText().equals("")) {
            executableName += MainFile.getName().replaceFirst("[.][^.]+$", "");
        } else {
            executableName += textfield1.getText();
        }
   
        String[] cmd = new String[4];
        cmd[0] = "g++";
        cmd[1] = absolutePath;
        cmd[2] = "-o";
        cmd[3] = executableName;
        
        GccCommand gccCmd = new GccCommand();
        int retValue = gccCmd.ExecutedCommand1(cmd);

        listview2.getItems().add("ExitValue: " + retValue);
        listview2.getItems().add(gccCmd.GetOutput());
        
        if(retValue != 0){
            listview2.getItems().add(gccCmd.GetError());
        }
        
        System.out.println("ExitValue: " + retValue);
    }

    @FXML
    private void handleBtnCancel(ActionEvent event) {
        listview2.getItems().add("Closing the app");
        Platform.exit();
    }
    
}

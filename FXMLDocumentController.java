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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
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
    @FXML
    private Menu menuCompile;
    @FXML
    private Menu menuLink;
    @FXML
    private Menu menuDebug;
    @FXML
    private Menu menuCodeGeneration;
    @FXML
    private Menu menuDeveloperOptions;
    @FXML
    private MenuBar menuBar;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        menuCompile.setVisible(true);
        menuLink.setVisible(true);
        menuDebug.setVisible(true);
        menuCodeGeneration.setVisible(true);
        menuDeveloperOptions.setVisible(true);
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
        build();
    }

    @FXML
    private void handleBtnCancel(ActionEvent event) {
        listview2.getItems().add("Closing the app");
        Platform.exit();
    }

    @FXML
    private void handleBtnSelectUser(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("UserSelection.fxml"));
        
        Scene scene = new Scene(root);
        
        //Stage window = ShareObject.getInstance().stage;
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @FXML
    private void menuCompileAction1(ActionEvent event) {
    }

    @FXML
    private void menuCompileAction2(ActionEvent event) {
    }

    @FXML
    private void menuLinkAction1(ActionEvent event) {
    }

    @FXML
    private void menuLinkAction2(ActionEvent event) {
    }

    @FXML
    private void handleBtnSelectUser(MouseEvent event) {
        
    }

    @FXML
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
    }
    
    private void build(){
        
        ArrayList<String> comm  = new ArrayList<>(); 
        SingletonApp s = SingletonApp.getInstance();
        
        String absolutePath = MainFile.getAbsolutePath();
        String parentDirectory = MainFile.getParent();
        String executableName = parentDirectory + "\\";

        if (textfield1.getText().equals("")) {
            executableName += MainFile.getName().replaceFirst("[.][^.]+$", "");
        } else {
            executableName += textfield1.getText();
        }
        
        comm.add("g++");
        comm.add(absolutePath);
        
        if(s.CheckBox100_1){
            comm.add("-c");
        }
        if(s.CheckBox100_2){
            comm.add("-o");
            comm.add(s.TextField100_2);
        }
        
        String[] cmd1 = new String[comm.size()];
        int i=0;
        
        for(Object o : comm.toArray())
        {
            cmd1[i++]= (String)o;
        }
        
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
}

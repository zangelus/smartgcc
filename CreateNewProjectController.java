/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartgcc;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author zange
 */
public class CreateNewProjectController implements Initializable {

    SingletonApp s = SingletonApp.getInstance();
    @FXML
    private TextField textfield1;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleBrowse(ActionEvent event) {
        browse();
    }

    @FXML
    private void handleSave(ActionEvent event) {

        File f = SaveProject();
        if(f!=null){
            s.CURRENT_OPEN_PROJECT = f.getAbsolutePath();
        }
        
         Button btn = (Button)event.getSource();
         Stage stage = (Stage) btn.getScene().getWindow();

         s.setNews();
         stage.close();        
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        
        Button btn = (Button)event.getSource();
        Stage stage = (Stage)btn.getScene().getWindow();
        stage.close();
    }
    
    private void browse(){
        textfield1.clear();
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
            textfield1.setText(s.LAST_PATH_OPENED);
            //load defaults settings into properties
            s.Load(s.defaultProjctFile);

            //set desired settings in the new project
            s.setTextValue(s.OP_S_LAST_PATH_OPENED, s.LAST_PATH_OPENED);
        }
        else{
            System.out.println("file not valid");
        } 
    }
    
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
       Alert alert = new Alert(Alert.AlertType.WARNING, msg, ButtonType.OK);
       alert.showAndWait(); 
       return alert;
    }
    
    private void goBack(){
        
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
            Scene scene = new Scene(root);
            Stage window = s.stage;
            window.fireEvent(new WindowEvent(window, WindowEvent.WINDOW_HIDDEN));
            window.setScene(scene);
            window.show();
        } catch (Exception e) {
            System.out.println("Error opening FXML window");
        }
    }
    
}

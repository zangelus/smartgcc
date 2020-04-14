/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartgcc;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author za
 */
public class UserSelectionController implements Initializable {

    SingletonApp s = SingletonApp.getInstance();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleNoviceBtn(ActionEvent event) {
        
        //page 4
        s.prop.setProperty(s.OP_B_D_checkBox400_1            , "true");
        s.prop.setProperty(s.OP_B_D_checkBox400_2            , "true");
        s.prop.setProperty(s.OP_B_D_checkBox400_3            , "true");
        s.prop.setProperty(s.OP_B_D_checkBox400_4            , "true");
        
        //page 5
        s.prop.setProperty(s.OP_B_D_checkBox500_1            , "true");
        s.prop.setProperty(s.OP_B_D_checkBox500_2            , "true");
        s.prop.setProperty(s.OP_B_D_checkBox500_3            , "true");
        s.prop.setProperty(s.OP_B_D_checkBox500_4            , "true");
        
        goToMainWindow(event, "Novice");
        
    }

    @FXML
    private void handleIntermediateBtn(ActionEvent event) {
        
        //page 4
        s.prop.setProperty(s.OP_B_D_checkBox400_1            , "false");
        s.prop.setProperty(s.OP_B_D_checkBox400_2            , "false");
        s.prop.setProperty(s.OP_B_D_checkBox400_3            , "false");
        s.prop.setProperty(s.OP_B_D_checkBox400_4            , "false");
        
        //page 5
        s.prop.setProperty(s.OP_B_D_checkBox500_1            , "true");
        s.prop.setProperty(s.OP_B_D_checkBox500_2            , "true");
        s.prop.setProperty(s.OP_B_D_checkBox500_3            , "true");
        s.prop.setProperty(s.OP_B_D_checkBox500_4            , "true");
        
        goToMainWindow(event, "Typical");
    }

    @FXML
    private void handleExpertBtn(ActionEvent event) {
        
        //page 4
        s.prop.setProperty(s.OP_B_D_checkBox400_1            , "false");
        s.prop.setProperty(s.OP_B_D_checkBox400_2            , "false");
        s.prop.setProperty(s.OP_B_D_checkBox400_3            , "false");
        s.prop.setProperty(s.OP_B_D_checkBox400_4            , "false");
        
        //page 5
        s.prop.setProperty(s.OP_B_D_checkBox500_1            , "false");
        s.prop.setProperty(s.OP_B_D_checkBox500_2            , "false");
        s.prop.setProperty(s.OP_B_D_checkBox500_3            , "false");
        s.prop.setProperty(s.OP_B_D_checkBox500_4            , "false");
        
        goToMainWindow(event, "Expert");
    }

    private void goToMainWindow(ActionEvent event,String title) {
        
        boolean succ = s.Save(s.CURRENT_OPEN_PROJECT);
        
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
            Scene scene = new Scene(root);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.setTitle("SmartGcc - " + title + " profile");
            window.show();
        } catch (IOException e) {
            System.out.println("Problems opening the main window");
        }
    }
    
    
    
}

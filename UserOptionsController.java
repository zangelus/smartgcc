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
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author zange
 */
public class UserOptionsController implements Initializable {

    boolean CheckBox100_1;
    boolean CheckBox100_2;
    boolean CheckBox300_1;
    String TextField100_2;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleCheckBox100_1(ActionEvent event) {
        CheckBox chkbItem = (CheckBox) event.getSource();
        CheckBox100_1 = !chkbItem.isDisable();
    }

    @FXML
    private void handleTextField100_2(ActionEvent event) {
        TextField txtFieldItem = (TextField) event.getSource();
        TextField100_2 = txtFieldItem.getText();
    }

    @FXML
    private void handleCheckBox100_2(ActionEvent event) {
        CheckBox chkbItem = (CheckBox) event.getSource();
        CheckBox100_2 = !chkbItem.isDisable();
    }

    @FXML
    private void handleCheckBox300_1(ActionEvent event) {
    }

    @FXML
    private void handleComboBox300_1(ActionEvent event) {
        CheckBox chkbItem = (CheckBox) event.getSource();
        CheckBox300_1 = !chkbItem.isDisable();
    }

    @FXML
    private void handleBtnApply(ActionEvent event) {
        SingletonApp s   = SingletonApp.getInstance();
        s.CheckBox100_1  = CheckBox100_1;
        s.CheckBox100_2  = CheckBox100_2;
        s.CheckBox300_1  = CheckBox300_1;
        s.TextField100_2 = TextField100_2;
        goBack();
    }

    @FXML
    private void handleBtnCancel(ActionEvent event) {
        goBack();
    }
    
    private void goBack(){
        
        SingletonApp s   = SingletonApp.getInstance();
        
        try{
            Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
            Scene scene = new Scene(root);
            //Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            Stage window = s.stage;
            window.setScene(scene);
            window.show();
        }
        catch(IOException e){
            
        }
    }
    
}

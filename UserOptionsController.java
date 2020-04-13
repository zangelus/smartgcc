/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartgcc;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author zange
 */
public class UserOptionsController implements Initializable {

    //check box in the UI
    boolean tCheckBox100_1;
    boolean tCheckBox100_2;
    boolean tCheckBox300_1;
    
    boolean tCheckBox400_1;
    boolean tCheckBox400_2;
    boolean tCheckBox400_3;
    boolean tCheckBox400_4;

    boolean tCheckBox500_1;
    boolean tCheckBox500_2;
    boolean tCheckBox500_3;
    boolean tCheckBox500_4;
    
    //text Files in the UI
    String tTextField100_2;
    
    //ComboBox in the UI
    String tComboBox300_1;
              
    @FXML
    private CheckBox checkBox100_1;
    @FXML
    private TextField textField100_2;
    @FXML
    private CheckBox checkBox100_2;
    @FXML
    private CheckBox checkBox300_1;
    @FXML
    private ComboBox<String> comboBox300_1;
    @FXML
    private CheckBox checkBox500_1;
    @FXML
    private CheckBox checkBox400_1;
    @FXML
    private CheckBox checkBox400_2;
    @FXML
    private CheckBox checkBox400_3;
    @FXML
    private CheckBox checkBox400_4;
    @FXML
    private CheckBox checkBox500_4;
    @FXML
    private CheckBox checkBox500_3;
    @FXML
    private CheckBox checkBox500_2;
    
    
    SingletonApp s = SingletonApp.getInstance();
    Properties change;
    /**
     * Initializes the controller class.
     * @param url
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        s.Load(s.CURRENT_OPEN_PROJECT);
       
       change = new Properties();

       //page 1
       tCheckBox100_1   = s.getBoolValue(s.OP_B_CREATE_OBJECT_FILE);
       tCheckBox100_2   = s.getBoolValue(s.OP_B_OUTPUT_FILE_NAME);
       
       //page2
       
       //page3
       tCheckBox300_1   = s.getBoolValue(s.OP_B_WARNING_PROFILE);
       
       //page4
        tCheckBox400_1   = s.getBoolValue(s.OP_B_V_checkBox400_1);
        tCheckBox400_2   = s.getBoolValue(s.OP_B_V_checkBox400_2);
        tCheckBox400_3   = s.getBoolValue(s.OP_B_V_checkBox400_3);
        tCheckBox400_4   = s.getBoolValue(s.OP_B_V_checkBox400_4);
        
       //page5
       tCheckBox500_1   = s.getBoolValue(s.OP_B_V_checkBox500_1);
       tCheckBox500_2   = s.getBoolValue(s.OP_B_V_checkBox500_2);
       tCheckBox500_3   = s.getBoolValue(s.OP_B_V_checkBox500_3);
       tCheckBox500_4   = s.getBoolValue(s.OP_B_V_checkBox500_4);
       
 
       
       tTextField100_2  = s.getTextValue(s.OP_S_OUTPUT_FILE_NAME);

       comboBox300_1.getItems()
               .addAll(s.cb300_1_option1,
                       s.cb300_1_option2,
                       s.cb300_1_option3,
                       s.cb300_1_option4);
       
       comboBox300_1.setValue(s.getTextValue(s.OP_S_WARNING_PROFILE));
       
       updateUI();
    }    

    private void updateUI() {
        
        //page1
        checkBox100_1.setSelected(tCheckBox100_1);
        
        //page2 
        checkBox100_2.setSelected(tCheckBox100_2);
        textField100_2.setText(tTextField100_2);
        
        //page3
        checkBox300_1.setSelected(tCheckBox300_1);
        
        //page4
        checkBox400_1.setSelected(tCheckBox400_1);
        checkBox400_2.setSelected(tCheckBox400_2);
        checkBox400_3.setSelected(tCheckBox400_3);
        checkBox400_4.setSelected(tCheckBox400_4);
        
        if(s.isOpeningUserOptions){
            checkBox400_1.setDisable(s.getBoolValue(s.OP_B_D_checkBox400_1));
            checkBox400_2.setDisable(s.getBoolValue(s.OP_B_D_checkBox400_2));
            checkBox400_3.setDisable(s.getBoolValue(s.OP_B_D_checkBox400_3));
            checkBox400_4.setDisable(s.getBoolValue(s.OP_B_D_checkBox400_4)); 
        }

        //page5
        checkBox500_1.setSelected(tCheckBox500_1);
        checkBox500_2.setSelected(tCheckBox500_2);
        checkBox500_3.setSelected(tCheckBox500_3);
        checkBox500_4.setSelected(tCheckBox500_4);
        
        if (s.isOpeningUserOptions) {
            checkBox500_1.setDisable(s.getBoolValue(s.OP_B_D_checkBox500_1));
            checkBox500_2.setDisable(s.getBoolValue(s.OP_B_D_checkBox500_2));
            checkBox500_3.setDisable(s.getBoolValue(s.OP_B_D_checkBox500_3));
            checkBox500_4.setDisable(s.getBoolValue(s.OP_B_D_checkBox500_4));
        }
        
    }
    
        
    @FXML
    private void handleCheckBox100_1(ActionEvent event) {
        CheckBox chkbItem = (CheckBox) event.getSource();
        tCheckBox100_1 = chkbItem.isSelected();
        change.setProperty(s.OP_B_CREATE_OBJECT_FILE, tCheckBox100_1?"true":"false");
    }

    @FXML
    private void handleCheckBox100_2(ActionEvent event) {
        CheckBox chkbItem = (CheckBox) event.getSource();
        tCheckBox100_2 = chkbItem.isSelected();
        change.setProperty(s.OP_B_OUTPUT_FILE_NAME, tCheckBox100_2?"true":"false");
    }
    
    private void handleCheckBox300_1(ActionEvent event) {
        CheckBox chkbItem = (CheckBox) event.getSource();
        tCheckBox300_1 = chkbItem.isSelected();
        change.setProperty(s.OP_B_WARNING_PROFILE, tCheckBox300_1?"true":"false");
    }
    
    private void handleTextField100_2(ActionEvent event) {
        TextField txtFieldItem = (TextField) event.getSource();
        tTextField100_2 = txtFieldItem.getText();
        change.setProperty(s.OP_S_OUTPUT_FILE_NAME, tTextField100_2);
    }


    private void handleComboBox300_1(ActionEvent event) {
        CheckBox chkbItem = (CheckBox) event.getSource();
        tCheckBox300_1 = !chkbItem.isDisable();
    }

    @FXML
    private void handleBtnApply(ActionEvent event) {
        
        SingletonApp s   = SingletonApp.getInstance();
        
        Set<String> keys = change.stringPropertyNames();
        for (String key : keys) {
           s.prop.setProperty(key, change.getProperty(key));
        }
        
        s.Save(s.CURRENT_OPEN_PROJECT);
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

    @FXML
    private void handleTextFieldChanged100_2(KeyEvent event) {
        TextField txtFieldItem = (TextField) event.getSource();
        tTextField100_2 = txtFieldItem.getText();
        change.setProperty(s.OP_S_OUTPUT_FILE_NAME, tTextField100_2);
    }

    @FXML
    private void handleCheckBoxAll(ActionEvent event) {
        
        CheckBox chkbItem = (CheckBox) event.getSource();
        
        if(chkbItem.idProperty().getValue().equals("checkBox300_1")){
            tCheckBox300_1 = chkbItem.isSelected();
            change.setProperty(s.OP_B_WARNING_PROFILE, tCheckBox300_1?"true":"false");
        }
        else if(chkbItem.idProperty().getValue().equals("checkBox500_1")){
            tCheckBox500_1 = chkbItem.isSelected();
            change.setProperty(s.OP_B_V_checkBox500_1, tCheckBox500_1?"true":"false");
        }
        
        
    }

    @FXML
    private void handleComboBoxAll(ActionEvent event) {
        
        ComboBox cmbBox = (ComboBox) event.getSource();
        
        if(cmbBox.idProperty().getValue().equals("comboBox300_1")){
            String selectedValue = cmbBox.getValue().toString();
            
            if (s.cb300_1_option1.equals(selectedValue)) {
                tComboBox300_1 = s.cb300_1_option1;
            }
            else if (s.cb300_1_option2.equals(selectedValue)) {
                tComboBox300_1 = s.cb300_1_option2;
            }
            else if (s.cb300_1_option3.equals(selectedValue)) {
                tComboBox300_1 = s.cb300_1_option3;
            }
            else if (s.cb300_1_option4.equals(selectedValue)) {
                tComboBox300_1 = s.cb300_1_option4;
            }
            
            change.setProperty(s.OP_S_WARNING_PROFILE, tComboBox300_1);
 
        }
    }


}

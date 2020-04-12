/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartgcc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author zange
 */

public class SmartGcc extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {

        
        SingletonApp s = SingletonApp.getInstance();
        s.stage = stage;
        Parent root;
        
        if(s.profileExist()){
             root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        }
        else{
            root = FXMLLoader.load(getClass().getResource("UserSelection.fxml"));
        }      
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}

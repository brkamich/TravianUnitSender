/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pupkaci.travianunitsender;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author molnaric
 */
public class LoginFXController implements Initializable {
    @FXML
    private TextField passwordField;
     @FXML
    private TextField serverField;
    @FXML
    private TextField usernameField;
    @FXML
    private Button loginButton;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    
    @FXML
    private void onLoginBtn(ActionEvent event) {
        loginButton.layoutXProperty().set(20.0);
        System.out.println("oh yeah, you clicked me!");
        MainApp.onLogin(usernameField.textProperty().get(),serverField.textProperty().get());
    }
}

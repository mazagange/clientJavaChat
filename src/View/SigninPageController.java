/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.ClientController;
import Model.User;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Mrawi
 */
public class SigninPageController implements Initializable{
    
    @FXML private Button signinBtn;
    @FXML private Hyperlink signupLink;
    @FXML private BorderPane myPane;
    @FXML private TextField email;
    @FXML private PasswordField pass;
    ClientController controller;
    @FXML
    void signinBtn_OnAction() {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("error");
                alert.setHeaderText(null);
                Pattern emailPattern = Pattern.compile("^[_A-Za-z0-9-]+(.[A-Za-z0-9-]+)*@[A-Za-z0-9-]+(.[A-Za-z0-9-]+)*(.[A-Za-z]{2,})$");
                Matcher match = emailPattern.matcher(email.getText());
                if(email.getText().equals("")){
                    alert.setContentText("email is required");
                    alert.showAndWait();
                }else if(!match.matches()){
                    alert.setContentText("please enter valid mail");
                    alert.showAndWait();
                }else if(pass.getText().equals("")){
                    alert.setContentText("password is required");
                    alert.showAndWait();
                }else if(controller.isExist(email.getText())){
                    User user= controller.getUserData(email.getText());
                    if(Password.checkPassword(pass.getText(), user.getPassword())){
                        controller.registerMe(user);
                        controller.changeView("profilePage");
                    }else{
                        alert.setContentText("wrong password");
                        alert.showAndWait();
                    }
                }else{
                    alert.setContentText("this email not registered");
                    alert.showAndWait();
                }
        
    } 
    @FXML
    void loadSignUp(ActionEvent event){

      controller.changeView("signupPage");
    
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        signinBtn.setDefaultButton(true);
    }    
    public void setCon(ClientController  con){
        this.controller= con;    
    }
}

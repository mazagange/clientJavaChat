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
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author Mrawi
 */
public class SignupPageController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML private Button signupBtn;
    @FXML private BorderPane signupPage;
    @FXML private DatePicker datePicker ;
    @FXML private BorderPane myPane;
    @FXML private Button cancelBtn;
    @FXML private TextField userName;
    @FXML private PasswordField passWord;
    @FXML private PasswordField cPassWord;
    @FXML private TextField email;
    @FXML private ComboBox myComboBox ;
    @FXML private RadioButton male;
    @FXML private RadioButton female;
     ToggleGroup gender;
    ObservableList<String> conutries = FXCollections.observableArrayList("choose a country","Egypt","USA","UAE");
    ClientController controller;
 
    
    @FXML
    void saveSuccess(){
        Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("error");
                alert.setHeaderText(null);
                Pattern emailPattern = Pattern.compile("^[_A-Za-z0-9-]+(.[A-Za-z0-9-]+)*@[A-Za-z0-9-]+(.[A-Za-z0-9-]+)*(.[A-Za-z]{2,})$");
                Matcher match = emailPattern.matcher(email.getText());
                if(userName.getText().equals("")){
                    alert.setContentText("user name is required");
                    alert.showAndWait();
                }else if(passWord.getText().equals("")){
                    alert.setContentText("password is required");
                    alert.showAndWait();
                }else if(!passWord.getText().equals(cPassWord.getText())){
                    alert.setContentText("confirmation password dosen't match");
                    alert.showAndWait();
                }else if(email.getText().equals("")){
                    alert.setContentText("email is required");
                    alert.showAndWait();
                }else if(!match.matches()){
                    alert.setContentText("please enter valid mail");
                    alert.showAndWait();
                }else if(myComboBox.getSelectionModel().getSelectedIndex() == 0){
                    alert.setContentText("country is required");
                    alert.showAndWait();
                }else if(datePicker.getValue()==null){
                    alert.setContentText("date is required");
                    alert.showAndWait();
                }else if(gender.getSelectedToggle() == null){
                    alert.setContentText("gender is required");
                    alert.showAndWait();
                }else if(controller.isExist(email.getText())){
                    alert.setContentText("email is registered on our system");
                    alert.showAndWait();
                }else{
                    controller.changeView("signinPage");
                    controller.insertUser(new User(0, userName.getText(), email.getText(), Password.hashPassword(passWord.getText()), myComboBox.getSelectionModel().getSelectedItem().toString(), datePicker.getValue().toString(), "avaliable", gender.getSelectedToggle().getUserData().toString(), "1"));
                    alert.setContentText("thanks for registeration");
                    alert.showAndWait();
                }
    }
     @FXML
    void cancelSuccess(){

      controller.changeView("signinPage");
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        signupBtn.setDefaultButton(true);
           myComboBox.getItems().addAll(conutries); 
           myComboBox.getSelectionModel().selectFirst();
          gender =new ToggleGroup();
          male.setUserData("male");
          male.setToggleGroup(gender);
          female.setUserData("female");
          female.setToggleGroup(gender);
           
    }
    //this Mothed to validate input and call isExist from controllaer
   /* public void validateForm(){
        
    }*/
     public void setCon(ClientController  con){
        this.controller= con;
        
    }
}

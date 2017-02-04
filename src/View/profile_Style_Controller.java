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
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
/**
 *
 * @author bo
 */
public class profile_Style_Controller implements Initializable {
    
    @FXML
    private Label label;
    @FXML private Pane listpane;
    @FXML private Label nameOfUser;
    ObservableList<String> friends = FXCollections.observableArrayList();
    ListView<String> listOfFriends= new ListView<>(friends);;
    
    @FXML private ComboBox comboStatus;
    @FXML ObservableList<String> states=FXCollections.observableArrayList("available","busy","away");
     
    ClientController controller;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

            // TODO
            comboStatus.getItems().addAll(states);
            comboStatus.getSelectionModel().selectFirst();
            
            listpane.getChildren().add(listOfFriends);
            
            listOfFriends.setOnMouseClicked((event) -> {
                
                if(event.getClickCount()==2){
                    String friend = listOfFriends.getSelectionModel()
                                                    .getSelectedItem();
                     controller.openChatWindow(friend);
                     System.out.println("click");
                    
                }
            });
            
    }
    public void setCon(ClientController  con){
        this.controller= con;
        
    }
    
    public void updateProfile(User user) throws RemoteException{
        ArrayList<User> friends1 = controller.getFriends(user.getId());
        HashMap<String,User> friends2 = new HashMap<>();
        friends1.forEach((u) -> {
            friends2.put(u.getUserName(), u);
        });
        controller.registerFriends(friends2);
        List<String> friendsNames = friends1.stream().map((t) ->t.getUserName()).collect(Collectors.toList());
        friends.clear();
        friends.addAll(friendsNames);
        
        nameOfUser.setText(user.getUserName());
        
        
}
    
    
}

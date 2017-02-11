/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.ClientController;
import Model.User;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
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
    ListView<String> listOfFriends= new ListView<>(friends);
    
    @FXML private ComboBox comboStatus;
    @FXML ObservableList<String> states=FXCollections.observableArrayList("available","busy","away");
      @FXML
    private ImageView ad;
    @FXML
    private MenuItem logout;

    @FXML
    private MenuItem exit;
     
    ClientController controller;

    public profile_Style_Controller(ClientController aThis) {
        this.controller = aThis;
    }
    
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
    
    @FXML
    void addFriendAction(ActionEvent event) {
        System.out.println("add friend");
        Dialog< String> dialog = new Dialog<>();
        dialog.setTitle("Add friend");
        dialog.setHeaderText("Send a friend request");

        // Set the icon (must be included in the project).
        //dialog.setGraphic(new ImageView(this.getClass().getResource("login.png").toString()));
        dialog.getDialogPane().getStyleClass().add("myDialog");
        // Set the button types.
        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);
        dialog.getDialogPane().setStyle("-fx-background-color: #488484;");
        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        //grid.setStyle("-fx-background-color: #488484;");
        TextField username = new TextField();
        username.setPromptText("Email");
        

        grid.add(new Label("Email:"), 0, 0);
        grid.add(username, 1, 0);


        // Enable/Disable login button depending on whether a username was entered.
        Node loginButton = dialog.getDialogPane().lookupButton(addButtonType);
        loginButton.setDisable(true);

        // Do some validation (using the Java 8 lambda syntax).
        username.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });
        
        dialog.setResultConverter(dialogButton -> {
        if (dialogButton == addButtonType) {
            return username.getText();
         }
            return null;
        });


        dialog.getDialogPane().setContent(grid);

        // Request focus on the username field by default.
        Platform.runLater(() -> username.requestFocus());

        Optional<String> result = dialog.showAndWait();
        
        if(result.isPresent()) {
            String email = result.get();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            Pattern emailPattern = Pattern.compile("^[_A-Za-z0-9-]+(.[A-Za-z0-9-]+)*@[A-Za-z0-9-]+(.[A-Za-z0-9-]+)*(.[A-Za-z]{2,})$");
            Matcher match = emailPattern.matcher(email);
            if(!match.matches()){
                alert.setContentText("please enter valid mail");
                alert.showAndWait();
            }else if(email.equals(controller.me.getEmail())){
                alert.setContentText("you are adding yourself :)");
                alert.showAndWait();
            }else {
                boolean friend=false;
                for(User u: controller.friends.values()){
                    if(u.getEmail().equals(email)) friend=true;
                }
                if(friend){
                    alert.setContentText("this user is your friend already");
                    alert.showAndWait();
                }else if(!controller.isExist(email)){
                    alert.setContentText("no user registered by this email");
                    alert.showAndWait();
                }else{
                    controller.addFriend(email);  
                    User fr = controller.getUserData(email);
                    controller.friends.put(fr.getUserName(), fr);
                }
            }
              
        }
            
        
    }
    
      @FXML
    void exit(ActionEvent event) {
        controller.exit();
    }

    @FXML
    void logout(ActionEvent event) {
        controller.logout();
    }

    public void recieveAdv(byte[] image) {
        ByteArrayInputStream st = new ByteArrayInputStream(image);
        Image img = new Image(st);
        ad.setImage(img);
    }
    
    
}

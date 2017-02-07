package Controller;

import ChatWindow.ChatWindow;
import Model.Massage;
import Model.Model;
import Model.ServerInterface;
import Model.User;
import View.SigninPageController;
import View.SignupPageController;
import View.profile_Style_Controller;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ClientController extends Application {

    SigninPageController viewRefrance;
    ServerInterface ServerIntRef;
    private Scene signinScene;
    private Scene signupScene;
    private Scene profileScene;
    private SignupPageController signUpController;
    private SigninPageController signInController;
    private profile_Style_Controller profileController;
    private Stage myStage;
    public User me;
    public HashMap<String,User> friends;
    public HashMap<Integer,ChatWindow> chatWindows= new HashMap<>();
    Model model;
    Registry reg = LocateRegistry.getRegistry("127.0.0.1");
    public static void main(String[] args) {
        
            launch(args);
    }

    
    @Override
    public void init() throws Exception {

    }

    @Override
    public void start(Stage stage) throws Exception {
        myStage = stage;
        
       stage.setOnCloseRequest((event) -> {
           unRegister();
       });
        signInController = new SigninPageController(this);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setController(signInController);
        Parent signin = fxmlLoader.load(getClass().getResource("/View/signinPage.fxml").openStream());
        signinScene = new Scene(signin);
        
        
        signUpController = new SignupPageController(this);
        FXMLLoader fxmlLoader2 = new FXMLLoader();
        fxmlLoader2.setController(signUpController);
        Parent signup = fxmlLoader2.load(getClass().getResource("/View/signupPage.fxml").openStream());
        signupScene = new Scene(signup);
        
        profileController = new profile_Style_Controller(this);
        FXMLLoader fxmlLoader3 = new FXMLLoader();
        fxmlLoader3.setController(profileController);
        Parent profile = fxmlLoader3.load(getClass().getResource("/View/profile_Style.fxml").openStream());
        profileScene = new Scene(profile);
        
        
        
        stage.getIcons().add(new Image("images/chatting.png"));
        stage.setResizable(false);
        stage.setScene(signinScene);
        stage.setTitle("Connect me");
        stage.show();
        
        
        

    }

    public ClientController() throws RemoteException {

        
        try {
            
            ServerIntRef = (ServerInterface) reg.lookup("chatserver");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
         model = new Model(this);
         
    }
    
    public void register(){
        try {
            
            ServerIntRef.registerUser(me.getId(), model);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void unRegister(){
        try {
            if(me !=null){
                ServerIntRef.unRegisterUser(me.getId(), model);
            }
            Platform.exit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //those all method to send data to user
    public boolean isExist(String email) {
        boolean exist = true;
        try {
            if(ServerIntRef == null){
                ServerIntRef = (ServerInterface) reg.lookup("chatserver");
            }
            exist = ServerIntRef.isExist(email);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return exist;
    }

    public int insertUser(User newUser) {
        int result = 0;
        try {

            result = ServerIntRef.insertUser(newUser);

        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public User getUserData(String email) {
        User user = null;
        try {

            user = ServerIntRef.getUserData(email);

        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
        return user;
    }

    public int updateUser(User updatedUser) throws RemoteException {
        return ServerIntRef.updateUser(updatedUser);
    }

    public int updateStatus(int id, String status) throws RemoteException {
        return ServerIntRef.updateStatus(id, status);
    }

    public int updateOnline(int id, String onlineStatus) throws RemoteException {
        return ServerIntRef.updateOnline(id, onlineStatus);
    }

    public ArrayList<User> getFriends(int id) throws RemoteException {
        return ServerIntRef.getFriends(id);
    }

    public void changeView(String view) {
        switch (view) {
            case "signinPage":
                myStage.setScene(signinScene);
                break;
            case "signupPage":
                myStage.setScene(signupScene);
                break;
            case "profilePage":
                updateProfile(me);
                myStage.setScene(profileScene);
                checkForFriendRequests();
                break;
        }

    }
    public void registerMe(User me){
        this.me = me;
    }
    public void registerFriends(HashMap<String,User> friends){
        this.friends = friends;
    }
    
    public void updateProfile(User me){
        try {
            profileController.updateProfile(me);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }

    public void openChatWindow(String friend) {
        
        User f = friends.get(friend);
        if(!chatWindows.containsKey(f.getId())){
            ChatWindow c = new ChatWindow(me,f,this);
            chatWindows.put(f.getId(), c);
        }
    }

    public void sendMassage(Massage msg, User from, User to) {
        try {
            ServerIntRef.sendToUser(msg, from.getId(), to.getId());
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }
    
    public void recieveMassage(Massage msg,User from){
        if(chatWindows.containsKey(from.getId())){
            Platform.runLater(() -> {
                chatWindows.get(from.getId()).recieveMassage(msg);
            });
        }else{
            Platform.runLater(
                () -> {
                    // Update UI here.
                    ChatWindow c = new ChatWindow(me, from, this);
                    chatWindows.put(from.getId(), c);
                    c.recieveMassage(msg);
                  }
                );
            
        }
    }

    public void addFriend(String email) {
        try {
            ServerIntRef.addFriend(me.getId(),email,"friends");
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }
    
    public void checkForFriendRequests(){
        try{
            ArrayList<User> friendsRequests = ServerIntRef.getFriendsRequests(me.getId());
            friendsRequests.forEach((f) -> {
                     Alert alert = new Alert(AlertType.CONFIRMATION);
                     alert.setTitle("Friend Request");
                     alert.setHeaderText(f.getUserName() + " want to be your friend :");
                     alert.setContentText("Choose your option.");

                     ButtonType buttonTypeOne = new ButtonType("Acccept");
                     ButtonType buttonTypeCancel = new ButtonType("Refuse", ButtonData.CANCEL_CLOSE);

                     alert.getButtonTypes().setAll(buttonTypeOne,  buttonTypeCancel);

                     Optional<ButtonType> result = alert.showAndWait();
                     if (result.get() == buttonTypeOne){
                         // ... user chose "Accept"
                         try{
                            ServerIntRef.acceptFriend(me.getId(), f.getId());
                            profileController.updateProfile(me);
                         } catch(Exception ex){
                             ex.printStackTrace();
                         }
                     } else {
                         // ... user chose CANCEL or closed the dialog
                         try{
                            ServerIntRef.refuseFriend(me.getId(), f.getId());
                         } catch(Exception ex){
                             ex.printStackTrace();
                         }
                     } 
            });
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void goOff(String reason) {
        Platform.runLater(() -> {
            changeView("signinPage");
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("error");
            alert.setHeaderText(null);
            alert.setContentText(reason);
            alert.show();
            ServerIntRef = null;
        });
        
    }
}

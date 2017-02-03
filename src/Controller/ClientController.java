package Controller;

import ChatWindow.ChatWindow;
import View.SigninPageController;
import View.SignupPageController;
import View.profile_Style_Controller;
import Model.ServerInterface;
import Model.User;
import View.SigninPageController;
import View.SigninPageController;
import View.SignupPageController;
import View.SignupPageController;
import View.profile_Style_Controller;
import View.profile_Style_Controller;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    User me;
    HashMap<String,User> friends;
    public HashMap<User,ChatWindow> chatWindows= new HashMap<User, ChatWindow>();
    public static void main(String[] args) {
        
            launch(args);
    }

    
    @Override
    public void init() throws Exception {

    }

    @Override
    public void start(Stage stage) throws Exception {
        myStage = stage;
        
       
        
        
        
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent signin = fxmlLoader.load(getClass().getResource("/View/signinPage.fxml").openStream());
        signinScene = new Scene(signin);
        signInController = fxmlLoader.getController();
        signInController.setCon(this);
        
        FXMLLoader fxmlLoader2 = new FXMLLoader();
        Parent signup = fxmlLoader2.load(getClass().getResource("/View/signupPage.fxml").openStream());
        signupScene = new Scene(signup);
        signUpController = fxmlLoader2.getController();
        signUpController.setCon(this);
        
        FXMLLoader fxmlLoader3 = new FXMLLoader();
        Parent profile = fxmlLoader3.load(getClass().getResource("/View/profile_Style.fxml").openStream());
        profileScene = new Scene(profile);
        profileController = fxmlLoader3.getController();
        profileController.setCon(this);
        
        
        

        stage.setScene(signinScene);
        stage.setTitle("Connect me");
        stage.show();
        
        
        

    }

    public ClientController() throws RemoteException {

        Registry reg = LocateRegistry.getRegistry("127.0.0.1");

        try {
            ServerIntRef = (ServerInterface) reg.lookup("chatserver");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    //those all method to send data to user
    public boolean isExist(String email) {
        boolean exist = true;
        try {
            exist = ServerIntRef.isExist(email);
        } catch (RemoteException ex) {
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
        if(!chatWindows.containsKey(f)){
            ChatWindow c = new ChatWindow(me,f,this);
            chatWindows.put(f, c);
        }
    }
}

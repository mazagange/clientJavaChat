/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChatWindow;

import ChatWindow.View.FXMLDocumentController;
import Controller.ClientController;
import Model.Massage;
import Model.User;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
public class ChatWindow {
    
    User from;
    User to;
    ClientController controller;
    FXMLDocumentController chatWindowcontroller;
    Stage stage;

    public ChatWindow(User me, User f,ClientController controller) {
        this.from = me;
        this.to = f;
        this.controller = controller;
        Stage chat = new Stage();
        start(chat);
        
    }
    
    public void start(Stage stage) {
        try {
            this.stage = stage;
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("/ChatWindow/View/FXMLDocument.fxml").openStream());
            chatWindowcontroller = fxmlLoader.getController();
            chatWindowcontroller.setCon(this,from);
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setTitle("chat with "+ to.getUserName());
            stage.setScene(scene);
            stage.show();
            
            stage.setOnCloseRequest((event) -> {
                controller.chatWindows.remove(to.getId());
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    

    public void recieveMassage(Massage msg){
        chatWindowcontroller.showMasssage(msg, to);
    }

    public void sendMassage(Massage msg) {
        controller.sendMassage(msg,from,to);
    }
    
    public void openChooser(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("choose File to send");
        File file = fileChooser.showOpenDialog(stage);
        if(file != null){
            controller.requestSendFile(file,to);
        }
        
    }
    
    public File acceptRecieveFile(File f) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save file");
        fileChooser.setInitialFileName(f.getName());
        
        File file = fileChooser.showSaveDialog(stage);
        return file;
    }

    public void showSystemMessage(String msg) {
        Platform.runLater(() -> {
            chatWindowcontroller.showSystemMasssage(msg);
        });
        
    }
  
    
    
}

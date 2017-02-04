/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChatWindow.View;

import ChatWindow.ChatWindow;
import Controller.ClientController;
import Model.Massage;
import Model.TextColor;
import Model.TextFont;
import Model.User;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableIntegerValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 *
 * @author BOSHA
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    @FXML private TextArea chatArea;
    @FXML private TextArea sendArea;
    @FXML private ColorPicker color;
    @FXML private ComboBox<Integer> fontSize;
    ObservableList fontlist =  FXCollections.observableArrayList(10,12,14,16,18,20);
    ChatWindow chatWindow; 
    @FXML private Button sendBtn;
    User me;
    

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        fontSize.getItems().addAll(fontlist); 
        fontSize.getSelectionModel().selectFirst();
        color.setValue(Color.BLUE);
        sendBtn.setOnAction((event) -> {
            if(!sendArea.getText().equals("")){
                Massage msg = new Massage(sendArea.getText(), new TextColor(color.getValue().getRed(),color.getValue().getGreen(),color.getValue().getBlue()), new TextFont(fontSize.getSelectionModel().getSelectedItem().doubleValue()));
                chatWindow.sendMassage(msg);
                showMasssage(msg, me);
                sendArea.clear();
            }
        });
    }    
    public void saveChat(ActionEvent a){
        
    }
    public void chooseFile(ActionEvent a){
        
    }
    
    public void showMasssage(Massage msg,User from){
        chatArea.setStyle( "-fx-text-fill: Black;");
        chatArea.setFont(Font.getDefault());
        chatArea.appendText(from.getUserName()+" : ");
        chatArea.setStyle( "-fx-text-fill: " + toRgbString(msg.getColor()) + ";");
        chatArea.setFont(new Font(msg.getFont().getSize()));
        chatArea.appendText(msg.getContent()+"\n");
    }
    
    private String toRgbString(TextColor c) {
        return "rgb("
                          + to255Int(c.getRed())
                    + "," + to255Int(c.getGreen())
                    + "," + to255Int(c.getBlue())
             + ")";
    }
    
    private int to255Int(double d) {
        return (int) (d * 255);
    }
    
    
    
    public void setCon(ChatWindow  con,User me){
        this.chatWindow= con;
        this.me=me;
        
    }
    
}

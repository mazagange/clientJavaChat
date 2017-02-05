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
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableIntegerValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 *
 * @author BOSHA
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    @FXML private ScrollPane scroll;
    @FXML private TextFlow chatArea;
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
        chatArea.setStyle("-fx-background-color: white;");
        fontSize.getItems().addAll(fontlist); 
        fontSize.getSelectionModel().selectFirst();
        color.setValue(Color.BLUE);
        sendArea.setOnKeyPressed((event) -> {
            if(event.getCode() == KeyCode.ENTER){
                send();
                event.consume();
            }
        });
        sendBtn.setDefaultButton(true);
        sendBtn.setOnAction((event) -> {
            send();
        });
        
    }

    private void send(){
        if(!sendArea.getText().equals("")){
                Massage msg = new Massage(sendArea.getText(), new TextColor(color.getValue().getRed(),color.getValue().getGreen(),color.getValue().getBlue()), new TextFont(fontSize.getSelectionModel().getSelectedItem().doubleValue()));
                chatWindow.sendMassage(msg);
                showMasssage(msg, me);
            }
        sendArea.setText("");
        
    }
    public void saveChat(ActionEvent a){
        
    }
    public void chooseFile(ActionEvent a){
        
    }
    
    public void showMasssage(Massage msg,User from){
        Text text = new Text(from.getUserName()+" : ");
        
        text.setFill(new Color(0, 0, 0, 1));
        text.setFont(Font.getDefault());
        
        chatArea.getChildren().add(text);
        Text text2 = new Text(msg.getContent()+"\n");
        text2.setFill(new Color(msg.getColor().getRed(), msg.getColor().getGreen(), msg.getColor().getBlue(), 1));
        text2.setFont(new Font(msg.getFont().getSize()));
        chatArea.getChildren().add(text2);
        
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

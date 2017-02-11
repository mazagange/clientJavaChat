/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.ClientController;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.paint.Paint;
import javafx.util.Duration;
import tray.notification.TrayNotification;

/**
 *
 * @author ahmed mohsen
 */
public class Model extends UnicastRemoteObject implements ClientInterface {

    private final ClientController controller;

    public Model(ClientController controller) throws RemoteException {
        this.controller = controller;
    }

    @Override
    public void recieve(Massage msg, User from) throws RemoteException {
        controller.recieveMassage(msg, from);
    }

    @Override
    public void recieveFromGroup(Massage msg, Group group) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void notifyOnline(User user) throws RemoteException {
        Platform.runLater(() -> {
            TrayNotification tray = new TrayNotification();
            tray.setTitle("Connect Me Alert");
            tray.setMessage(user.getUserName() + " Is now Online");
            tray.setRectangleFill(Paint.valueOf("#488484"));
            tray.showAndDismiss(Duration.seconds(4));
        });

    }

    @Override
    public void notifyOffline(User user) throws RemoteException {
        Platform.runLater(() -> {
            TrayNotification tray = new TrayNotification();
            tray.setTitle("Connect Me Alert");
            tray.setMessage(user.getUserName() + " Is now Offline");
            tray.setRectangleFill(Paint.valueOf("#488484"));
            tray.showAndDismiss(Duration.seconds(4));
        });

    }

    

    @Override
    public void notifyChangeStatus(User user, String status) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void checkForRequests() throws RemoteException {
        controller.checkForFriendRequests();
    }

    @Override
    public void goOff(String reason) throws RemoteException {
        controller.goOff(reason);
    }

    public boolean RecieveData(File f, byte[] data, int len) throws RemoteException {
        try {

            f.createNewFile();
            FileOutputStream out = new FileOutputStream(f, true);
            out.write(data, 0, len);
            out.flush();
            out.close();
            System.out.println("Done writing data...");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean sendData(File source, File dest, ClientInterface c) throws RemoteException {
        try {
            
            FileInputStream in = new FileInputStream(source);
            byte[] mydata = new byte[1024 * 1024];
            int mylen = in.read(mydata);
            while (mylen > 0) {
                boolean RecieveData = c.RecieveData(dest, mydata, mylen);
                mylen = in.read(mydata);
            }
            c.recieveSystemMassage("file recieve complete",controller.me);
            
        } catch (Exception e) {
            e.printStackTrace();

        }

        return true;
    }

    public File acceptRecieveFile(File f, User from) throws RemoteException {
        File file = null;
        try {
            file = controller.acceptRecieveFile(f, from);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return file;
    }

    @Override
    public void recieveSystemMassage(String file_recieve_complete,User from) throws RemoteException {
        controller.showSystemMassage(file_recieve_complete,from);
    }

    @Override
    public void addFriendToList(User friend) throws RemoteException {
        controller.addFriendToList(friend);
    }

    @Override
    public void recieveAdv(byte[] image) throws RemoteException {
        controller.recieveAdv(image);
    }

}

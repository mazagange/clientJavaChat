/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.ClientController;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author ahmed mohsen
 */
public class Model extends UnicastRemoteObject implements ClientInterface{

    private final ClientController controller;
    
    public Model(ClientController controller)throws RemoteException{
        this.controller= controller;
    }
    
    @Override
    public void recieve(Massage msg, User from)throws RemoteException {
        controller.recieveMassage(msg, from);
    }

    @Override
    public void recieveFromGroup(Massage msg, Group group)throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void notifyOnline(User user)throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void notifyOffline(User user)throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void recieveAdv(String adv)throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void notifyChangeStatus(User user, String status)throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void checkForRequests() throws RemoteException {
        controller.checkForFriendRequests();
    }
    
    
    
}

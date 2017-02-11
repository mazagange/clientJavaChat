
package Model;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;


public interface ClientInterface extends Remote{
    
    void recieve(Massage msg,User from)throws RemoteException;
    void recieveFromGroup(Massage msg,Group group)throws RemoteException;
    void notifyOnline(User user)throws RemoteException;
    void notifyOffline(User user)throws RemoteException;
    void recieveAdv(byte[] image)throws RemoteException;
    void notifyChangeStatus(User user,String status)throws RemoteException;
    void checkForRequests()throws RemoteException;

    public void goOff(String you_are_signed_in_from_another_location)throws RemoteException;
    public boolean sendData(File source,File dest,ClientInterface c) throws RemoteException;
    public boolean RecieveData(File f, byte[] data, int len) throws RemoteException;
    public File acceptRecieveFile(File f,User from)throws RemoteException;

    public void recieveSystemMassage(String file_recieve_complete,User from)throws RemoteException;

    public void addFriendToList(User friend)throws RemoteException;
}

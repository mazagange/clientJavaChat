
package Model;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface ClientInterface extends Remote{
    
    void recieve(Massage msg,User from)throws RemoteException;
    void recieveFromGroup(Massage msg,Group group)throws RemoteException;
    void notifyOnline(User user)throws RemoteException;
    void notifyOffline(User user)throws RemoteException;
    void recieveAdv(String adv)throws RemoteException;
    void notifyChangeStatus(User user,String status)throws RemoteException;
    void checkForRequests()throws RemoteException;
}

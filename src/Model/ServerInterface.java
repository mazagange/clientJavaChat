package Model;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ServerInterface extends Remote {

    public boolean isExist(String email) throws RemoteException;

    public User getUserData(String email) throws RemoteException;

    public int insertUser(User bean) throws RemoteException;

    public int updateUser(User bean) throws RemoteException;

    public int updateStatus(int id, String status) throws RemoteException;

    public int updateOnline(int id, String onlineStatus) throws RemoteException;

    public ArrayList<User> getFriends(int id) throws RemoteException;
    
    public void registerUser(int id,ClientInterface client);
    
    public void unRegisterUser(int id,ClientInterface client);
    
    public void sendToUser(Massage msg,int fromId,int toId);
    
    public void notifyChangeStatus(User user,String status);
    
    public void registerGroup(Group group);
    
    public void unRegisterGroup(User user,Group group);
    
    public void sendToGroup(Massage msg,Group group);
}

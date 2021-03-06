package Model;

import java.io.File;
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
    
    public ArrayList<User> getFriendsRequests(int id) throws RemoteException;
    
    public void registerUser(int id,ClientInterface client)throws RemoteException;
    
    public void unRegisterUser(int id,ClientInterface client)throws RemoteException;
    
    public void sendToUser(Massage msg,int fromId,int toId)throws RemoteException;
    
    public void notifyChangeStatus(User user,String status)throws RemoteException;
    
    public void registerGroup(Group group)throws RemoteException;
    
    public void unRegisterGroup(User user,Group group)throws RemoteException;
    
    public void sendToGroup(Massage msg,Group group)throws RemoteException;

    public void addFriend(int id,String emailOfFriend,String Cat) throws RemoteException;
    
    public void acceptFriend(int id, int friendId) throws RemoteException;

    public void refuseFriend(int id, int friendId)throws RemoteException;

    public ClientInterface requestSendFile(File file, User to)throws RemoteException;
    public void RequestLatestAd(User user)throws RemoteException;
}

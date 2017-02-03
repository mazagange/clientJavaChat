
package Model;

import java.rmi.Remote;


public interface ClientInterface extends Remote{
    
    void recieve(Massage msg,User from);
    void recieveFromGroup(Massage msg,Group group);
    void notifyOnline(User user);
    void notifyOffline(User user);
    void recieveAdv(String adv);
    void notifyChangeStatus(User user,String status);
    
}

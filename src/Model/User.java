package Model;
import java.io.Serializable;

/**
 *
 * @author ahmed mohsen
 */
public class User implements Serializable
{
    private int id;
    private String userName;
    private String email;
    private String password; 
    private String country;
    private String birthDate;
    private String status;
    private String gender;
    private String onlineStatus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(String onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public User(int id, String userName, String email, String password, String country, String birthDate, String status, String gender, String onlineStatus) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.country = country;
        this.birthDate = birthDate;
        this.status = status;
        this.gender = gender;
        this.onlineStatus = onlineStatus;
    }

 
    
}
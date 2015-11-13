package team33.cmu.com.runningman.entities;

/**
 * Created by d on 11/13/15.
 */
public class User {
    private static User user;
    private String name;
    private String password;

    private User(){}

    public static User getUser(){
        if(user == null){
            user = new User();
        }
        return user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean authenticate(){
        // TODO: 11/13/15
        return false;
    }
}

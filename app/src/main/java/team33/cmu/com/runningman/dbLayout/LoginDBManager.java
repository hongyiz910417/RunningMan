package team33.cmu.com.runningman.dbLayout;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import team33.cmu.com.runningman.entities.User;

/**
 * Team33
 * RunningMan
 * Date: 12/10/15
 **/
public class LoginDBManager extends JDBCAdapter {
    public LoginDBManager(){
        super();
    }

    public boolean login (User user){
        try {
           String query = "select * from user_info where username ='"+ user.getName() +"' and password = '"+
                   user.getPassword() + "';";
            ResultSet rs = this.statement.executeQuery(query);
            while (rs.next()) {
                System.out.println("login success:" );
                System.out.println(rs.getString(1));
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}

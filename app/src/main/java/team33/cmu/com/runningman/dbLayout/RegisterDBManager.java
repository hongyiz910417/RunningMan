package team33.cmu.com.runningman.dbLayout;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Team33
 * RunningMan
 * Date: 12/10/15
 **/
public class RegisterDBManager extends JDBCAdapter{
    public RegisterDBManager() {
        super();
    }

    public boolean insertUser(String username, String password, byte[] photo)  {
        try {
            String query = "select * from user_info where username ='"+ username +"';";
            ResultSet rs = this.statement.executeQuery(query);
            if (rs.next()) {
                return false;
            }else{
                System.out.println(photo.length);
                System.out.println(password.length());
                PreparedStatement updateemp = connection.prepareStatement
                        ("insert into user_info values(?,?,?)");
                updateemp.setString(1,username);
                updateemp.setString(2, password);
                updateemp.setBytes(3, photo);
                updateemp.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

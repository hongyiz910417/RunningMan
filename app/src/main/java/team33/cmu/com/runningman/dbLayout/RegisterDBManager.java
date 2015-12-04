package team33.cmu.com.runningman.dbLayout;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by d on 11/13/15.
 */
public class RegisterDBManager extends JDBCAdapter{
    public RegisterDBManager() {
        super();
    }

    public void insertUser(String username, String password, byte[] photo) throws SQLException {
        try {
            System.out.println(photo.length);
            System.out.println(password.length());
            PreparedStatement updateemp = connection.prepareStatement
                    ("insert into user_info values(?,?,?)");
            updateemp.setString(1,username);
            updateemp.setString(2, password);
            updateemp.setBytes(3, photo);
            updateemp.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

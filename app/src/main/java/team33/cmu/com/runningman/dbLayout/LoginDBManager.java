package team33.cmu.com.runningman.dbLayout;

import java.sql.SQLException;

/**
 * Created by d on 11/13/15.
 */
public class LoginDBManager extends JDBCAdapter{
    public LoginDBManager() {
        super();
    }

    public void insertUser(String usrname, String password) throws SQLException {
        String sql = "insert into user_info values ('" + usrname + "','" + password+ "');";
        System.out.println(sql);
        this.statement.executeUpdate(sql);
    }
}

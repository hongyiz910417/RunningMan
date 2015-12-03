package team33.cmu.com.runningman.dbLayout;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import team33.cmu.com.runningman.entities.LadderEntry;

/**
 * @author Qinyu Tong:<qtong@andrew.cmu.edu>
 * @since Wed Dec  2 21:19:48 EST 2015
 * */
public class RunnerLadderDBMananger extends JDBCAdapter{
    public RunnerLadderDBMananger() {
        super();
    }

    public void addMilesToUser(String username, double miles) throws SQLException {
        try {
            String sql = "select distance from runner_ladder where username = '" + username + "';";
            System.out.println(sql);
            ResultSet rs = this.statement.executeQuery(sql);
            if (rs.next()) {
                double distance = rs.getDouble("distance");
                distance += miles;
                sql = "update runner_ladder set distance = '" + distance
                        + "' where username = '" + username
                        + "';";
                System.out.println(sql);
                this.statement.executeUpdate(sql);
            }
            else {
                sql = "insert into runner_ladder values ('" + username + "','" + miles+ "');";
                System.out.println(sql);
                this.statement.executeUpdate(sql);
            }

            System.out.println(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * return top k runners according to their total running distance
     * @param k
     * @return list of ladder entries
     */
    public List<LadderEntry> getTopUsers(int k) throws SQLException{
        List<LadderEntry> result = new LinkedList<>();
        try {
            String sql = "select * from runner_ladder order by distance DESC";
            System.out.println(sql);
            ResultSet rs = this.statement.executeQuery(sql);
            int count = 0;
            while (rs.next() && count < k) {
                String username = rs.getString("username");
                double distance = rs.getDouble("distance");
                LadderEntry ladderEntry = new LadderEntry(username,distance);
                result.add(ladderEntry);
                count++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}

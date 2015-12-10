package team33.cmu.com.runningman.dbLayout;

import com.google.android.gms.maps.model.LatLng;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import team33.cmu.com.runningman.entities.Summary;
import team33.cmu.com.runningman.entities.User;
import team33.cmu.com.runningman.utils.ConvertUtil;
/**
 * Team33
 * RunningMan
 * Date: 12/10/15
 **/
public class SummaryDBManager extends JDBCAdapter{
    public SummaryDBManager(){
        super();
    }

    public void insertSummary(Summary summary) throws SQLException{
        String sql = "insert into run_summary(username, start_date, end_date"
                + ", distance, pace, route, summary_name) values (?, ?, ?, ?, ?, ?, ?)";
        this.connection.setAutoCommit(false);
        PreparedStatement ps = this.connection.prepareStatement(sql);
        ps.setString(1, User.getUser().getName());
        ps.setDate(2, new java.sql.Date(summary.getStartDate().getTime()));
        ps.setDate(3, new java.sql.Date(summary.getEndDate().getTime()));
        System.out.println(summary.getDuration());
        ps.setDouble(4, summary.getDistance());
        System.out.println(summary.getPace());
        ps.setDouble(5, summary.getPace());
        byte[] bytes = ConvertUtil.LatLngListToBytes(summary.getRoute());
        ps.setBytes(6, bytes);
        ps.setString(7, summary.getName());
        ps.executeUpdate();
        this.connection.commit();
    }

    public List<Summary> getSummariesByUsername(String username) throws SQLException{
        List<Summary> summaries = new ArrayList<Summary>();
        String sql = "SELECT * FROM run_summary where username = '" + username + "'";
        ResultSet rs = statement.executeQuery(sql);
        while(rs.next()){
            List<LatLng> latLngList = ConvertUtil.BytesToLatLngList(rs.getBytes("route"));

            Summary summary = new Summary(rs.getInt("summary_id"), latLngList, rs.getString("summary_name")
                    , rs.getDouble("pace"), rs.getDouble("distance")
                    , new java.util.Date(rs.getDate("start_date").getTime())
                    , new java.util.Date(rs.getDate("end_date").getTime()));
            summaries.add(summary);
        }
        rs.close();
        System.out.println("summary size: " + summaries.size());
        return summaries;
    }
    
    public Summary getSummaryById(Integer summaryId) throws SQLException{
        String sql = "SELECT * FROM run_summary where summary_id = " + summaryId;
        ResultSet rs = statement.executeQuery(sql);
        Summary summary = null;
        if(rs.next()){
            List<LatLng> latLngList = ConvertUtil.BytesToLatLngList(rs.getBytes("route"));

            summary = new Summary(rs.getInt("summary_id"), latLngList, rs.getString("summary_name")
                    , rs.getDouble("pace"), rs.getDouble("distance")
                    , new java.util.Date(rs.getDate("start_date").getTime())
                    , new java.util.Date(rs.getDate("end_date").getTime()));
        }
        rs.close();
        return summary;
    }
}

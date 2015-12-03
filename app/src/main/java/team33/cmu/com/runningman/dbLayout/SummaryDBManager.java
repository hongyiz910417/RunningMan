package team33.cmu.com.runningman.dbLayout;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import team33.cmu.com.runningman.entities.Summary;
import team33.cmu.com.runningman.entities.User;
import team33.cmu.com.runningman.utils.ConvertUtil;

/**
 * Created by d on 11/13/15.
 */
public class SummaryDBManager extends JDBCAdapter{
    public SummaryDBManager(){
        super();
    }

    public void insertSummary(Summary summary) throws SQLException{
        String sql = "insert into run_summary(username, start_date, end_date"
                + ", distance, pace, route) values (?, ?, ?, ?, ?, ?)";
        this.connection.setAutoCommit(false);
        PreparedStatement ps = this.connection.prepareStatement(sql);
        ps.setString(1, User.getUser().getName());
        ps.setDate(2, new java.sql.Date(summary.getStartDate().getTime()));
        ps.setDate(3, new java.sql.Date(summary.getEndDate().getTime()));
        ps.setDouble(4, summary.getDistance());
        ps.setDouble(5, summary.getPace());
        byte[] bytes = ConvertUtil.LatLngListToBytes(summary.getRoute());
        InputStream is = new ByteArrayInputStream(bytes);
        ps.setBlob(6, is);
        ps.executeUpdate();
        this.connection.commit();
    }

    public List<Summary> getSummariesByUsername(String username){
        return null;
    }
    
    public Summary getSummaryById(Integer summaryId){
        //stub
//        Summary summary = new Summary(1234, new ArrayList<LatLng>(), "test", 25.3, 17.5, new Date()
//                , new Date());
        return null;
    }
}

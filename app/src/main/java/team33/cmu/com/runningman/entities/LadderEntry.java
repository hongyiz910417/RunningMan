package team33.cmu.com.runningman.entities;

/**
 * Created by d on 11/13/15.
 */
public class LadderEntry {
    private String userName;
    private String distance;

    public LadderEntry(String userName, String distance) {
        this.userName = userName;
        this.distance = distance;
    }

    public String getUserName() {
        return userName;
    }

    public String getDistance() {
        return distance;
    }
}

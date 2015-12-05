package team33.cmu.com.runningman.entities;

/**
 * Created by d on 11/13/15.
 */
public class LadderEntry {
    private String userName;
    private double distance;

    public LadderEntry(String userName, double distance) {
        this.userName = userName;
        this.distance = distance;
    }

    public String getUserName() {
        return userName;
    }

    public double getDistance() {
        return distance;
    }
}

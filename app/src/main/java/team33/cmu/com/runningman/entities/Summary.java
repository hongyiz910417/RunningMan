package team33.cmu.com.runningman.entities;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;
import java.util.List;



/**
 * Created by d on 11/13/15.
 * summary entities
 */
public class Summary {
    private Integer id;
    private List<LatLng> route;
    private String name;
    private double pace;
    private double distance;
    private Date startDate;
    private Date endDate;

    public Summary(Integer id, List<LatLng> route, String name, double pace, double distance
            , Date startDate, Date endDate) {
        this.id = id;
        this.route = route;
        this.name = name;
        this.pace = pace;
        this.distance = distance;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Summary(){
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<LatLng> getRoute() {
        return route;
    }

    public void setRoute(List<LatLng> route) {
        this.route = route;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPace() {
        return pace;
    }

    public void setPace(double pace) {
        this.pace = pace;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}

package b12app.vyom.com.bmwproject;

import java.io.Serializable;

public class LocationInfo implements Serializable {


    /**
     * ID : 40295
     * Name : Doughnut Vault Canal
     * Latitude : 41.883976
     * Longitude : -87.639346
     * Address : 11 N Canal St L30 Chicago, IL 60606
     * ArrivalTime : 2018-04-14T04:02:07.657
     */

    private int ID;
    private String Name;
    private double Latitude;
    private double Longitude;
    private String Address;
    private String ArrivalTime;


    public LocationInfo(int ID, String name, double latitude, double longitude, String address, String arrivalTime) {
        this.ID = ID;
        Name = name;
        Latitude = latitude;
        Longitude = longitude;
        Address = address;
        ArrivalTime = arrivalTime;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double Latitude) {
        this.Latitude = Latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double Longitude) {
        this.Longitude = Longitude;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getArrivalTime() {
        return ArrivalTime;
    }

    public void setArrivalTime(String ArrivalTime) {
        this.ArrivalTime = ArrivalTime;
    }
}

package com.laacompany.bluejackkost;

public class BHouse {

    private String name, facility, description, imageURL;
    private int price;
    private double latitude, longitude;

    public BHouse(String imageURL, String name, String facility, int price, String description, double latitude, double longitude) {
        this.imageURL = imageURL;
        this.name = name;
        this.facility = facility;
        this.price = price;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}

package com.crimekiller.safetrip.Model;

/**
 * Created by shuangliang on 4/3/16.
 */
public class Post {

    public String date, licensePlate, destination, model, color, departure;


    public Post(String date, String plate, String destination, String model, String color, String departure) {
        this.date = date;
        this.licensePlate = plate;
        this.destination = destination;
        this.model = model;
        this.color = color;
        this.departure = departure;
    }

    public String getDate() {
        return date;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getDestination() {
        return destination;
    }

    public String getModel() {
        return model;
    }

    public String getColor() {
        return color;
    }

    public String getDeparture() {
        return departure;
    }

}

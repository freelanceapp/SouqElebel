package com.souqelebel.models;

import java.io.Serializable;
import java.util.List;

public class ProductModel implements Serializable {
    private int id;
    private String title;
    private String image;
    private int departemnt_id;
    private double price;
    private int user_id;
    private String details;
    private double google_lat;
    private double google_long;
    private String address;
    private String vedio;
    private String created_at;
    private MainCategoryModel department_fk;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public int getDepartemnt_id() {
        return departemnt_id;
    }

    public double getPrice() {
        return price;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getDetails() {
        return details;
    }

    public double getGoogle_lat() {
        return google_lat;
    }

    public double getGoogle_long() {
        return google_long;
    }

    public String getAddress() {
        return address;
    }

    public String getVedio() {
        return vedio;
    }

    public MainCategoryModel getDepartment_fk() {
        return department_fk;
    }

    public String getCreated_at() {
        return created_at;
    }
}

package com.users.shelp_teacher;

public class Model {
    String image;
    String name, title;
    double rating;

    public Model(String image, String name, String title, double rating) {
        this.image = image;
        this.name = name;
        this.title = title;
        this.rating = rating;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}

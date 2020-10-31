package com.example.gmail.models;

public class EmailItemModel {
    String logo;
    String name;
    String time;
    String description;
    boolean favorite;

    public EmailItemModel(String logo, String name, String time, String description, boolean favorite) {
        this.logo = logo;
        this.name = name;
        this.time = time;
        this.description = description;
        this.favorite = favorite;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}

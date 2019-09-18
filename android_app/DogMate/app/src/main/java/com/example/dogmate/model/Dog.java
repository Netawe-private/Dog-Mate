package com.example.dogmate.model;

public class Dog {

    private String mName;
    private String mImage;
    private String mCity;
    private String mNeighborhood;
    private String mSize;
    private String mBreed;
    private String mTemperament;
    private String mOwner;

    public Dog(String name, String image, String city, String neighborhood, String size,
               String breed, String temperament, String owner) {
        mName = name;
        mImage = image;
        mCity = city;
        mNeighborhood = neighborhood;
        mSize = size;
        mBreed = breed;
        mTemperament = temperament;
        mOwner = owner;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public String getCity() {
        return mCity;
    }

    public void setCity(String city) {
        mCity = city;
    }

    public String getNeighborhood() {
        return mNeighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        mNeighborhood = neighborhood;
    }

    public String getSize() {
        return mSize;
    }

    public void setSize(String size) {
        mSize = size;
    }

    public String getBreed() {
        return mBreed;
    }

    public void setBreed(String breed) {
        mBreed = breed;
    }

    public String getTemperament() {
        return mTemperament;
    }

    public void setTemperament(String temperament) {
        mTemperament = temperament;
    }

    public String getOwner() {
        return mOwner;
    }
}

package com.example.gasolineprices;

public class FuelClass {
    String City;
    String Type;
    String Brand;
    double Price;
    public FuelClass(String city, String type, String brand, double price) {
        City = city;
        Type = type;
        Brand = brand;
        Price = price;
    }


    public FuelClass() {
    };

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }
}

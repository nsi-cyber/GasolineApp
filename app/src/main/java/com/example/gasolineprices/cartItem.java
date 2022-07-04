package com.example.gasolineprices;

public class cartItem
{
    String City;
    String Type;
    String Brand;

    public cartItem(String city, String type, String brand, double price, int count) {
        City = city;
        Type = type;
        Brand = brand;
        Price = price;
        Count = count;
    }

    public cartItem(){};

    double Price;
    int Count;

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

    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
    }
}

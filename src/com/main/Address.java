package com.main;

public class Address {
    private String city;
    private String street;
    private int flatNumber;

    public Address() {
    }

    public Address(String city, String street, int flatNumber) {

        this.city = city;
        this.street = street;
        this.flatNumber = flatNumber;
    }

    public String getCity() {

        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getFlatNumber() {
        return flatNumber;
    }

    public void setFlatNumber(int flatNumber) {
        this.flatNumber = flatNumber;
    }

    @Override
    public String toString() {
        return "Address{" +
                "city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", flatNumber=" + flatNumber +
                '}';
    }
}

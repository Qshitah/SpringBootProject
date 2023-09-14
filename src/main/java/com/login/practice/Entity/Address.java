package com.login.practice.Entity;

import com.login.practice.signUp.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column(name = "streetaddress")
    private String streetAddress;


    @Column(name = "city")
    private String City;

    @Column(name = "stateprovince")
    private String StateProvince;

    @Column(name = "postalcode")
    private String PostalCode;

    @Column(name = "country")
    private String Country;

    @Column(name = "addresstype")
    private String AddressType;

    @OneToOne(fetch = FetchType.LAZY,cascade = {CascadeType.REFRESH,CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST})
    @JoinColumn(name = "user_id")
    private User user;

    public Address(){

    }

    public Address(int id, String streetAddress, String city, String stateProvince, String postalCode, String country, String addressType) {
        this.id = id;
        this.streetAddress = streetAddress;
        City = city;
        StateProvince = stateProvince;
        PostalCode = postalCode;
        Country = country;
        AddressType = addressType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getStateProvince() {
        return StateProvince;
    }

    public void setStateProvince(String stateProvince) {
        StateProvince = stateProvince;
    }

    public String getPostalCode() {
        return PostalCode;
    }

    public void setPostalCode(String postalCode) {
        PostalCode = postalCode;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getAddressType() {
        return AddressType;
    }

    public void setAddressType(String addressType) {
        AddressType = addressType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", streetAddress='" + streetAddress + '\'' +
                ", City='" + City + '\'' +
                ", StateProvince='" + StateProvince + '\'' +
                ", PostalCode='" + PostalCode + '\'' +
                ", Country='" + Country + '\'' +
                ", AddressType='" + AddressType + '\'' +
                '}';
    }
}

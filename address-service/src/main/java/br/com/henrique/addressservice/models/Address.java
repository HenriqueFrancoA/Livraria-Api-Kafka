package br.com.henrique.addressservice.models;

import br.com.henrique.addressservice.models.dto.AddressWithUserDto;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "Addresses")
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    private String street;

    @Column(name = "house_number")
    private String number;

    private String city;

    private String state;

    @Column(name = "zip_code")
    private String zipCode;

    private String country;

    public Address() {
    }

    public Address(Address address) {
        this.id = address.getId();
        this.userId = address.getUserId();
        this.number = address.getNumber();
        this.street = address.getStreet();
        this.city = address.getCity();
        this.state = address.getState();
        this.zipCode = address.getZipCode();
        this.country = address.getCountry();
    }

    public Address(AddressWithUserDto addressWithUserDto, Long userId) {
        this.userId = userId;
        this.number = addressWithUserDto.getNumber();
        this.street = addressWithUserDto.getStreet();
        this.city = addressWithUserDto.getCity();
        this.state = addressWithUserDto.getState();
        this.zipCode = addressWithUserDto.getZipCode();
        this.country = addressWithUserDto.getCountry();
    }

    public Address(Long userId, String number, String street, String city, String state, String zipCode, String country) {
        this.userId = userId;
        this.number = number;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.country = country;
    }

    public Address(Long id, Long userId, String number, String street, String city, String state, String zipCode, String country) {
        this.id = id;
        this.userId = userId;
        this.number = number;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.country = country;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long user) {
        this.userId = userId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}

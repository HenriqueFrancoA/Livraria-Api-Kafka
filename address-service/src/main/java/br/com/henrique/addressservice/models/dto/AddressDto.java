package br.com.henrique.addressservice.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.dozermapper.core.Mapping;
import org.springframework.hateoas.RepresentationModel;

@JsonPropertyOrder({"id", "publicPlaces","number","neighborhood","city","state","zipCode","country"})
public class AddressDto extends RepresentationModel<AddressDto> {

    @JsonProperty("id")
    @Mapping("id")
    private Long key;
    private String street;
    private String number;
    private String city;
    private String state;
    private String zipCode;
    private String country;

    public AddressDto() {
    }

    public AddressDto(Long key, String street, String number, String city, String state, String zipCode, String country) {
        this.key = key;
        this.street = street;
        this.number = number;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.country = country;
    }

    public Long getKey() {return key;}

    public void setKey(Long key) {this.key = key;}

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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

package br.com.henrique.paymentservice.models.dto;

import java.io.Serializable;


public class Publisher implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String country;

    public Publisher() {
    }

    public Publisher(Publisher publisher) {
        this.id = publisher.getId();
        this.name = publisher.getName();
        this.country = publisher.getCountry();
    }

    public Publisher(Long id, String name, String country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}

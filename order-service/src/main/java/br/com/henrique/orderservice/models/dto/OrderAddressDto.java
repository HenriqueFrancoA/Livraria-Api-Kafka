package br.com.henrique.orderservice.models.dto;

public class OrderAddressDto {

    private Long address;

    public OrderAddressDto() {
    }

    public OrderAddressDto(Long address) {
        this.address = address;
    }

    public Long getAddress() {return address;}

    public void setAddress(Long address) {this.address = address;}
}

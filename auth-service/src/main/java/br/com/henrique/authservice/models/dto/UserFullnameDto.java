package br.com.henrique.authservice.models.dto;

public class UserFullnameDto {

    private String fullName;

    public UserFullnameDto() {
    }

    public UserFullnameDto(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}


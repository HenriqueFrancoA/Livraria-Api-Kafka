package br.com.henrique.authservice.models.dto;

import java.util.List;

public class UserToCreateDto {

    private String userName;
    private String fullName;
    private String password;
    private List<Long> permissionsIds;

    public UserToCreateDto() {
    }

    public UserToCreateDto(String userName, String fullName, String password, List<Long> permissionsIds) {
        this.userName = userName;
        this.fullName = fullName;
        this.password = password;
        this.permissionsIds = permissionsIds;
    }

    public List<Long> getPermissionsIds() {
        return permissionsIds;
    }

    public void setPermissionsIds(List<Long> permissionsIds) {
        this.permissionsIds = permissionsIds;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}

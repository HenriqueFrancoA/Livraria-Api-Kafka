package br.com.henrique.paymentservice.models.dto;


public class UserPermission {

    private Long id;

    private User user;

    private Permission permission;

    public UserPermission() {
    }

    public UserPermission(Long id, User user, Permission permission) {
        this.id = id;
        this.user = user;
        this.permission = permission;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }
}

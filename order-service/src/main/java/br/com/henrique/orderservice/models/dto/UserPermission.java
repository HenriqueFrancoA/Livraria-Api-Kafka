package br.com.henrique.orderservice.models.dto;

import br.com.henrique.orderservice.models.embedd.UserPermissionId;


public class UserPermission {

    private UserPermissionId id;

    private User user;

    private Permission permission;

    public UserPermission() {
    }

    public UserPermission(UserPermissionId id, User user, Permission permission) {
        this.id = id;
        this.user = user;
        this.permission = permission;
    }

    public UserPermissionId getId() {
        return id;
    }

    public void setId(UserPermissionId id) {
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

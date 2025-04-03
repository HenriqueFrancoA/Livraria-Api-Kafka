package br.com.henrique.authservice.models;

import br.com.henrique.authservice.models.embedd.UserPermissionId;
import jakarta.persistence.*;

@Entity
@Table(name = "user_permission")
public class UserPermission {

    @EmbeddedId
    private UserPermissionId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "id_user")
    private User user;

    @ManyToOne
    @MapsId("permissionId")
    @JoinColumn(name = "id_permission")
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

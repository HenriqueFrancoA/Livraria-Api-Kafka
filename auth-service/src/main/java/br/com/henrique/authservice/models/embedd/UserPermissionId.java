package br.com.henrique.authservice.models.embedd;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserPermissionId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "id_user")
    private Long userId;

    @Column(name = "id_permission")
    private Long permissionId;

    public UserPermissionId() {}

    public UserPermissionId(Long userId, Long permissionId) {
        this.userId = userId;
        this.permissionId = permissionId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPermissionId that = (UserPermissionId) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(permissionId, that.permissionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, permissionId);
    }
}

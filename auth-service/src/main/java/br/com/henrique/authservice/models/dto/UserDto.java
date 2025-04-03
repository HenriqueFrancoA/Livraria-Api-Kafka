package br.com.henrique.authservice.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.dozermapper.core.Mapping;
import org.springframework.hateoas.RepresentationModel;

@JsonPropertyOrder({"id", "fullName","enabled"})
public class UserDto extends RepresentationModel<UserDto> {

    @JsonProperty("id")
    @Mapping("id")
    private Long key;
    private String fullName;
    private Boolean enabled;

    public UserDto() {
    }

    public UserDto(Long key, String fullName, Boolean enabled) {
        this.key = key;
        this.fullName = fullName;
        this.enabled = enabled;
    }

    public Long getKey() {return key;}

    public void setKey(Long key) {this.key = key;}

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

}

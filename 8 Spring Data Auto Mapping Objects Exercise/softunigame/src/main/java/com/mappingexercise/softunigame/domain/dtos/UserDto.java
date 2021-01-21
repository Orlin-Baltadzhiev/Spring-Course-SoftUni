package com.mappingexercise.softunigame.domain.dtos;


import com.mappingexercise.softunigame.domain.entities.Role;

public class UserDto {
    private String email;
    private String fullName;
    private Role role;

    public UserDto(String email, String fullName, Role role) {
        this.email = email;
        this.fullName = fullName;
        this.role = role;
    }

    public UserDto() {
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}

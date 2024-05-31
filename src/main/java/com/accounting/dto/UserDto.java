package com.accounting.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;
    private String username;
    private String password;
    private String confirmPassword;
    private String firstname;
    private String lastname;
    private String phone;

    private RoleDto role;
    private CompanyDto company;
    private Boolean isOnlyAdmin;

    public Long getId(){
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getUsername() {return username;}

    public void setUsername(String username){
        this.username = username;
    }
    public String getLastname() {return lastname;}

    public void setLastname(String lastname){
        this.lastname = lastname;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        checkConfirmPassword();
    }

    private void checkConfirmPassword() {
        if (password != null && !password.equals(confirmPassword)) {
            this.confirmPassword = null;
        }
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public RoleDto getRole() {
        return role;
    }

    public void setRole(RoleDto role) {
        this.role = role;
    }

    public CompanyDto getCompany() {
        return company;
    }

    public void setCompany(CompanyDto company) {
        this.company = company;
    }

    public Boolean getIsOnlyAdmin() {
        return isOnlyAdmin;
    }

    public void setIsOnlyAdmin(Boolean isOnlyAdmin) {
        this.isOnlyAdmin = isOnlyAdmin;
    }
}


package com.accounting.entity.common;


import com.accounting.entity.User;
import com.accounting.enums.CompanyStatus;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserPrincipal implements UserDetails {

    private final User user;

    public UserPrincipal(@Lazy User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !user.getCompany().getCompanyStatus().equals(CompanyStatus.PASSIVE);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }

    public Long getId(){
        return this.user.getId();
    }

    public String getFullNameForProfile() {
        return this.user.getFirstname() + " " + this.user.getLastname();
    }

    public String getCompanyTitleForProfile() {
        return this.user.getCompany().getTitle().toUpperCase();
    }
}

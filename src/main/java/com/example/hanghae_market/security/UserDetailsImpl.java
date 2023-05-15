package com.example.hanghae_market.security;

import com.example.hanghae_market.entity.User;
import com.example.hanghae_market.entity.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class UserDetailsImpl implements UserDetails {

    private final User user;
    private final String UserId;

    public UserDetailsImpl(User user, String userId) {
        this.user = user;
        this.UserId = userId;
    }

    public User getUser() {
        return user;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        UserRole role = user.getRole();
        String authority = role.getAuthority();
//      String authority = "ROLE_USER";

        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(simpleGrantedAuthority);

        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.UserId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}

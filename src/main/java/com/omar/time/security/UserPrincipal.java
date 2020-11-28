package com.omar.time.security;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.omar.time.model.User;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class UserPrincipal implements UserDetails {

    private static final long serialVersionUID = 1096596654707987665L;

    private final Long id;
    private final String firstName;
    private final String lastName;
    private final String username;
    private final String mobile;
    @JsonIgnore
    private final String email;
    @JsonIgnore
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(Long id, String firstName, String lastName, String username,
                         String mobile, String email, String password,
                         Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.mobile = mobile;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserPrincipal create(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream().map(role ->
                new SimpleGrantedAuthority(role.getName().name())
        ).collect(Collectors.toList());

        return new UserPrincipal(user.getId(), user.getFirstName(), user.getLastName(), user.getUsername(),
                user.getMobile(), user.getEmail(), user.getPassword(), authorities);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
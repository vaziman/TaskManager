package org.example.taskmanager.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.example.taskmanager.models.enums.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "users")
@Data
public class User implements UserDetails {
    private LocalDateTime dateOfCreated;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "active")
    private boolean active;


    @ElementCollection(targetClass = UserRole.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Set<UserRole> roles = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER)
    private List<Task> tasks = new ArrayList<>();


    @PrePersist
    private void init() {
        dateOfCreated = LocalDateTime.now();
    }

    // security
    public boolean isAdmin() {
        return roles.contains(UserRole.ADMIN);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    public List<Task> getTasksByUserName(String username) {
        return null;
    }

    public void setUserName(String username) {
        this.username = username;
    }
}

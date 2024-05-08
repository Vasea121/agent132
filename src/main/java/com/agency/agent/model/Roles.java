package com.agency.agent.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serial;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Roles implements GrantedAuthority {
    @Serial
    private static final long serialVersionUID = 615786319041370972L;
    @Id
    private Long id;
    private String name;
    @Transient
    @ManyToMany(mappedBy = "roles")
    private Set<Users> users;

    public Roles() {
    }

    public Roles(Long id) {
        this.id = id;
    }

    public Roles(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Users> getUsers() {
        return users;
    }

    public void setUsers(Set<Users> users) {
        this.users = users;
    }

    @Override
    public String getAuthority() {
        return getName();
    }
}

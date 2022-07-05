package com.ironhack.midterm.project.models.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Role> roles;
    private String name;
    private String password;

    public User(Set<Role> roles, String name, String password) {
        this.roles = roles;
        this.name = name;
        this.password = password;
    }
    public User( String name, String password) {
        this.name = name;
        this.password = password;
    }



    public User() {

    }

}

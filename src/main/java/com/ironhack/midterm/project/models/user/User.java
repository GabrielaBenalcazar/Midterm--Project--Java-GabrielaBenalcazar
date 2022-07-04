package com.ironhack.midterm.project.models.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private String name;
    private String password;

    public User(String name, String password, UserRole role) {
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public User() {

    }

}

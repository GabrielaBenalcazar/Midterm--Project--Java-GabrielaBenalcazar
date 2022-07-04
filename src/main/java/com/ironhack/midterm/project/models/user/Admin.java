package com.ironhack.midterm.project.models.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@Getter
@Setter

@PrimaryKeyJoinColumn(name = "id")
public class Admin extends User {
    public Admin() {
        super();
    }

    public Admin(String name, String password) {
        super(name, password, UserRole.ADMIN);
    }


}

package com.ironhack.midterm.project.models.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "id")
public class ThirdParty extends User {


    public ThirdParty(Set<Role> roles, String name, String password) {
        super(roles, name, password);

    }
    public ThirdParty(String name, String password) {
        super( name, password);

    }

}

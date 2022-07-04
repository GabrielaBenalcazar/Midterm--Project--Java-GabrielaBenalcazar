package com.ironhack.midterm.project.models.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@Getter
@Setter
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "id")
public class ThirdParty extends User {
    public String hashKey;

    public ThirdParty(String name, String password, String hashKey) {
        super(name, password, UserRole.THIRD_PARTY);
        this.hashKey = hashKey;
    }
}

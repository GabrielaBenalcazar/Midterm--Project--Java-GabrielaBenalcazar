package com.ironhack.midterm.project.models.clasees;

import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@AllArgsConstructor
@Data
@NoArgsConstructor(force = true)
@Embeddable

public class Address {

    private long houseNumber;
    private String streetName;
    private String city;
    private String postcode;
    private String country;

}

package com.ironhack.midterm.project.models.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ironhack.midterm.project.models.account.Account;
import com.ironhack.midterm.project.models.clasees.Address;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@PrimaryKeyJoinColumn(name = "id")
public class AccountHolders extends User{
    private String dateOfBirth;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "houseNumber", column = @Column(name = "house_number")),
            @AttributeOverride(name = "streetName", column = @Column(name = "street_name")),
            @AttributeOverride(name = "city", column = @Column(name = "city")),
            @AttributeOverride(name = "postcode", column = @Column(name = "postcode")),
            @AttributeOverride(name = "country", column = @Column(name = "country"))
    })
    private Address address;
    private String mailingAddress;

    @JsonIgnoreProperties
    @OneToMany(mappedBy = "primaryOwner", cascade = CascadeType.ALL)
    private List<Account> accountsPrimary;

    @JsonIgnoreProperties
    @Nullable
    @OneToMany(mappedBy = "secondaryOwner", cascade = CascadeType.ALL)
    private List<Account> accountsSecondary;

    public AccountHolders(String name, String password, String dateOfBirth, Address address, String mailingAddress) {
        super(name, password, UserRole.ACCOUNT_HOLDER);
        this.dateOfBirth = dateOfBirth;
//        this.address = address;
        this.mailingAddress = mailingAddress;
    }

    public AccountHolders() {
    }
}

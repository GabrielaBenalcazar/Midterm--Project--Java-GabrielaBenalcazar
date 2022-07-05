package com.ironhack.midterm.project.models.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ironhack.midterm.project.models.account.Account;
import com.ironhack.midterm.project.models.clasees.Address;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

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
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "houseNumber", column = @Column(name = "house_number_mailing")),
            @AttributeOverride(name = "streetName", column = @Column(name = "street_name_mailing")),
            @AttributeOverride(name = "city", column = @Column(name = "city_mailing")),
            @AttributeOverride(name = "postcode", column = @Column(name = "postcode_mailing")),
            @AttributeOverride(name = "country", column = @Column(name = "country_mailing"))
    })
    private Address mailingAddress;

    @JsonIgnoreProperties
    @OneToMany(mappedBy = "primaryOwner", cascade = CascadeType.ALL)
    private List<Account> accountsPrimary;

    @JsonIgnoreProperties
    @Nullable
    @OneToMany(mappedBy = "secondaryOwner", cascade = CascadeType.ALL)
    private List<Account> accountsSecondary;

    public AccountHolders(Set<Role> roles, String name, String password, String dateOfBirth, Address address, Address mailingAddress, List<Account> accountsPrimary, @Nullable List<Account> accountsSecondary) {
        super(roles, name, password);
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.mailingAddress = mailingAddress;
        this.accountsPrimary = accountsPrimary;
        this.accountsSecondary = accountsSecondary;
    }
    public AccountHolders( String name, String password, String dateOfBirth, Address address, Address mailingAddress) {
        super(name, password);
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.mailingAddress = mailingAddress;

    }

    public AccountHolders() {
    }


}

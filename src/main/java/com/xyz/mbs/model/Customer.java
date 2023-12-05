package com.xyz.mbs.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@DiscriminatorValue("CUSTOMER")
public class Customer extends User{
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<Booking> bookings;
}

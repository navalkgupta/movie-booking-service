package com.xyz.mbs.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue("REFUND")
public class Refund extends Payment{
    @OneToOne
    @JoinColumn(name = "booking_id")
    @JsonManagedReference
    private Booking booking;
}

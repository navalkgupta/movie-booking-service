package com.xyz.mbs.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.xyz.mbs.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "booking_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "show_id")
    @JsonManagedReference
    private Show show;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private Customer customer;

    @Column(name = "created_time")
    private ZonedDateTime createdTime = ZonedDateTime.now();

    @Column(name = "booking_status")
    private BookingStatus bookingStatus = BookingStatus.CREATED;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<ShowSeat> showSeats;

    @OneToOne
    @JoinColumn(name = "payment_id", referencedColumnName = "payment_id")
    @JsonBackReference
    private Payment payment;

    @OneToOne
    @JoinColumn(name = "refund_id", referencedColumnName = "payment_id")
    @JsonBackReference
    private Refund refund;
}

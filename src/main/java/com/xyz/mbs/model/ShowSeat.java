package com.xyz.mbs.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.xyz.mbs.enums.SeatStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "show_seat")
public class ShowSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "show_seat_id")
    private Long id;

    @Column(name = "seat_status")
    private SeatStatus seatStatus = SeatStatus.EMPTY;

    @ManyToOne
    @JoinColumn(name = "seat_id")
    @JsonManagedReference
    private Seat seat;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    @JsonManagedReference
    private Booking booking;

    @ManyToOne
    @JoinColumn(name = "show_id")
    @JsonManagedReference
    private Show show;
}

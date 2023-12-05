package com.xyz.mbs.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "seat")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "seat_id")
    private Long id;

    @Column(name = "seat_number")
    private Integer seatNumber;

    @ManyToOne
    @JoinColumn(name = "theatre_id")
    @JsonManagedReference
    private Theatre theatre;
}

package com.xyz.mbs.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "show")
public class Show {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "show_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    @JsonManagedReference
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "theatre_id")
    @JsonManagedReference
    private Theatre theatre;

    @Column(name = "show_time")
    private Date showTime;

    @Column(name = "price")
    private Double price;

    @OneToMany(mappedBy = "show", cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<Booking> bookings;

    @OneToMany(mappedBy = "show", cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<ShowSeat> showSeats;
}

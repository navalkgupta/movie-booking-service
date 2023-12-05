package com.xyz.mbs.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import jakarta.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "theatre")
public class Theatre {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "theatre_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "user_id")
    private TheatrePartner theatrePartner;

    @ManyToOne
    @JoinColumn(name = "city_id")
    @JsonManagedReference
    private City city;

    @OneToMany(mappedBy = "theatre", cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<Movie> movies;

    @OneToMany(mappedBy = "theatre", cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<Show> shows;

    @OneToMany(mappedBy = "theatre", cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<Seat> seats;
}

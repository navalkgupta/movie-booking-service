package com.xyz.mbs.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.xyz.mbs.enums.Genre;
import com.xyz.mbs.enums.Language;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "movie")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "movie_id")
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "language")
    private Language language;

    @Column(name = "description")
    private String description;

    @Column(name = "genre")
    private Genre genre;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<Show> shows;

    @ManyToOne
    @JoinColumn(name = "theatre_id")
    @JsonManagedReference
    private Theatre theatre;
}

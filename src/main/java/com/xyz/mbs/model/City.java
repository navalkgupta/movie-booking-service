package com.xyz.mbs.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "city")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "city_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "state")
    private String state;

    @Column(name = "pin_code")
    private String pinCode;

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<Theatre> theatres;
}

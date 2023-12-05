package com.xyz.mbs.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@DiscriminatorValue("THEATRE_PARTNER")
public class TheatrePartner extends User{
    @OneToMany(mappedBy = "theatrePartner", cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<Theatre> theatres;
}

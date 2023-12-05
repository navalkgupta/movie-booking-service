package com.xyz.mbs.service;

import com.xyz.mbs.model.*;
import com.xyz.mbs.model.dto.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ManagementService {

    User signUp(UserDto userDto);

    Theatre addTheatre(TheatreDto theateDto);
    
    Movie addMovie(MovieDto movieDto);
    
    List<Show> addMovieShows(ShowDto showDto);

    City addCity(CityDto cityDto);
}

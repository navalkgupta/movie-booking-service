package com.xyz.mbs.service.impl;

import com.xyz.mbs.exceptions.RecordNotFoundException;
import com.xyz.mbs.model.*;
import com.xyz.mbs.model.dto.*;
import com.xyz.mbs.repository.*;
import com.xyz.mbs.service.ManagementService;
import com.xyz.mbs.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ManagementServiceImpl implements ManagementService {
    
    @Autowired
    TheatreRepository theatreRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    ShowRepository showRepository;

    @Autowired
    ShowSeatRepository showSeatRepository;

    @Autowired
    SeatRepository seatRepository;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    UserService userService;

    public User signUp(UserDto userDto){
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setMobile(userDto.getMobile());
        user.setUserType(userDto.getUserType().name());
        return userService.addUser(user);
    }

    @Transactional
    public Theatre addTheatre(TheatreDto theatreDto) {
        Theatre theatre = new Theatre();
        theatre.setTheatrePartner((TheatrePartner) userService.getUser(theatreDto.getUserId()));
        theatre.setCity(cityRepository.findById(theatreDto.getCityId()).orElseThrow(RecordNotFoundException::new));
        theatre.setName(theatreDto.getName());
        theatre.setSeats(addSeats(theatre, theatreDto.getSeatCount()));
        return theatreRepository.save(theatre);
    }

    public Movie addMovie(MovieDto movieDto) {
        Movie movie = new Movie();
        movie.setTitle(movieDto.getTitle());
        movie.setDescription(movieDto.getDescription());
        movie.setLanguage(movieDto.getLanguage());
        movie.setGenre(movieDto.getGenre());
        movie.setTheatre(theatreRepository.findById(movieDto.getTheatreId()).orElseThrow(RecordNotFoundException::new));
        return movieRepository.save(movie);
    }

    @Transactional
    public List<Show> addMovieShows(ShowDto showDto) {
        List<Show> shows = new ArrayList<>();
        for (Map.Entry<Date, Double> entry : showDto.getShowTimings().entrySet()) {
            Date showTime = entry.getKey();
            Double price = entry.getValue();
            Show show = new Show();
            Theatre theatre = theatreRepository.findById(showDto.getTheatreId()).orElseThrow(RecordNotFoundException::new);
            show.setTheatre(theatre);
            show.setMovie(movieRepository.findById(showDto.getMovieId()).orElseThrow(RecordNotFoundException::new));
            show.setShowTime(showTime);
            show.setPrice(price);
            show = showRepository.save(show);
            show.setShowSeats(addShowSeats(show, theatre.getSeats()));
            shows.add(show);
        }
        return showRepository.saveAll(shows);
    }

    public City addCity(CityDto cityDto) {
        City city = new City();
        city.setName(cityDto.getName());
        city.setState(cityDto.getState());
        city.setPinCode(cityDto.getPinCode());
        return cityRepository.save(city);
    }

    private Set<Seat> addSeats(Theatre theatre, int seatCount){
        List<Seat> seats = new ArrayList<>();
        for(int i=1; i<=seatCount; i++){
            Seat seat = new Seat();
            seat.setSeatNumber(i);
            seat.setTheatre(theatre);
            seats.add(seat);
        }
        return new HashSet<>(seatRepository.saveAll(seats));
    }

    private Set<ShowSeat> addShowSeats(Show show, Set<Seat> seats){
        List<ShowSeat> showSeats = new ArrayList<>();
        seats.forEach(seat -> {
            ShowSeat showSeat = new ShowSeat();
            showSeat.setShow(show);
            showSeat.setSeat(seat);
            showSeats.add(showSeat);
        });
        return new HashSet<>(showSeatRepository.saveAll(showSeats));
    }
}

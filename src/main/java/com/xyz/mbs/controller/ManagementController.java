package com.xyz.mbs.controller;

import com.xyz.mbs.exceptions.RecordNotFoundException;
import com.xyz.mbs.model.dto.*;
import com.xyz.mbs.service.ManagementService;
import com.xyz.mbs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/mgmt")
public class ManagementController {

    @Autowired
    private UserService userService;

    @Autowired
    private ManagementService managementService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDto userDto){
        return ResponseEntity.ok(managementService.signUp(userDto));
    }

    @PostMapping("/city")
    public ResponseEntity<?> addCity(@RequestBody CityDto cityDto){
        return ResponseEntity.ok(managementService.addCity(cityDto));
    }

    @PostMapping("/theatre")
    public ResponseEntity<?> addTheatre(@RequestBody TheatreDto theatreDto){
        return ResponseEntity.ok(managementService.addTheatre(theatreDto));
    }

    @PostMapping("/movies")
    public ResponseEntity<?> addMovies(@RequestBody MovieDto movieDto){
        return ResponseEntity.ok(managementService.addMovie(movieDto));
    }

    @PostMapping("/shows")
    public ResponseEntity<?> addShows(@RequestBody ShowDto showDto){
        return ResponseEntity.ok(managementService.addMovieShows(showDto));
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "record not found")
    @ExceptionHandler(RecordNotFoundException.class)
    void recordNotFound(RecordNotFoundException recordNotFoundException){}
}

package com.xyz.mbs.service.impl;

import com.xyz.mbs.enums.SeatStatus;
import com.xyz.mbs.exceptions.RecordNotFoundException;
import com.xyz.mbs.model.Show;
import com.xyz.mbs.model.ShowSeat;
import com.xyz.mbs.model.Theatre;
import com.xyz.mbs.repository.ShowRepository;
import com.xyz.mbs.repository.ShowSeatRepository;
import com.xyz.mbs.repository.TheatreRepository;
import com.xyz.mbs.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private TheatreRepository theatreRepository;

    @Autowired
    private ShowSeatRepository showSeatRepository;

    public List<Theatre> getTheatres(String pinCode){
        return theatreRepository.findTheatres(pinCode);
    }

    public Map<Theatre, List<Show>> getShows(long cityId, long movieId, Date date) {
        List<Show> shows = showRepository.findMovieShowsInCity(cityId, movieId, date);
        return shows.stream().collect(Collectors.groupingBy(Show::getTheatre));
    }

    public List<ShowSeat> getEmptyShowSeats(long showId){
        Show show = showRepository.findById(showId).orElseThrow(RecordNotFoundException::new);
        return showSeatRepository.getSeatsByIds(show.getShowSeats().stream().map(ShowSeat::getId).toList())
                .stream().filter(showSeat -> showSeat.getSeatStatus().equals(SeatStatus.EMPTY)).toList();
    }
}

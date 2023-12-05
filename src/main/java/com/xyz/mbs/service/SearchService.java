package com.xyz.mbs.service;

import com.xyz.mbs.model.Show;
import com.xyz.mbs.model.ShowSeat;
import com.xyz.mbs.model.Theatre;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public interface SearchService {
    Map<Theatre, List<Show>> getShows(long cityId, long movieId, Date date);

    List<ShowSeat> getEmptyShowSeats(long showId);
}

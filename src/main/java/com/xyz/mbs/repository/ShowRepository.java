package com.xyz.mbs.repository;

import com.xyz.mbs.model.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ShowRepository extends JpaRepository<Show, Long> {

    @Query("select s from Show s inner join Theatre t on s.theatre.id = t.id " +
            "where t.city.id =:cityId and s.movie.id =:movieId " +
            "and date(s.showTime) =:movieDate")
    List<Show> findMovieShowsInCity(@Param("cityId") long cityId, @Param("movieId") long movieId, @Param("movieDate") Date date);

}

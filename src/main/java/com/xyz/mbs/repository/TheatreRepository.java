package com.xyz.mbs.repository;

import com.xyz.mbs.model.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TheatreRepository extends JpaRepository<Theatre, Long> {

    @Query("select t from Theatre t inner join City c on t.city.id = c.id where c.pinCode = :pinCode")
    List<Theatre> findTheatres(@Param("pinCode") String pinCode);
}

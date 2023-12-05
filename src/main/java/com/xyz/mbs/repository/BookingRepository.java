package com.xyz.mbs.repository;

import com.xyz.mbs.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("select b from Booking b inner join Customer c on b.customer.id =:userId")
    List<Booking> findByUserId(@Param("userId") long userId);
}

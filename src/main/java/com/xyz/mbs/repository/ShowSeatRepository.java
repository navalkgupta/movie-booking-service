package com.xyz.mbs.repository;

import com.xyz.mbs.enums.SeatStatus;
import com.xyz.mbs.model.Seat;
import com.xyz.mbs.model.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {

    @Query("select st from ShowSeat st where st.id =:showId and st.seatStatus =:seatStatus")
    List<ShowSeat> getSeatsByStatus(@Param("showId") long showId, @Param("seatStatus") SeatStatus seatStatus);

    @Query("select st from ShowSeat st where st.id IN :showSeatIds")
    List<ShowSeat> getSeatsByIds(@Param("showSeatIds") List<Long> showSeatIds);

    @Query("select st.seatStatus from ShowSeat st where st.id IN :showSeatIds")
    List<SeatStatus> getSeatStatusByIds(@Param("showSeatIds") List<Long> showSeatIds);

    @Query("update ShowSeat st set st.seatStatus =:seatStatus where st.id IN :showSeatIds")
    void updateSeatsByIds(@Param("showSeatIds") List<Long> showSeatIds, @Param("seatStatus") SeatStatus seatStatus);
}

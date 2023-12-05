package com.xyz.mbs.model.dto;

import com.xyz.mbs.enums.PaymentMode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class BookingDto {
    private long userId;
    private long showId;
    private List<Long> seatIds;
    private PaymentMode paymentMode;
}

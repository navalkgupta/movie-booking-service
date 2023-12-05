package com.xyz.mbs.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Map;

@Getter
@Setter
public class ShowDto {
    private long theatreId;
    private long movieId;
    private Map<Date, Double> showTimings;
}

package com.xyz.mbs.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TheatreDto {
    private long userId;
    private long cityId;
    private String name;
    private int seatCount;
}

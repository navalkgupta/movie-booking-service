package com.xyz.mbs.model.dto;

import com.xyz.mbs.enums.Genre;
import com.xyz.mbs.enums.Language;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieDto {
    private long theatreId;
    private String title;
    private String description;
    private Language language;
    private Genre genre;
}

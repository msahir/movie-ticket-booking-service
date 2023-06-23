package org.msahir.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@Getter
public class Show {

    private final String id;
    private final Movie movie;
    private final Audi audi;
    private final Date startTime;
    private final Integer durationInSeconds;
}

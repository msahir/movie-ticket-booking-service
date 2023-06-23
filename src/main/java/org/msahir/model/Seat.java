package org.msahir.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.msahir.enums.SeatType;

@AllArgsConstructor
@Getter
public class Seat {

    private final String id;
    private final int rowNo;
    private final int seatNo;
    private final SeatType seatType;
}

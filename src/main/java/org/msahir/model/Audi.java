package org.msahir.model;

import lombok.Getter;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Audi {

    private final String id;
    private final String name;
    private final Theatre theatre;
    //Other audi metadata.

    private final List<Seat> seats;

    public Audi(@NonNull final String id, @NonNull final String name, @NonNull final Theatre theatre) {
        this.id = id;
        this.name = name;
        this.theatre = theatre;
        this.seats = new ArrayList<>();
    }

    public void addSeat(@NonNull final Seat seat) {
        this.seats.add(seat);
    }

}

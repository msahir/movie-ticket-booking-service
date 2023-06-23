package org.msahir.services;

import org.msahir.enums.SeatType;
import org.msahir.exceptions.NotFoundException;
import org.msahir.model.Audi;
import org.msahir.model.Seat;
import org.msahir.model.Theatre;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TheatreService {

    private final Map<String, Theatre> theatres;
    private final Map<String, Audi> audis;
    private final Map<String, Seat> seats;

    public TheatreService() {
        this.theatres = new HashMap<>();
        this.audis = new HashMap<>();
        this.seats = new HashMap<>();
    }

    public Seat getSeat(@NonNull final String seatId) {
        if (!seats.containsKey(seatId)) {
            throw new NotFoundException();
        }
        return seats.get(seatId);
    }

    public Theatre getTheatre(@NonNull final String theatreId) {
        if (!theatres.containsKey(theatreId)) {
            throw new NotFoundException();
        }
        return theatres.get(theatreId);
    }

    public Audi getAudi(@NonNull final String audiId) {
        if (!audis.containsKey(audiId)) {
            throw new NotFoundException();
        }
        return audis.get(audiId);
    }

    public Theatre createTheatre(@NonNull final String theatreName) {
        String theatreId = UUID.randomUUID().toString();
        Theatre theatre = new Theatre(theatreId, theatreName);
        theatres.put(theatreId, theatre);
        return theatre;
    }

    public Audi createAudiInTheatre(@NonNull final String audiName, @NonNull final Theatre theatre) {
        Audi audi = createAudi(audiName, theatre);
        theatre.addAudi(audi);
        return audi;
    }

    public Seat createSeatInAudi(@NonNull final Integer rowNo, @NonNull final Integer seatNo, @NonNull final Audi audi) {
        String seatId = UUID.randomUUID().toString();
        Seat seat = new Seat(seatId, rowNo, seatNo, SeatType.Gold);
        seats.put(seatId, seat);
        audi.addSeat(seat);

        return seat;
    }

    private Audi createAudi(final String audiName, final Theatre theatre) {
        String audiId = UUID.randomUUID().toString();
        Audi audi = new Audi(audiId, audiName, theatre);
        audis.put(audiId, audi);
        return audi;
    }

}

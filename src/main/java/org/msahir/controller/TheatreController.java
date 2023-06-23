package org.msahir.controller;

import org.msahir.model.Audi;
import org.msahir.model.Theatre;
import org.msahir.services.TheatreService;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class TheatreController {
    final private TheatreService theatreService;

    public String createTheatre(@NonNull final String theatreName) {
        return theatreService.createTheatre(theatreName).getId();
    }

    public String createAudiInTheatre(@NonNull final String audiName, @NonNull final String theatreId) {
        final Theatre theatre = theatreService.getTheatre(theatreId);
        return theatreService.createAudiInTheatre(audiName, theatre).getId();
    }

    public String createSeatInAudi(@NonNull final Integer rowNo, @NonNull final Integer seatNo, @NonNull final String audiId) {
        final Audi audi = theatreService.getAudi(audiId);
        return theatreService.createSeatInAudi(rowNo, seatNo, audi).getId();
    }
}

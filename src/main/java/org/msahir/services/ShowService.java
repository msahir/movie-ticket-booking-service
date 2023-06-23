package org.msahir.services;


import org.msahir.exceptions.NotFoundException;
import org.msahir.exceptions.AudiAlreadyOccupiedException;
import org.msahir.model.Movie;
import org.msahir.model.Audi;
import org.msahir.model.Show;
import lombok.NonNull;

import java.util.*;

public class ShowService {

    private final Map<String, Show> shows;

    public ShowService() {
        this.shows = new HashMap<>();
    }

    public Show getShow(@NonNull final String showId) {
        if (!shows.containsKey(showId)) {
            throw new NotFoundException();
        }
        return shows.get(showId);
    }

    public Show createShow(@NonNull final Movie movie, @NonNull final Audi audi, @NonNull final Date startTime,
                           @NonNull final Integer durationInSeconds) {
        if (!checkIfShowCreationAllowed(audi, startTime, durationInSeconds)) {
            throw new AudiAlreadyOccupiedException();
        }
        String showId = UUID.randomUUID().toString();
        final Show show = new Show(showId, movie, audi, startTime, durationInSeconds);
        this.shows.put(showId, show);
        return show;
    }

    private List<Show> getShowsForAudi(final Audi audi) {
        final List<Show> response = new ArrayList<>();
        for (Show show : shows.values()) {
            if (show.getAudi().equals(audi)) {
                response.add(show);
            }
        }
        return response;
    }

    private boolean checkIfShowCreationAllowed(final Audi audi, final Date startTime, final Integer durationInSeconds) {
        // TODO: For demo purpose, return always true.
        return true;
    }
}

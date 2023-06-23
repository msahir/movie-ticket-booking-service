package org.msahir.controller;

import org.msahir.model.Movie;
import org.msahir.model.Audi;
import org.msahir.model.Seat;
import org.msahir.model.Show;
import org.msahir.services.MovieService;
import org.msahir.services.SeatAvailabilityService;
import org.msahir.services.ShowService;
import org.msahir.services.TheatreService;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ShowController {
    private final SeatAvailabilityService seatAvailabilityService;
    private final ShowService showService;
    private final TheatreService theatreService;
    private final MovieService movieService;

    public String createShow(@NonNull final String movieId, @NonNull final String audiId, @NonNull final Date startTime,
                             @NonNull final Integer durationInSeconds) {
        final Audi audi = theatreService.getAudi(audiId);
        final Movie movie = movieService.getMovie(movieId);
        return showService.createShow(movie, audi, startTime, durationInSeconds).getId();
    }

    public List<String> getAvailableSeats(@NonNull final String showId) {
        final Show show = showService.getShow(showId);
        final List<Seat> availableSeats = seatAvailabilityService.getAvailableSeats(show);
        return availableSeats.stream().map(Seat::getId).collect(Collectors.toList());
    }
}

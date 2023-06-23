package org.msahir.controller;

import org.msahir.model.Seat;
import org.msahir.model.Show;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.msahir.services.BookingService;
import org.msahir.services.ShowService;
import org.msahir.services.TheatreService;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class BookingController {

    private final ShowService showService;
    private final BookingService bookingService;
    private final TheatreService theatreService;

    public String createBooking(@NonNull final String userId, @NonNull final String showId,
                                @NonNull final List<String> seatsIds) {
        final Show show = showService.getShow(showId);
        final List<Seat> seats = seatsIds.stream()
                .map(theatreService::getSeat)
                .collect(Collectors.toList());
        return bookingService.createBooking(userId, show, seats).getId();
    }

    public Boolean cancelBooking(@NonNull final String bookingId) {
        return bookingService.cancelBooking(bookingId);
    }
}

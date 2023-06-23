package org.msahir.services;

import org.msahir.model.Seat;
import org.msahir.model.Show;
import org.msahir.plugin.SeatLockPlugin;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

public class SeatAvailabilityService {
    private final BookingService bookingService;
    private final SeatLockPlugin seatLockPlugin;

    public SeatAvailabilityService(@NonNull final BookingService bookingService,
                                   @NonNull final SeatLockPlugin seatLockPlugin) {
        this.bookingService = bookingService;
        this.seatLockPlugin = seatLockPlugin;
    }

    public List<Seat> getAvailableSeats(@NonNull final Show show) {
        final List<Seat> allSeats = show.getAudi().getSeats();
        final List<Seat> unavailableSeats = getUnavailableSeats(show);

        final List<Seat> availableSeats = new ArrayList<>(allSeats);
        availableSeats.removeAll(unavailableSeats);
        return availableSeats;
    }

    private List<Seat> getUnavailableSeats(@NonNull final Show show) {
        final List<Seat> unavailableSeats = bookingService.getBookedSeats(show);
        unavailableSeats.addAll(seatLockPlugin.getLockedSeats(show));
        return unavailableSeats;
    }

}

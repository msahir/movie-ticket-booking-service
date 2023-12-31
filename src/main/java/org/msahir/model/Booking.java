package org.msahir.model;

import org.msahir.enums.BookingStatus;
import org.msahir.exceptions.InvalidStateException;
import lombok.Getter;
import lombok.NonNull;

import java.util.List;

@Getter
public class Booking {

    private final String id;
    private final Show show;
    private final List<Seat> seatsBooked;
    private final String user;
    private BookingStatus bookingStatus;

    public Booking(@NonNull final String id, @NonNull final Show show, @NonNull final String user,
                   @NonNull final List<Seat> seatsBooked) {
        this.id = id;
        this.show = show;
        this.seatsBooked = seatsBooked;
        this.user = user;
        this.bookingStatus = BookingStatus.Created;
    }

    public boolean isConfirmed() {
        return BookingStatus.Confirmed.equals(this.bookingStatus);
    }

    public void confirmBooking() {
        if (!BookingStatus.Created.equals(this.bookingStatus)) {
            throw new InvalidStateException();
        }
        this.bookingStatus = BookingStatus.Confirmed;
    }

    public void expireBooking() {
        if (!BookingStatus.Created.equals(this.bookingStatus)) {
            throw new InvalidStateException();
        }
        this.bookingStatus = BookingStatus.Cancel;
    }
}

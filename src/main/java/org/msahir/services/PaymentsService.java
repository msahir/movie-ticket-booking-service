package org.msahir.services;

import org.msahir.exceptions.BadRequestException;
import org.msahir.model.Booking;
import org.msahir.plugin.SeatLockPlugin;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

public class PaymentsService {

    Map<Booking, Integer> bookingFailures;
    private final Integer allowedRetries;
    private final SeatLockPlugin seatLockPlugin;

    public PaymentsService(@NonNull final Integer allowedRetries, SeatLockPlugin seatLockPlugin) {
        this.allowedRetries = allowedRetries;
        this.seatLockPlugin = seatLockPlugin;
        bookingFailures = new HashMap<>();
    }

    public void processPaymentFailed(@NonNull final Booking booking, @NonNull final String user) {
        if (!booking.getUser().equals(user)) {
            throw new BadRequestException();
        }
        if (!bookingFailures.containsKey(booking)) {
            bookingFailures.put(booking, 0);
        }
        final Integer currentFailuresCount = bookingFailures.get(booking);
        final Integer newFailuresCount = currentFailuresCount + 1;
        bookingFailures.put(booking, newFailuresCount);
        if (newFailuresCount > allowedRetries) {
            seatLockPlugin.unlockSeats(booking.getShow(), booking.getSeatsBooked(), booking.getUser());
        }
    }

    public boolean processPaymentSuccess(@NonNull final Booking booking, @NonNull final String user) {
        //TODO: Connect with Payment Service to perform the payment. For demo return true.
        return true;
    }
}

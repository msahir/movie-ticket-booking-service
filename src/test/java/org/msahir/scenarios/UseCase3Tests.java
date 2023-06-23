package org.msahir.scenarios;

import com.google.common.collect.ImmutableList;
import org.msahir.exceptions.SeatPermanentlyUnavailableException;
import org.msahir.exceptions.SeatTemporaryUnavailableException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

public class UseCase3Tests extends BaseTest {

    @BeforeEach
    void setUp() {
        setup(10, 0);
    }

    @Test
    void testCase3() {
        String user1 = "User1";
        String user2 = "User2";

        final String movie = movieController.createMovie("Movie 1");
        final String audi = setupAudi();
        final List<String> audiSeats = createSeats(theatreController, audi, 2, 10);

        final String show = showController.createShow(movie, audi, new Date(), 2 * 60 * 60);

        List<String> u1AvailableSeats = showController.getAvailableSeats(show);

        // Validate that seats u1 received has all audi seats
        validateSeatsList(u1AvailableSeats, audiSeats, ImmutableList.of());

        ImmutableList<String> u1SelectedSeats = ImmutableList.of(
                audiSeats.get(0),
                audiSeats.get(2),
                audiSeats.get(5),
                audiSeats.get(10)
        );

        ImmutableList<String> u2SelectedSeats = ImmutableList.of(
                audiSeats.get(0),
                audiSeats.get(1),
                audiSeats.get(2),
                audiSeats.get(3)
        );

        final String u1BookingId = bookingController.createBooking(user1, show, u1SelectedSeats);

        Assertions.assertThrows(SeatTemporaryUnavailableException.class, () -> {
            final String u2BookingId = bookingController.createBooking(user2, show, u2SelectedSeats);
        });

        paymentsController.paymentSuccess(u1BookingId, user1);

        Assertions.assertThrows(SeatPermanentlyUnavailableException.class, () -> {
            final String u2BookingId = bookingController.createBooking(user2, show, u2SelectedSeats);
        });
    }
}

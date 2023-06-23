package org.msahir.scenarios;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

public class UseCase2Tests extends BaseTest {

    @BeforeEach
    void setUp() {
        setup(10, 0);
    }

    @Test
    void testCase2() {
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
        final String bookingId = bookingController.createBooking(user1, show, u1SelectedSeats);

        final List<String> u2AvailableSeats = showController.getAvailableSeats(show);

        // Validate that u2 seats has all audi seats except the ones already blocked by u1
        validateSeatsList(u2AvailableSeats, audiSeats, u1SelectedSeats);

        paymentsController.paymentFailed(bookingId, user1);

        final List<String> u2AvailableSeatsAfterPaymentFailure = showController.getAvailableSeats(show);
        // Since u1's payment has failed so u2 should now get back all the seats.
        validateSeatsList(u2AvailableSeatsAfterPaymentFailure, audiSeats, ImmutableList.of());
    }
}

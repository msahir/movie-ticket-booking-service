package org.msahir.scenarios;

import org.msahir.controller.*;
import org.msahir.plugin.DemoSeatLockPlugin;
import org.junit.Assert;
import org.msahir.services.*;

import java.util.ArrayList;
import java.util.List;

public class BaseTest {

    protected BookingController bookingController;
    protected ShowController showController;
    protected TheatreController theatreController;
    protected MovieController movieController;
    protected PaymentsController paymentsController;

    protected void setup(int lockTimeout, int allowedRetries) {
        final DemoSeatLockPlugin seatLockProvider = new DemoSeatLockPlugin(lockTimeout);
        final BookingService bookingService = new BookingService(seatLockProvider);
        final MovieService movieService = new MovieService();
        final ShowService showService = new ShowService();
        final TheatreService theatreService = new TheatreService();
        final SeatAvailabilityService seatAvailabilityService
                = new SeatAvailabilityService(bookingService, seatLockProvider);
        final PaymentsService paymentsService = new PaymentsService(allowedRetries, seatLockProvider);

        bookingController = new BookingController(showService, bookingService, theatreService);
        showController = new ShowController(seatAvailabilityService, showService, theatreService, movieService);
        theatreController = new TheatreController(theatreService);
        movieController = new MovieController(movieService);
        paymentsController = new PaymentsController(paymentsService, bookingService);
    }


    protected void validateSeatsList(List<String> seatsList, List<String> allSeatsInAudi, List<String> excludedSeats) {
        for (String includedSeat: allSeatsInAudi) {
            if (!excludedSeats.contains(includedSeat)) {
                Assert.assertTrue(seatsList.contains(includedSeat));
            }
        }

        for (String excludedSeat: excludedSeats) {
            Assert.assertFalse(seatsList.contains(excludedSeat));
        }
    }

    protected List<String> createSeats(TheatreController theatreController, String audi, int numRows, int numSeatsInRow) {
        List<String> seats = new ArrayList<>();
        for (int row = 0; row < numRows; row++) {
            for (int seatNo = 0; seatNo < numSeatsInRow; seatNo++) {
                String seat = theatreController.createSeatInAudi(row, seatNo, audi);
                seats.add(seat);
            }
        }
        return seats;
    }

    protected String setupAudi() {
        final String theatre = theatreController.createTheatre("PVR MG Road");
        return theatreController.createAudiInTheatre("Audi 1", theatre);
    }
}

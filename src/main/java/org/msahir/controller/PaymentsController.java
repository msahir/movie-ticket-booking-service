package org.msahir.controller;

import org.msahir.model.Booking;
import org.msahir.services.BookingService;
import org.msahir.services.PaymentsService;
import lombok.NonNull;

public class PaymentsController {
    private final PaymentsService paymentsService;
    private final BookingService bookingService;

    public PaymentsController(PaymentsService paymentsService, BookingService bookingService) {
        this.paymentsService = paymentsService;
        this.bookingService = bookingService;
    }

    public void paymentFailed(@NonNull final String bookingId, @NonNull final String user) {
        paymentsService.processPaymentFailed(bookingService.getBooking(bookingId), user);
    }

    public void paymentSuccess(@NonNull final  String bookingId, @NonNull final String user) {
        Booking booking = bookingService.getBooking(bookingId);
        paymentsService.processPaymentSuccess(booking, user);
        bookingService.confirmBooking(booking, user);
    }

}

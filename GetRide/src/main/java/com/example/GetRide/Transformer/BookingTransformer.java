package com.example.GetRide.Transformer;

import com.example.GetRide.Dto.request.BookingRequest;
import com.example.GetRide.Dto.response.BookingResponse;
import com.example.GetRide.Enum.BookingStatus;
import com.example.GetRide.Model.Booking;
import com.example.GetRide.Model.Cab;

import java.util.UUID;

public class BookingTransformer {
    public static Booking bookingRequestToBooking(BookingRequest bookingRequest,
                                                  Cab cab) {
        return Booking.builder()
                .bookingId(String.valueOf(UUID.randomUUID()))
                .pickup(bookingRequest.getPickup())
                .destination(bookingRequest.getDestination())
                .bookingStatus(BookingStatus.CONFIRMED)
                .totalDistance(bookingRequest.getTotalDistance())
                .totalFare(bookingRequest.getTotalDistance()*cab.getFarePerKm())
                .build();
    }

    public static BookingResponse bookingToBookingResponse(Booking booking) {
        return BookingResponse.builder()
                .bookingId(booking.getBookingId())
                .pickup(booking.getPickup())
                .destination(booking.getDestination())
                .bookingStatus(booking.getBookingStatus())
                .totalDistance(booking.getTotalDistance())
                .totalFare(booking.getTotalFare())
                .bookedAt(booking.getBookedAt())
                .customerResponse(CustomerTransformer.customerToCustomerResponse(booking.getCustomer()))
                .driverResponse(DriverTransformer.driverToDriverResponse(booking.getDriver()))
                .build();
    }
}

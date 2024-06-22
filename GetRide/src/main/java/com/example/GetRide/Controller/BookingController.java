package com.example.GetRide.Controller;

import com.example.GetRide.Dto.request.BookingRequest;
import com.example.GetRide.Dto.response.BookingResponse;
import com.example.GetRide.Service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/book")
public class BookingController {
    @Autowired
    BookingService bookingService;

    @PostMapping
    public ResponseEntity bookCab(@RequestParam(value = "coupon-applied", required = false) boolean couponApplied,
                                  @RequestBody BookingRequest bookingRequest) {
        try {
            BookingResponse response = bookingService.bookCab(bookingRequest);
            return new ResponseEntity(response, HttpStatus.CREATED);
        }
        catch (Exception e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}

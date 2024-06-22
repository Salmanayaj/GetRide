package com.example.GetRide.Service;

import com.example.GetRide.Dto.request.BookingRequest;
import com.example.GetRide.Dto.response.BookingResponse;
import com.example.GetRide.Exception.CabNotFoundException;
import com.example.GetRide.Exception.CustomerNotFoundException;
import com.example.GetRide.Model.Booking;
import com.example.GetRide.Model.Cab;
import com.example.GetRide.Model.Customer;
import com.example.GetRide.Model.Driver;
import com.example.GetRide.Repository.BookingRepository;
import com.example.GetRide.Repository.CabRepository;
import com.example.GetRide.Repository.CustomerRepository;
import com.example.GetRide.Repository.DriverRepository;
import com.example.GetRide.Transformer.BookingTransformer;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final CustomerRepository customerRespository;
    private final CabRepository cabRepository;
    private final DriverRepository driverRepository;
    private final BookingRepository bookingRepo;

    @Autowired
    JavaMailSender javaMailSender;

//    public BookingService(CustomerRespository customerRespository,
//                          CabRepository cabRepository,
//                          DriverRepository driverRepository,
//                          BookingRepo bookingRepo) {
//        this.customerRespository = customerRespository;
//        this.cabRepository = cabRepository;
//        this.driverRepository = driverRepository;
//        this.bookingRepo = bookingRepo;
//    }

    public BookingResponse bookCab(BookingRequest bookingRequest) {
        Customer customer = customerRespository.findByEmailId(bookingRequest.getCustomerEmail());
        if(ObjectUtils.isEmpty(customer)) {
            throw new CustomerNotFoundException("Invalid email Id");
        }

        Optional<Cab> optionalCab = cabRepository.getRandomAvailableCab();
        if(optionalCab.isEmpty()) {
            throw new CabNotFoundException("Seems like all the drivers are busy");
        }
        Cab cab = optionalCab.get();
        Driver driver = cab.getDriver();
        cab.setBooked(true);

        // booking entity
        Booking booking = BookingTransformer.bookingRequestToBooking(bookingRequest,cab);
        booking.setCustomer(customer);
        booking.setDriver(driver);
        Booking savedBooking = bookingRepo.save(booking);

        // set the remaining attributes
        customer.getBookings().add(savedBooking);
        driver.getBookings().add(savedBooking);

        customerRespository.save(customer); // customer + savedBooking
        driverRepository.save(driver); // driver + savedBooking

        sendEmail(savedBooking);

        // prepare response dto
        return BookingTransformer.bookingToBookingResponse(savedBooking);
    }

    private void sendEmail(Booking booking) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("acciojobspring@gmail.com");
        simpleMailMessage.setTo(booking.getCustomer().getEmailId());
        simpleMailMessage.setSubject("Booking confirmed!!");
        simpleMailMessage.setText("Congrats! " +
                booking.getCustomer().getName() + " Your ride is confirmed!" + " " +
                "Your booking id is: "+booking.getBookingId());
        javaMailSender.send(simpleMailMessage);
    }

}

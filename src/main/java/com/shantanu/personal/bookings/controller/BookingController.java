package com.shantanu.personal.bookings.controller;

import com.shantanu.personal.bookings.dto.BookingDTO;
import com.shantanu.personal.bookings.model.Booking;
import com.shantanu.personal.bookings.service.BookingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/bookingservice/bookings")
public class BookingController {

    @Autowired
    BookingService bookingService;

    @PostMapping("/bookings")
    public ResponseEntity<Object> createDepartment(@RequestBody BookingDTO bookingDTO) {
        try {
            Booking booking = bookingService.createBooking(bookingDTO);
            return new ResponseEntity<>(booking, HttpStatus.OK);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/bookings/{bookingId}")
    public ResponseEntity<Object> updateBookingById(@PathVariable UUID bookingId, @RequestBody BookingDTO bookingDTO) {
        try {
            Booking booking = bookingService.updateBooking(bookingId, bookingDTO);
            return new ResponseEntity<>(booking, HttpStatus.OK);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/bookings/{bookingId}")
    public ResponseEntity<Object> getBookingById(@PathVariable UUID bookingId) {
        try {
            Booking booking = bookingService.getBookingById(bookingId);
            if (booking == null) {
                return new ResponseEntity<>(String.format("Booking with ID: %s wasn't found", bookingId),
                        HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(booking, HttpStatus.OK);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/bookings/department/{department}")
    public ResponseEntity<Object> getBookingsByDepartment(
            @PathVariable(name = "department") String departmentName) {
        try {
            Set<Booking> bookingSet = bookingService.getBookingsByDepartment(departmentName);
            return new ResponseEntity<>(bookingSet, HttpStatus.OK);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/bookings/currencies")
    public ResponseEntity<Object> getListOfAllUsedCurrencies() {
        try {
            return new ResponseEntity<>(bookingService.getUsedCurrencyList(), HttpStatus.OK);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/sum/{currency}")
    public ResponseEntity<Object> getSumOfCurrency(@PathVariable String currency) {
        try {
            return new ResponseEntity<>(bookingService.getSumOfCurrency(currency), HttpStatus.OK);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/bookings/dobusiness/{bookingId}")
    public ResponseEntity<Object> doBusinessForBooking(@PathVariable UUID bookingId) {
        try {
            return new ResponseEntity<>(bookingService.doBusinessForBookingById(bookingId), HttpStatus.OK);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

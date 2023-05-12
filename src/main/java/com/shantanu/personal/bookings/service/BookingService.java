package com.shantanu.personal.bookings.service;

import com.shantanu.personal.bookings.data.PersistenceData;
import com.shantanu.personal.bookings.dto.BookingDTO;
import com.shantanu.personal.bookings.model.Booking;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BookingService {

    public Booking createBooking(BookingDTO bookingDTO) {
        Booking booking = new Booking(bookingDTO);
        this.addBookingToPersistence(booking);
        log.info("A new Booking with ID: {} was created.", booking.getBookingId());
        return booking;
    }

    public Booking createBooking(UUID bookingId, BookingDTO bookingDTO) {
        Booking booking = new Booking(bookingId, bookingDTO);
        this.addBookingToPersistence(booking);
        log.info("A new Booking with ID: {} was created.", booking.getBookingId());
        return booking;
    }

    public Booking updateBooking(UUID bookingId, BookingDTO bookingDTO) {
        if (!PersistenceData.bookingMap.containsKey(bookingId)) {
            log.info("Booking with ID: {} doesn't exist.", bookingId);
            return createBooking(bookingId, bookingDTO);
        } else {
            Booking booking = PersistenceData.bookingMap.get(bookingId);
            String existingCurrency = new String(booking.getCurrency());
            String existingDepartmentName = new String(booking.getDepartmentName());
            booking.modifyBooking(bookingDTO);
            if (!existingCurrency.equals(booking.getCurrency())) {
                PersistenceData.currencyBookingSetMap.get(existingCurrency).remove(booking);
                if (PersistenceData.currencyBookingSetMap.containsKey(booking.getCurrency())) {
                    PersistenceData.currencyBookingSetMap.get(booking.getCurrency()).add(booking);
                } else {
                    Set<Booking> bookingSet = new HashSet<>();
                    bookingSet.add(booking);
                    PersistenceData.currencyBookingSetMap.put(booking.getCurrency(), bookingSet);
                }
            }
            String departmentName = booking.getDepartmentName();
            if (!existingDepartmentName.equals(departmentName)) {
                PersistenceData.departmentBookingSetMap.get(existingDepartmentName).remove(booking);
                if (PersistenceData.departmentBookingSetMap.containsKey(departmentName)) {
                    PersistenceData.departmentBookingSetMap.get(departmentName).add(booking);
                } else {
                    Set<Booking> bookingSet = new HashSet<>();
                    bookingSet.add(booking);
                    PersistenceData.departmentBookingSetMap.put(departmentName, bookingSet);
                }
            }
            log.info("Booking with ID: {} was successfully updated.", bookingId);
            return booking;
        }
    }

    public Booking getBookingById(UUID bookingId) {
        Booking booking = PersistenceData.bookingMap.get(bookingId);
        if (booking == null) {
            log.warn("No booking exists for the Booking ID: {}", bookingId);
        }
        log.info("Booking with ID: {} was successfully found.", bookingId);
        return booking;
    }

    public Set<Booking> getBookingsByDepartment(String departmentName) {
        Set<Booking> bookingSet = PersistenceData.departmentBookingSetMap.getOrDefault(departmentName, new HashSet<>());
        log.info("Found {} bookings with departmentName: {}", bookingSet.size(), departmentName);
        return bookingSet;
    }

    public Set<String> getUsedCurrencyList() {
        Set<String> currencySet = PersistenceData.currencyBookingSetMap.keySet();
        log.info("Found {} currencies.", currencySet.size());
        return currencySet;
    }

    public double getSumOfCurrency(String currency) {
        if (!PersistenceData.currencyBookingSetMap.containsKey(currency)) {
            return 0.0D;
        }
        Set<Booking> bookingSet = PersistenceData.currencyBookingSetMap.get(currency);
        double sum = bookingSet.stream().mapToDouble(Booking::getPrice).sum();
        log.info("Total Sum of Currency: {} was found to be: {}", currency, sum);
        return sum;
    }

    public String doBusinessForBookingById(UUID bookingId) {
        Booking booking = this.getBookingById(bookingId);
        if (booking == null) {
            log.error("Can't do business for Booking with ID: {}", bookingId);
            return null;
        }
        return booking.getDepartment().doBusiness();
    }

    private void addBookingToPersistence(Booking booking) {
        PersistenceData.bookingMap.put(booking.getBookingId(), booking);
        if (PersistenceData.currencyBookingSetMap.containsKey(booking.getCurrency())) {
            PersistenceData.currencyBookingSetMap.get(booking.getCurrency()).add(booking);
        } else {
            Set<Booking> bookingSet = new HashSet<>();
            bookingSet.add(booking);
            PersistenceData.currencyBookingSetMap.put(booking.getCurrency(), bookingSet);
        }

        String departmentName = booking.getDepartment().getName();
        if (PersistenceData.departmentBookingSetMap.containsKey(departmentName)) {
            PersistenceData.departmentBookingSetMap.get(departmentName).add(booking);
        } else {
            Set<Booking> bookingSet = new HashSet<>();
            bookingSet.add(booking);
            PersistenceData.departmentBookingSetMap.put(departmentName, bookingSet);
        }
    }

}

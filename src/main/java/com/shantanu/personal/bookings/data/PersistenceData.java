package com.shantanu.personal.bookings.data;

import com.shantanu.personal.bookings.model.Booking;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class PersistenceData {

    public static Map<UUID, Booking> bookingMap = new HashMap<>();

    public static Map<String, Set<Booking>> currencyBookingSetMap = new HashMap<>();

    public static Map<String, Set<Booking>> departmentBookingSetMap = new HashMap<>();

}

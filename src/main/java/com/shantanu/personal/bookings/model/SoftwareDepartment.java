package com.shantanu.personal.bookings.model;

public class SoftwareDepartment implements Department {

    @Override
    public String doBusiness() {
        return "This is Software Department Business.";
    }

    @Override
    public String getName() {
        return "Software Department";
    }

}

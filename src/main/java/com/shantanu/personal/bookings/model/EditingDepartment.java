package com.shantanu.personal.bookings.model;

import lombok.Getter;

@Getter
public class EditingDepartment implements Department {

    @Override
    public String doBusiness() {
        return "This is Editing Department Business";
    }

    @Override
    public String getName() {
        return "Editing Department";
    }

}

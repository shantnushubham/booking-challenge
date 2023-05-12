package com.shantanu.personal.bookings.model.factory;

import com.shantanu.personal.bookings.model.Department;
import com.shantanu.personal.bookings.model.EditingDepartment;
import com.shantanu.personal.bookings.model.SoftwareDepartment;

public class DepartmentFactory {

    public static Department createDepartment(String departmentType) {
        return switch (departmentType) {
            case "Editing Department" -> new EditingDepartment();
            case "Software Department" -> new SoftwareDepartment();
            default -> throw new RuntimeException("Please enter valid Department Type.");
        };
    }

}

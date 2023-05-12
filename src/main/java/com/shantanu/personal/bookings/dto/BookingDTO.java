package com.shantanu.personal.bookings.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.shantanu.personal.bookings.model.Department;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)

@Getter
@Setter
public class BookingDTO {

    private String description;

    private double price;

    private String currency;

    private Date subscription_start_date;

    private String email;

    private String department;

}

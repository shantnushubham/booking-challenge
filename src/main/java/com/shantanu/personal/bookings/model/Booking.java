package com.shantanu.personal.bookings.model;

import com.fasterxml.jackson.annotation.*;
import com.shantanu.personal.bookings.dto.BookingDTO;
import com.shantanu.personal.bookings.model.factory.DepartmentFactory;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)

@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
@Getter
@Setter
public class Booking {

    @Builder.Default
    private UUID bookingId = UUID.randomUUID();

    private String description;

    private double price;

    private String currency;

    private Date subscriptionStartDate;

    private String email;

    @JsonIgnore
    private Department department;

    @JsonProperty("department")
    public String getDepartmentName() {
        return this.department.getName();
    }

    public Booking(BookingDTO dto) {
        this.setBookingId(UUID.randomUUID());
        this.setDepartment(DepartmentFactory.createDepartment(dto.getDepartment()));
        this.setDescription(dto.getDescription());
        this.setPrice(dto.getPrice());
        this.setCurrency(dto.getCurrency());
        this.setSubscriptionStartDate(dto.getSubscription_start_date());
        this.setEmail(dto.getEmail());
    }

    public Booking(UUID id, BookingDTO dto) {
        this.setBookingId(id);
        this.setDepartment(DepartmentFactory.createDepartment(dto.getDepartment()));
        this.setDescription(dto.getDescription());
        this.setPrice(dto.getPrice());
        this.setCurrency(dto.getCurrency());
        this.setSubscriptionStartDate(dto.getSubscription_start_date());
        this.setEmail(dto.getEmail());
    }

    public void modifyBooking(BookingDTO dto) {
        this.setDepartment(DepartmentFactory.createDepartment(dto.getDepartment()));
        this.setDescription(dto.getDescription());
        this.setPrice(dto.getPrice());
        this.setCurrency(dto.getCurrency());
        this.setSubscriptionStartDate(dto.getSubscription_start_date());
        this.setEmail(dto.getEmail());
    }

}

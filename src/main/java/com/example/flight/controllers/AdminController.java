package com.example.flight.controllers;

import com.example.flight.models.Flight;
import com.example.flight.services.FlightService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/admin/flights")
@SecurityRequirement(name = "KeyCloak")
public class AdminController {

    @Autowired
    private FlightService flightService;

    @PostMapping("/v1/insertFlight")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String insertFlight(@RequestParam String fromPort,
                               @RequestParam String toPort,
                               @RequestParam String availableDates,
                               @RequestParam String recurringDays,
                               @RequestParam int capacity) {
        Flight flight = new Flight();
        flight.setFromPort(fromPort);
        flight.setToPort(toPort);
        flight.setAvailableDates(LocalDate.parse(availableDates));
        flight.setRecurringDays(recurringDays);
        flight.setCapacity(capacity);
        flightService.insertFlight(flight);

        return "success";
    }


    @GetMapping("/v1/reportFlights")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<Flight> reportFlights(@RequestParam String fromPort,
                                      @RequestParam String toPort,
                                      @RequestParam int capacity,
                                      @RequestParam String dates,
                                      @RequestParam int page) {
        int size = 10;
        LocalDate availableDate = LocalDate.parse(dates);
        return flightService.reportFlightsWithCapacity(fromPort, toPort, availableDate, capacity, page, size).stream().toList();
    }
}
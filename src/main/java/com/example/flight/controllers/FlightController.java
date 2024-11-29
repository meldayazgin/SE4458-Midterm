package com.example.flight.controllers;

import com.example.flight.models.Flight;
import com.example.flight.services.FlightService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/flights")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @GetMapping("/v1/query")
    public List<Flight> queryFlights(
            @RequestParam String fromPort,
            @RequestParam String toPort,
            @RequestParam String date,
            @RequestParam int page) {
        int size = 10;
        String recurringDays = "";
        LocalDate availableDate = LocalDate.parse(date);

        return flightService.getAvailableFlights(fromPort, toPort, availableDate.toString(), recurringDays, page, size)
                .getContent();
    }
}

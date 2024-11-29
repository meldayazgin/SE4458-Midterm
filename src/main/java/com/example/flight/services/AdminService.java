package com.example.flight.services;

import com.example.flight.models.Flight;
import com.example.flight.repositories.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AdminService {

    private final FlightRepository flightRepository;

    @Autowired
    public AdminService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public Flight insertFlight(Flight flight) {
        return flightRepository.save(flight);
    }

    public Page<Flight> reportFlightsWithCapacity(String fromPort, String toPort, LocalDate availableDate, int capacity, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return flightRepository.findByCapacityGreaterThanEqual(
                fromPort, toPort, availableDate, capacity, pageable);
    }

    public Page<Flight> getAvailableFlights(String fromPort, String toPort, String availableDates, String recurringDays, int page, int size) {
        LocalDate date = LocalDate.parse(availableDates);
        Pageable pageable = PageRequest.of(page, size);
        return flightRepository.findAvailableFlights(fromPort, toPort, date, recurringDays, pageable);
    }
}

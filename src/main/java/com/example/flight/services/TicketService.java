package com.example.flight.services;

import com.example.flight.models.Flight;
import com.example.flight.models.Ticket;
import com.example.flight.repositories.TicketRepository;
import com.example.flight.repositories.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private FlightRepository flightRepository;

    public boolean buyTicket(String date, String fromPort, String toPort, String passengerFullName) {
        LocalDate bookingDate = java.time.LocalDate.parse(date);
        String recurringDays = "Monday";
        Flight flight = flightRepository.findAvailableFlights(fromPort, toPort, bookingDate, recurringDays, PageRequest.of(0, 1))
                .getContent().stream().findFirst().orElse(null);

        if (flight != null && flight.getCapacity() > 0) {
            Ticket ticket = new Ticket();
            ticket.setusername(passengerFullName);
            ticket.setDate(bookingDate);
            ticket.setCheckedIn(false);
            ticket.setFlight(flight);
            ticketRepository.save(ticket);
            flight.setCapacity(flight.getCapacity() - 1);
            flightRepository.save(flight);

            return true;
        } else {

            return false;
        }
    }

    public boolean checkInTicket(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElse(null);
        if (ticket != null && !ticket.isCheckedIn()) {
            ticket.setCheckedIn(true);
            ticketRepository.save(ticket);
            return true;
        }
        return false;
    }
}

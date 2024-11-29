package com.example.flight.controllers;
import com.example.flight.services.TicketService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@RestController
@RequestMapping("/Ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("/v1/buyTicket")
    public ResponseEntity<String> buyTicket(@RequestParam String date,
                                            @RequestParam String fromPort,
                                            @RequestParam String toPort,
                                            @RequestParam String passengerFullName) {
        try {
            LocalDate bookingDate = LocalDate.parse(date);
            boolean isSuccess = ticketService.buyTicket(date, fromPort, toPort, passengerFullName);
            if (isSuccess) {
                return ResponseEntity.ok("success");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("error: could not process ticket");
            }
        } catch (DateTimeParseException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("error: invalid date format");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error: internal server error");
        }
    }


    @PostMapping("/v1/checkin/{ticketId}")
    public String checkInTicket(@PathVariable Long ticketId) {
        boolean isCheckedIn = ticketService.checkInTicket(ticketId);
        return isCheckedIn ? "success" : "error";
    }
}
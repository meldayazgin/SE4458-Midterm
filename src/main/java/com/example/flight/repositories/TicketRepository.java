package com.example.flight.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.flight.models.Ticket;


public interface TicketRepository extends JpaRepository<Ticket, Long> {

}

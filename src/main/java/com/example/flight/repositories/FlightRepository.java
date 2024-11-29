package com.example.flight.repositories;

import com.example.flight.models.Flight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface FlightRepository extends JpaRepository<Flight, Long> {


    @Query("SELECT f FROM Flight f WHERE f.fromPort = :fromPort AND f.toPort = :toPort " +
            "AND f.availableDates = :availableDates AND f.capacity > 0 " +
            "AND f.recurringDays LIKE %:recurringDays%")
    Page<Flight> findAvailableFlights(@Param("fromPort") String fromPort,
                                      @Param("toPort") String toPort,
                                      @Param("availableDates") LocalDate availableDates,
                                      @Param("recurringDays") String recurringDays,
                                      Pageable pageable);


    Page<Flight> findByCapacityGreaterThanEqual(String fromPort, String toPort, LocalDate availableDates, int capacity, Pageable pageable);

    Page<Flight> findAllByFromPortAndToPortAndAvailableDatesAndCapacity(String fromPort, String toPort, LocalDate availableDates, int capacity, Pageable pageable);
}

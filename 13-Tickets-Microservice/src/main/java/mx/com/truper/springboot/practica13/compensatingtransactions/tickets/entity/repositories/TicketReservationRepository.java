package mx.com.truper.springboot.practica13.compensatingtransactions.tickets.entity.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.truper.springboot.practica13.compensatingtransactions.tickets.entity.TicketReservation;

@Repository
public interface TicketReservationRepository extends JpaRepository<TicketReservation, Long> {

}

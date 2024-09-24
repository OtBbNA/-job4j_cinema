package ru.job4j.cinema.service.ticket;

import ru.job4j.cinema.model.Ticket;

import java.util.Collection;
import java.util.Optional;

public interface TicketService {

    boolean save(Ticket ticket);

    Optional<Ticket> findById(int id);

    Collection<Ticket> findAll();
}

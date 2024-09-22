package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.Ticket;

import java.util.Collection;
import java.util.Optional;

public interface TicketRepository {

    Ticket save(Ticket ticket);

    void deleteById(int id);

    Optional<Ticket> findById(int id);

    Collection<Ticket> findAll();
}

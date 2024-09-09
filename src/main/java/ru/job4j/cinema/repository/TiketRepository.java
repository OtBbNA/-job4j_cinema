package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.Ticket;

import java.util.Optional;

public interface TiketRepository {

    Ticket save(Ticket ticket);

    Optional<Ticket> findById(int id);
}

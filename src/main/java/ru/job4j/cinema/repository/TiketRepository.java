package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.Tiket;

import java.util.Optional;

public interface TiketRepository {

    Tiket save(Tiket tiket);

    Optional<Tiket> findByUser(String email);

    Optional<Tiket> findById(int id);

    Optional<Tiket> deleteById(int id);
}

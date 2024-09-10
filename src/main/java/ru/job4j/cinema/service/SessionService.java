package ru.job4j.cinema.service;

import ru.job4j.cinema.model.Session;

import java.util.Optional;

public interface SessionService {

    Optional<Session> findById(int id);
}

package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Session;

import java.util.Optional;

@Service
public class SimpleSessionService implements SessionService {

    private final SessionService sessionService;

    public SimpleSessionService(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Override
    public Optional<Session> findById(int id) {
        return sessionService.findById(id);
    }
}

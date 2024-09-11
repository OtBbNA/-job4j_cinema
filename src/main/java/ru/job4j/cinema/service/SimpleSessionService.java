package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.repository.SessionRepository;

import java.util.Optional;

@Service
public class SimpleSessionService implements SessionService {

    private final SessionRepository sessionRepository;

    public SimpleSessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public Optional<Session> findById(int id) {
        return sessionRepository.findById(id);
    }
}

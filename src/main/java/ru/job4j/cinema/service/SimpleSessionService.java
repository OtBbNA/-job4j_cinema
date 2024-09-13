package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.dto.SessionDto;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.repository.SessionRepository;

import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SimpleSessionService implements SessionService {

    private final SessionRepository sessionRepository;

    private final FilmService filmService;

    private final HallService hallService;

    public SimpleSessionService(SessionRepository sessionRepository, FilmService filmService, HallService hallService) {
        this.sessionRepository = sessionRepository;
        this.filmService = filmService;
        this.hallService = hallService;
    }

    @Override
    public Optional<SessionDto> findById(int id) {
        Optional<Session> getSession = sessionRepository.findById(id);
        if (getSession.isPresent()) {
            Session session = getSession.get();
            return Optional.of(new SessionDto(id,
                    filmService.findById(session.getFilmId()).get(),
                    hallService.findById(session.getHallsId()).get(),
                    session.getStartTime().format(DateTimeFormatter.ofPattern("HH:mm")).toString(),
                    session.getEndTime().format(DateTimeFormatter.ofPattern("HH:mm")).toString(),
                    session.getPrice()
                    ));
        }
        return Optional.empty();
    }

    @Override
    public Collection<SessionDto> findAll() {
        ConcurrentHashMap<Integer, SessionDto> sessionDto = new ConcurrentHashMap<>();
        for (Session session : sessionRepository.findAll()) {
            sessionDto.put(session.getId(), new SessionDto(session.getId(),
                    filmService.findById(session.getFilmId()).get(),
                    hallService.findById(session.getHallsId()).get(),
                    session.getStartTime().format(DateTimeFormatter.ofPattern("HH:mm")).toString(),
                    session.getEndTime().format(DateTimeFormatter.ofPattern("HH:mm")).toString(),
                    session.getPrice()
            ));
        }
        return sessionDto.values();
    }
}

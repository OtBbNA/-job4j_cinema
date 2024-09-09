package ru.job4j.cinema.repository;

import org.sql2o.Sql2o;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.model.Ticket;

import java.util.Optional;

public class Sql2oSessionRepository implements SessionRepository {

    private final Sql2o sql2o;

    public Sql2oSessionRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Optional<Session> findById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM film_sessions WHERE id = :id");
            query.addParameter("id", id);
            var session = query.setColumnMappings(Session.COLUMN_MAPPING).executeAndFetchFirst(Session.class);
            return Optional.ofNullable(session);
        }
    }
}

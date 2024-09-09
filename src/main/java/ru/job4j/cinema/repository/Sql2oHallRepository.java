package ru.job4j.cinema.repository;

import org.sql2o.Sql2o;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.model.Session;

import java.util.Optional;

public class Sql2oHallRepository implements HallRepository {

    private final Sql2o sql2o;

    public Sql2oHallRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Optional<Hall> findById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM halls WHERE id = :id");
            query.addParameter("id", id);
            var hall = query.setColumnMappings(Hall.COLUMN_MAPPING).executeAndFetchFirst(Hall.class);
            return Optional.ofNullable(hall);
        }
    }
}

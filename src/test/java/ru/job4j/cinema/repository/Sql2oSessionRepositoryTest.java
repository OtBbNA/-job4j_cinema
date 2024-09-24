package ru.job4j.cinema.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sql2o.Sql2o;
import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.repository.session.Sql2oSessionRepository;
import ru.job4j.cinema.repository.user.Sql2oUserRepository;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Properties;

import static org.assertj.core.api.Assertions.*;

class Sql2oSessionRepositoryTest {

    private static Sql2oSessionRepository sql2oSessionRepository;

    private static Sql2o sql2o;

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oUserRepository.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");
        var configuration = new DatasourceConfiguration();

        var datasource = configuration.connectionPool(url, username, password);

        sql2o = configuration.databaseClient(datasource);
        sql2oSessionRepository = new Sql2oSessionRepository(sql2o);
    }

    @Test
    void findById() {
        assertThat(sql2oSessionRepository.findById(1).get()).isEqualTo(new Session(1, 4, 1, LocalDateTime.of(2024, Month.JUNE, 16, 9, 0, 0), LocalDateTime.of(2024, Month.JUNE, 16, 11, 20, 0), 15));
    }

    @Test
    void findByFilmId() {
        var expectedSessions = List.of(
                new Session(1, 4, 1, LocalDateTime.of(2024, Month.JUNE, 16, 9, 0, 0), LocalDateTime.of(2024, Month.JUNE, 16, 11, 20, 0), 15),
                new Session(12, 4, 6, LocalDateTime.of(2024, Month.JUNE, 16, 10, 55, 0), LocalDateTime.of(2024, Month.JUNE, 16, 13, 15, 0), 15)
        );
        assertThat(sql2oSessionRepository.findByFilmId(4)).isEqualTo(expectedSessions);
    }

    @Test
    void findAll() {
        assertThat(sql2oSessionRepository.findAll()).hasSize(12);
    }
}
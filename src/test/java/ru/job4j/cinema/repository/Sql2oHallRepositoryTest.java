package ru.job4j.cinema.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sql2o.Sql2o;
import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.model.Hall;

import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

class Sql2oHallRepositoryTest {

    private static Sql2oHallRepository sql2oHallRepository;

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
        sql2oHallRepository = new Sql2oHallRepository(sql2o);
    }

    @Test
    void findById() {
        assertThat(sql2oHallRepository.findById(1).get()).isEqualTo(new Hall(1, "Нора1", 5, 15, "Уютное маленькое место, с большим расстоянием между местами для интровертов"));
    }
}
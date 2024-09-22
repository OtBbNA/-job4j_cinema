package ru.job4j.cinema.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sql2o.Sql2o;
import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.model.Film;

import java.util.Properties;

import static org.assertj.core.api.Assertions.*;

class Sql2oFilmRepositoryTest {

    private static Sql2oFilmRepository sql2oFilmRepository;

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
        sql2oFilmRepository = new Sql2oFilmRepository(sql2o);
    }

    @Test
    void whenFindAllThenGetAll() {
        assertThat(sql2oFilmRepository.findAll()).hasSize(6);
    }

    @Test
    void whenFindByIDThenGetFilmWithSameId() {
        assertThat(sql2oFilmRepository.findById(1).get()).isEqualTo(new Film(1, "Большой кущ ", "Фрэнки Три Копыта должен был переправить краденый куст редкой малины из Пустого Поля в Дикие Озера своему боссу Эви. Но вместо этого герой попадает в эпицентр больших неприятностей. Сделав ставку на подпольном боксерском поединке, Фрэнки попадает в водоворот весьма нежелательных событий. Вокруг героя и его груза развертывается сложная интрига с участием множества колоритных персонажей дна Пустого Поля - русского гангстера, троих незадачливых грабителей, хитрого боксера по имени Микки Один Укус, угрюмого громилы грозного мафиози Камня, каждый из которых норовит в одиночку сорвать Большой кущ. Настолько же опасная, насколько и анекдотичная, ситуация постепенно выходит из-под контроля и превращается в огромный ком проблем для ее участников, скоро катящийся к неожиданному и остроумному финалу.", 2000, 6, 6, 103, 1));
    }
}
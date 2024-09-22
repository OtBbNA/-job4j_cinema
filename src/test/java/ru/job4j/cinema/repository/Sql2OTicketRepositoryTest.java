package ru.job4j.cinema.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;
import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.model.Ticket;

import java.util.List;
import java.util.Properties;

import static java.time.LocalDateTime.now;
import static java.util.Collections.emptyList;
import static java.util.Optional.empty;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class Sql2OTicketRepositoryTest {

    private static Sql2oTicketRepository sql2oTicketRepository;

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
        sql2oTicketRepository = new Sql2oTicketRepository(sql2o);
    }

    @AfterEach
    public void clearTickets() {
        var tickets = sql2oTicketRepository.findAll();
        for (var ticket : tickets) {
            sql2oTicketRepository.deleteById(ticket.getId());
        }
    }

    @Test
    void whenSaveThenGetSame() {
        var ticket = new Ticket(1, 1, 5, 15, 1);
        var savedTicket = sql2oTicketRepository.save(ticket);
        assertThat(savedTicket).usingRecursiveComparison().isEqualTo(ticket);
    }

    @Test
    void whenSaveSerialThenGetAll() {
        var ticket1 = sql2oTicketRepository.save(new Ticket(1, 1, 5, 15, 1));
        var ticket2 = sql2oTicketRepository.save(new Ticket(2, 2, 4, 15, 1));
        var ticket3 = sql2oTicketRepository.save(new Ticket(3, 3, 7, 20, 1));
        var savedTickets = sql2oTicketRepository.findAll();
        assertThat(savedTickets).isEqualTo(List.of(ticket1, ticket2, ticket3));
    }

    @Test
    void whenSaveSameSessionIdRowNumberPlaceNumberThenGetException() {
        var ticket1 = sql2oTicketRepository.save(new Ticket(1, 1, 5, 15, 1));
        Assertions.assertThrows(Sql2oException.class, () -> sql2oTicketRepository.save(new Ticket(2, 1, 5, 15, 1)));
    }

    @Test
    public void whenDontSaveThenNothingFound() {
        assertThat(sql2oTicketRepository.findAll()).isEqualTo(emptyList());
        assertThat(sql2oTicketRepository.findById(0)).isEqualTo(empty());
    }

    @Test
    public void whenDeleteThenGetEmptyOptional() {
        var ticket = sql2oTicketRepository.save(new Ticket(1, 1, 5, 15, 1));
        sql2oTicketRepository.deleteById(ticket.getId());
        var savedTicket = sql2oTicketRepository.findById(ticket.getId());
        assertThat(savedTicket).isEqualTo(empty());
    }

    @Test
    void whenFindByIdThenGetSame() {
        var ticket = new Ticket(1, 1, 5, 15, 1);
        var savedTicket = sql2oTicketRepository.save(ticket);
        assertThat(savedTicket).isEqualTo(sql2oTicketRepository.findById(ticket.getId()).get());
    }

    @Test
    void whenFindByInvalidIdThenGetFalse() {
        assertThat(sql2oTicketRepository.findById(0)).isEqualTo(empty());
    }
}
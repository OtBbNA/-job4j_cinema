package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.dto.SessionDto;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.service.film.FilmService;
import ru.job4j.cinema.service.session.SessionService;
import ru.job4j.cinema.service.ticket.TicketService;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ScheduleControllerTest {

    private SessionService sessionService;

    private FilmService filmService;

    private TicketService ticketService;

    private ScheduleController scheduleController;

    private List<FilmDto> films;

    private List<Hall> halls;

    @BeforeEach
    public void initServices() {
        sessionService = mock(SessionService.class);
        ticketService = mock(TicketService.class);
        filmService = mock(FilmService.class);
        scheduleController = new ScheduleController(sessionService, ticketService);
        var hall1 = new Hall(1, "Нора1", 5, 15, "Описание");
        var hall2 = new Hall(2, "Нора2", 4, 15, "Описание");
        var film1 = new FilmDto(1, "Большой кущ", "Описание", 2000, 6, 103, "криминал", 1);
        var film2 = new FilmDto(2, "Капуцин с бульвара Человеков", "Описание", 1987, 4, 94, "комедия", 2);
        films = List.of(film1, film2);
        halls = List.of(hall1, hall2);
    }

    @Test
    public void whenRequestSchedulePageThenGetPageWithSessions() {
        var session1 = new SessionDto(1, films.get(0), halls.get(0), LocalDateTime.of(2024, Month.JUNE, 16, 9, 00).toString(), LocalDateTime.of(2024, Month.JUNE, 16, 11, 20).toString(), 15);
        var session2 = new SessionDto(2, films.get(1), halls.get(1), LocalDateTime.of(2024, Month.JUNE, 16, 9, 00).toString(), LocalDateTime.of(2024, Month.JUNE, 16, 10, 34).toString(), 10);
        var expectedSchedule = List.of(session1, session2);
        when(sessionService.findAll()).thenReturn(expectedSchedule);

        var model = new ConcurrentModel();
        var view = scheduleController.getSchedulePage(model);
        var actualSchedule = model.getAttribute("schedules");

        assertThat(view).isEqualTo("schedule/schedule");
        assertThat(actualSchedule).isEqualTo(expectedSchedule);
    }

    @Test
    public void whenRequestScheduleByFilmIdThenGetPageWithSessionsByFilmId() {
        var session1 = new SessionDto(1, films.get(0), halls.get(0), LocalDateTime.of(2024, Month.JUNE, 16, 9, 00).toString(), LocalDateTime.of(2024, Month.JUNE, 16, 11, 20).toString(), 15);
        var session2 = new SessionDto(2, films.get(0), halls.get(1), LocalDateTime.of(2024, Month.JUNE, 16, 9, 00).toString(), LocalDateTime.of(2024, Month.JUNE, 16, 11, 20).toString(), 15);
        var expectedSchedule = List.of(session1, session2);
        when(sessionService.findByFilmId(1)).thenReturn(expectedSchedule);

        var model = new ConcurrentModel();
        var view = scheduleController.getSchedulePageByFilmId(model, 1);
        var actualSchedule = model.getAttribute("schedules");

        assertThat(view).isEqualTo("schedule/schedule");
        assertThat(actualSchedule).isEqualTo(expectedSchedule);
    }

    @Test
    public void whenScheduleGetByIdThenGetPageTicketForThisSession() {
        var session = new SessionDto(1, films.get(0), halls.get(0), LocalDateTime.of(2024, Month.JUNE, 16, 9, 00).toString(), LocalDateTime.of(2024, Month.JUNE, 16, 11, 20).toString(), 15);
        when(sessionService.findById(session.getId())).thenReturn(Optional.of(session));

        var model = new ConcurrentModel();
        var view = scheduleController.getById(model, 1, new MockHttpSession());
        var actualSchedule = model.getAttribute("schedule");

        assertThat(view).isEqualTo("schedule/ticket");
        assertThat(actualSchedule).isEqualTo(session);
    }

    @Test
    public void whenGetByIdUnfindedSessionThenGetErrorPage() {
        when(sessionService.findById(any(Integer.class))).thenReturn(Optional.empty());

        var model = new ConcurrentModel();
        var view = scheduleController.getById(model, any(Integer.class), new MockHttpSession());
        var actualMessage = model.getAttribute("message");

        assertThat(view).isEqualTo("errors/404");
        assertThat(actualMessage).isEqualTo("Сессия не найдена");
    }

    @Test
    public void whenSaveTicketIsSuccesThenGetMessagePage() {
        var ticket = new Ticket(1, 1, 5, 15, 1);
        when(ticketService.save(ticket)).thenReturn(true);

        var model = new ConcurrentModel();
        var view = scheduleController.save(ticket, model);
        var actualMessage = model.getAttribute("message");

        assertThat(view).isEqualTo("message/message");
        assertThat(actualMessage).isEqualTo("Билет успешно приобретен");
    }

    @Test
    public void whenSaveTicketHaveOccupiedSeatThenGetErrorPage() {
        var ticket = new Ticket(1, 1, 5, 15, 1);
        when(ticketService.save(ticket)).thenReturn(false);

        var model = new ConcurrentModel();
        var view = scheduleController.save(ticket, model);
        var actualMessage = model.getAttribute("message");

        assertThat(view).isEqualTo("message/message");
        assertThat(actualMessage).isEqualTo("Извините, указанное место занято");
    }

    @Test
    public void whenSomeExceptionThrownThenGetErrorPageWithMessage() {
        var expectedException = new RuntimeException();
        when(ticketService.save(any())).thenThrow(expectedException);

        var model = new ConcurrentModel();
        var view = scheduleController.save(new Ticket(), model);
        var actualExceptionMessage = model.getAttribute("message");

        assertThat(view).isEqualTo("errors/404");
        assertThat(actualExceptionMessage).isEqualTo(expectedException.getMessage());
    }
}
package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.service.FilmService;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LibraryControllerTest {

    private FilmService filmService;

    private  LibraryController libraryController;

    @BeforeEach
    public void initServices() {
        filmService = mock(FilmService.class);
        libraryController = new LibraryController(filmService);
    }

    @Test
    void whenRequestFilmsListPageThenGetPageWithFilms() {
        var film1 = new FilmDto(1, "Большой кущ", "Описание", 2000, 6, 103, "криминал", 1);
        var film2 = new FilmDto(2, "Капуцин с бульвара Человеков", "Описание", 1987, 4, 94, "комедия", 2);
        var expectedList = List.of(film1, film2);
        when(filmService.findAll()).thenReturn(expectedList);

        var model = new ConcurrentModel();
        var view = libraryController.getLibraryPage(model);
        var actualList = model.getAttribute("films");

        assertThat(view).isEqualTo("library/library");
        assertThat(actualList).isEqualTo(expectedList);
    }

    @Test
    void whenGetByIdThenGetPageOneForThisFilm() {
        var film = new FilmDto(1, "Большой кущ", "Описание", 2000, 6, 103, "криминал", 1);
        when(filmService.findById(1)).thenReturn(Optional.of(film));

        var model = new ConcurrentModel();
        var view = libraryController.getById(model, 1);
        var actualSchedule = model.getAttribute("film");

        assertThat(view).isEqualTo("library/one");
        assertThat(actualSchedule).isEqualTo(film);
    }

    @Test
    public void whenGetByIdUnfindedFilmThenGetErrorPage() {
        when(filmService.findById(any(Integer.class))).thenReturn(Optional.empty());

        var model = new ConcurrentModel();
        var view = libraryController.getById(model, any(Integer.class));
        var actualMessage = model.getAttribute("message");

        assertThat(view).isEqualTo("errors/404");
        assertThat(actualMessage).isEqualTo("Фильм не найден");
    }
}
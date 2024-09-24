package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.service.film.FilmService;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class IndexControllerTest {

    private FilmService filmService;

    private IndexController indexController;

    @BeforeEach
    public void initServices() {
        filmService = mock(FilmService.class);
        indexController = new IndexController(filmService);
    }

    @Test
    public void whenRequestIndexPageThenGetPageWithHome() {
        var filmDto1 = new FilmDto(1, "Человек с джангл-стрит", "Описание", 2013, 5, 180, "комедия", 1);
        var filmDto2 = new FilmDto(2, "День человека", "Описание", 1993, 3, 101, "комедия", 2);
        var expectedFilms = List.of(filmDto1, filmDto2);

        when(filmService.findAll()).thenReturn(expectedFilms);

        var model = new ConcurrentModel();
        var view = indexController.getIndex(model);
        var actualFilms = model.getAttribute("films");

        assertThat(view).isEqualTo("index");
        assertThat(actualFilms).isEqualTo(expectedFilms);
    }
}
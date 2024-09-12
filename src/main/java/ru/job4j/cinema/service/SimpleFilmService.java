package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.repository.FilmRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SimpleFilmService implements FilmService {

    private final FilmRepository filmRepository;

    private final GenreService genreService;

    public SimpleFilmService(FilmRepository sql2oFilmRepository, GenreService genreService) {
        this.filmRepository = sql2oFilmRepository;
        this.genreService = genreService;
    }

    @Override
    public Collection<FilmDto> findAll() {
        ConcurrentHashMap<Integer, FilmDto> filmsDto = new ConcurrentHashMap<>();
        for (Film film : filmRepository.findAll()) {
            filmsDto.put(film.getId(), new FilmDto(
                    film.getName(),
                    film.getDescription(),
                    film.getYear(),
                    film.getMinimalAge(),
                    film.getDurationInMinutes(),
                    genreService.findById(film.getGenreId()).get().getName(),
                    film.getFileId()
            ));
        }
        return filmsDto.values();
    }

    @Override
    public Optional<FilmDto> findById(int id) {
        var film = filmRepository.findById(id);
        if (film.isEmpty()) {
            return Optional.empty();
        }
        Film filmRsl = film.get();
        FilmDto filmDto = new FilmDto(
                filmRsl.getName(),
                filmRsl.getDescription(),
                filmRsl.getYear(),
                filmRsl.getMinimalAge(),
                filmRsl.getDurationInMinutes(),
                    genreService.findById(filmRsl.getGenreId()).get().getName(),
                filmRsl.getFileId()
            );
        return Optional.of(filmDto);
    }
}

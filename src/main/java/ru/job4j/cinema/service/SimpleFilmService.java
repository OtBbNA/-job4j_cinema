package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.repository.FilmRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class SimpleFilmService implements FilmService {

    private final FilmRepository filmRepository;

    private final GenreService genreService;

    private final FileService fileService;

    public SimpleFilmService(FilmRepository filmRepository, GenreService genreService, FileService fileService) {
        this.filmRepository = filmRepository;
        this.genreService = genreService;
        this.fileService = fileService;
    }

    @Override
    public Collection<FilmDto> findAll() {
        ConcurrentHashMap<AtomicInteger, FilmDto> filmsDto = new ConcurrentHashMap<>();
        for (Film film : filmRepository.findAll()) {
            filmsDto.putIfAbsent(new AtomicInteger(film.getId()), new FilmDto(
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
        return Optional.empty();
    }
}

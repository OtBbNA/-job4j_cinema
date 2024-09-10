package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.repository.FilmRepository;

import java.util.Collection;
import java.util.Optional;

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
        return null;
    }

    @Override
    public Optional<FilmDto> findById(int id) {
        return Optional.empty();
    }
}

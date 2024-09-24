package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.cinema.service.film.FilmService;

@ThreadSafe
@Controller
@RequestMapping("/library")
public class LibraryController {

    FilmService filmService;

    public LibraryController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/library")
    public String getLibraryPage(Model model) {
        model.addAttribute("films", filmService.findAll());
        return "library/library";
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id) {
        var filmOptional = filmService.findById(id);
        if (filmOptional.isEmpty()) {
            model.addAttribute("message", "Фильм не найден");
            return "errors/404";
        }
        model.addAttribute("film", filmOptional.get());
        return "library/one";
    }
}

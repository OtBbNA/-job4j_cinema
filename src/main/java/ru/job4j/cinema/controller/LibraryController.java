package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.cinema.service.FilmService;

@ThreadSafe
@Controller
@RequestMapping("/library")
public class LibraryController {

    FilmService filmService;

    public LibraryController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/library")
    public String getSchedulePage(Model model) {
        model.addAttribute("films", filmService.findAll());
        return "/library/library";
    }
}

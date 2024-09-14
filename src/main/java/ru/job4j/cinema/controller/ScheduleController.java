package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.cinema.service.SessionService;

@ThreadSafe
@Controller
@RequestMapping("/schedule")
public class ScheduleController {

    private final SessionService sessionService;

    public ScheduleController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping("/schedule")
    public String getSchedulePage(Model model) {
        model.addAttribute("schedules", sessionService.findAll());
        return "schedule/schedule";
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id) {
        var sessionOptional = sessionService.findById(id);
        if (sessionOptional.isEmpty()) {
            model.addAttribute("message", "Сессия не найдена");
            return "errors/404";
        }
        model.addAttribute("schedule", sessionOptional.get());
        return "schedule/ticket";
    }
}

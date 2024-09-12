package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/ticket")
    public String getTicketPage() {
        return "schedule/ticket";
    }
}

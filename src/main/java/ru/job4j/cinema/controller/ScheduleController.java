package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.service.SessionService;
import ru.job4j.cinema.service.TicketService;

import javax.servlet.http.HttpSession;

@ThreadSafe
@Controller
@RequestMapping("/schedule")
public class ScheduleController {

    private final SessionService sessionService;

    private final TicketService ticketService;

    public ScheduleController(SessionService sessionService, TicketService ticketService) {
        this.sessionService = sessionService;
        this.ticketService = ticketService;
    }

    @GetMapping("/schedule")
    public String getSchedulePage(Model model) {
        model.addAttribute("schedules", sessionService.findAll());
        return "schedule/schedule";
    }

    @GetMapping("/scheduleByFilmId/{id}")
    public String getSchedulePageByFilmId(Model model, @PathVariable int id) {
        model.addAttribute("schedules", sessionService.findByFilmId(id));
        return "schedule/schedule";
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id, HttpSession session) {
        var sessionOptional = sessionService.findById(id);
        if (sessionOptional.isEmpty()) {
            model.addAttribute("message", "Сессия не найдена");
            return "errors/404";
        }
        model.addAttribute("schedule", sessionOptional.get());
        model.addAttribute("sessionId", id);
        model.addAttribute("user", session.getAttribute("user"));
        return "schedule/ticket";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Ticket ticket, Model model) {
        try {
            if (!ticketService.save(ticket)) {
                model.addAttribute("message", "Извините, указанное место занято");
                return "message/message";
            }
            model.addAttribute("message", "Билет успешно приобретен");
            return "message/message";
        } catch (Exception exception) {
            model.addAttribute("message", exception.getMessage());
            return "errors/404";
        }
    }
}

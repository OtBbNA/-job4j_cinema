package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@ThreadSafe
@Controller
@RequestMapping("/schedule")
public class ScheduleController {

    @GetMapping("/schedule")
    public String getSchedulePage() {
        return "/schedule/schedule";
    }

    @GetMapping("/ticket")
    public String getTicketPage() {
        return "schedule/ticket";
    }
}

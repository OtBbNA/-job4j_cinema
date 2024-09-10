package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Ticket;

import java.util.Optional;

@Service
public class SimpleTicketService implements TicketService {

    private final TicketService ticketService;

    public SimpleTicketService(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @Override
    public Ticket save(Ticket ticket) {
        return null;
    }

    @Override
    public Optional<Ticket> findById(int id) {
        return Optional.empty();
    }
}

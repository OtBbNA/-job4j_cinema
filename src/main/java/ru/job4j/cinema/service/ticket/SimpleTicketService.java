package ru.job4j.cinema.service.ticket;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.repository.ticket.TicketRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class SimpleTicketService implements TicketService {

    private final TicketRepository ticketRepository;

    public SimpleTicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public boolean save(Ticket ticket) {
        for (Ticket savedTicket : findAll()) {
            if (savedTicket.getSessionId() == ticket.getSessionId()
                    && savedTicket.getPlaceNumber() == ticket.getPlaceNumber()
                    && savedTicket.getRowNumber() == ticket.getRowNumber()) {
                return false;
            }
        }
        ticketRepository.save(ticket);
        return true;
    }

    @Override
    public Optional<Ticket> findById(int id) {
        return ticketRepository.findById(id);
    }

    @Override
    public Collection<Ticket> findAll() {
        return ticketRepository.findAll();
    }
}

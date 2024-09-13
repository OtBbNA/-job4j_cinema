package ru.job4j.cinema.dto;

import ru.job4j.cinema.model.Hall;

import java.util.Objects;

public class SessionDto {

    int id;
    FilmDto filmDto;
    Hall hall;
    String startTime;
    String endTime;
    int price;

    public SessionDto(int id, FilmDto filmDto, Hall hall, String startTime, String endTime, int price) {
        this.id = id;
        this.filmDto = filmDto;
        this.hall = hall;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public FilmDto getFilm() {
        return filmDto;
    }

    public void setFilmDto(FilmDto filmDto) {
        this.filmDto = filmDto;
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SessionDto that = (SessionDto) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

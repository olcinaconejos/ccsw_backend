package org.practicas.backCapgemini.prestamo.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "prestamos")
public class Prestamo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String gameName;
    String clName;
    LocalDateTime startDate;

    public LocalDateTime getEnd() {
        return endDate;
    }

    public void setEnd(LocalDateTime end) {
        this.endDate = end;
    }

    public LocalDateTime getStart() {
        return startDate;
    }

    public void setStart(LocalDateTime start) {
        this.startDate = start;
    }

    public String getClName() {
        return clName;
    }

    public void setClName(String clName) {
        this.clName = clName;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    LocalDateTime endDate;
}

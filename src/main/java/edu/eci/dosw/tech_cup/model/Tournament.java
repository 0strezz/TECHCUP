package edu.eci.dosw.tech_cup.model;

import edu.eci.dosw.tech_cup.model.enums.TournamentStatus;

public class Tournament {

    private Long id;
    private String name;
    private String game;
    private String location;
    private Integer maxPlayers;
    private TournamentStatus status;

    public Tournament() {
    }

    public Tournament(Long id, String name, String game, String location, Integer maxPlayers, TournamentStatus status) {
        this.id = id;
        this.name = name;
        this.game = game;
        this.location = location;
        this.maxPlayers = maxPlayers;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGame() {
        return game;
    }

    public String getLocation() {
        return location;
    }

    public Integer getMaxPlayers() {
        return maxPlayers;
    }

    public TournamentStatus getStatus() {
        return status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setMaxPlayers(Integer maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public void setStatus(TournamentStatus status) {
        this.status = status;
    }
}
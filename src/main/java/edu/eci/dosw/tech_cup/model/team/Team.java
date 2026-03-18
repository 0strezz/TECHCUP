package edu.eci.dosw.tech_cup.model.team;

import edu.eci.dosw.tech_cup.model.user.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Team {
    private UUID id;
    private String name;
    private String crestUrl;
    private String uniformColor;
    private List<Player> players;

    public Team() {
        this.id = UUID.randomUUID();
        this.players = new ArrayList<>();
    }

    public Team(String name, String uniformColor) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.uniformColor = uniformColor;
        this.players = new ArrayList<>();
    }

    public void addPlayer(Player player) {
        if (player != null && !players.contains(player)) {
            players.add(player);
        }
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCrestUrl() { return crestUrl; }
    public void setCrestUrl(String crestUrl) { this.crestUrl = crestUrl; }
    public String getUniformColor() { return uniformColor; }
    public void setUniformColor(String uniformColor) { this.uniformColor = uniformColor; }
    public List<Player> getPlayers() { return players; }
}

package edu.eci.dosw.tech_cup.model.user;

import edu.eci.dosw.tech_cup.model.team.Team;

public interface SportProfile {
    boolean isAvailableForTeam();
    void setAvailableForTeam(boolean available);
    boolean isUnderPlayingProcess();
    void setUnderPlayingProcess(boolean underPlayingProcess);
}

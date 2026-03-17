package edu.eci.dosw.tech_cup.model.tournament;
import java.util.Date;

public class TournamentConfig {
    private String rules;
    private Date registrationDeadline;

    public TournamentConfig(String rules, Date registrationDeadline) {
        this.rules = rules;
        this.registrationDeadline = registrationDeadline;
    }

    public String getRules() { return rules; }
    public Date getRegistrationDeadline() { return registrationDeadline; }

    public void setRules(String rules) { this.rules = rules; }
    public void setRegistrationDeadline(Date registrationDeadline) { this.registrationDeadline = registrationDeadline; }
}

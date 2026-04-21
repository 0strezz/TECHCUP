package edu.eci.dosw.tech_cup.model;

import edu.eci.dosw.tech_cup.model.enums.*;
import edu.eci.dosw.tech_cup.model.match.*;
import edu.eci.dosw.tech_cup.model.payment.Payment;
import edu.eci.dosw.tech_cup.model.security.AuditLog;
import edu.eci.dosw.tech_cup.model.statistics.PlayerStatistics;
import edu.eci.dosw.tech_cup.model.statistics.StandingEntry;
import edu.eci.dosw.tech_cup.model.team.Invitation;
import edu.eci.dosw.tech_cup.model.team.Lineup;
import edu.eci.dosw.tech_cup.model.team.Team;
import edu.eci.dosw.tech_cup.model.tournament.Field;
import edu.eci.dosw.tech_cup.model.tournament.TournamentConfig;
import edu.eci.dosw.tech_cup.model.user.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ModelsTest {

    // ---------- edu.eci.dosw.tech_cup.model.Tournament (top-level DTO) ----------
    @Test
    void testTournamentDto() {
        Tournament t = new Tournament();
        t.setId(1L); t.setName("Cup"); t.setGame("Football"); t.setLocation("Bogota"); t.setMaxPlayers(16); t.setStatus(TournamentStatus.DRAFT);
        assertEquals(1L, t.getId()); assertEquals("Cup", t.getName()); assertEquals("Football", t.getGame());
        assertEquals("Bogota", t.getLocation()); assertEquals(16, t.getMaxPlayers()); assertEquals(TournamentStatus.DRAFT, t.getStatus());

        Tournament t2 = new Tournament(2L, "Cup2", "Soccer", "Medellin", 8, TournamentStatus.ACTIVE);
        assertNotNull(t2.getId());
    }

    // ---------- statistics ----------
    @Test
    void testStandingEntry() {
        StandingEntry se = new StandingEntry();
        se.setMatchesPlayed(10); se.setWins(5); se.setDraws(3); se.setLosses(2);
        se.setGoalsFor(15); se.setGoalsAgainst(8); se.setPoints(18);
        assertEquals(10, se.getMatchesPlayed()); assertEquals(5, se.getWins());
        assertEquals(3, se.getDraws()); assertEquals(2, se.getLosses());
        assertEquals(15, se.getGoalsFor()); assertEquals(8, se.getGoalsAgainst());
        assertEquals(18, se.getPoints()); assertEquals(7, se.getGoalDifference());
    }

    @Test
    void testPlayerStatistics() {
        PlayerStatistics ps = new PlayerStatistics();
        ps.setGoals(3); ps.setYellowCards(1); ps.setRedCards(0);
        assertEquals(3, ps.getGoals()); assertEquals(1, ps.getYellowCards()); assertEquals(0, ps.getRedCards());

        PlayerStatistics ps2 = new PlayerStatistics(5, 2, 1);
        assertEquals(5, ps2.getGoals());
    }

    // ---------- security ----------
    @Test
    void testAuditLog() {
        AuditLog al = new AuditLog();
        assertNotNull(al.getId());
        al.setAction("CREATE"); al.setTimestamp(new Date());
        assertEquals("CREATE", al.getAction()); assertNotNull(al.getTimestamp());

        AuditLog al2 = new AuditLog("DELETE", new Date());
        assertNotNull(al2.getId()); assertEquals("DELETE", al2.getAction());
    }

    // ---------- payment ----------
    @Test
    void testPayment() {
        Payment p = new Payment();
        assertNotNull(p.getId()); assertEquals(PaymentStatus.PENDING, p.getStatus());
        p.setDate(new Date()); p.setReceiptUrl("http://test"); p.setStatus(PaymentStatus.APPROVED);
        assertNotNull(p.getDate()); assertEquals("http://test", p.getReceiptUrl());
        assertEquals(PaymentStatus.APPROVED, p.getStatus());

        Payment p2 = new Payment(new Date(), "http://receipt");
        assertEquals(PaymentStatus.PENDING, p2.getStatus());
        p2.approve(); assertEquals(PaymentStatus.APPROVED, p2.getStatus());

        Payment p3 = new Payment();
        p3.reject(); assertEquals(PaymentStatus.REJECTED, p3.getStatus());
    }

    // ---------- match ----------
    @Test
    void testMatch() {
        Match m = new Match();
        assertNotNull(m.getId()); assertEquals(MatchStatus.SCHEDULED, m.getStatus());
        assertNotNull(m.getGoals()); assertNotNull(m.getCards());
        m.setDate(new Date()); m.setStatus(MatchStatus.PLAYED);
        m.setHomeTeam(new Team()); m.setAwayTeam(new Team());
        m.setField(new Field()); m.setTournament(new edu.eci.dosw.tech_cup.model.tournament.Tournament());
        assertNotNull(m.getDate()); assertNotNull(m.getHomeTeam()); assertNotNull(m.getAwayTeam());
        assertNotNull(m.getField()); assertNotNull(m.getTournament());

        Match m2 = new Match(new Date(), new Team(), new Team(), new Field(), new edu.eci.dosw.tech_cup.model.tournament.Tournament());
        assertNotNull(m2.getId());

        MatchResult mr = new MatchResult(2, 1);
        m.setResult(mr);
        assertEquals(MatchStatus.PLAYED, m.getStatus()); assertNotNull(m.getResult());
    }

    @Test
    void testMatchResult() {
        MatchResult mr = new MatchResult();
        mr.setHomeGoals(3); mr.setAwayGoals(1);
        assertEquals(3, mr.getHomeGoals()); assertEquals(1, mr.getAwayGoals());

        MatchResult mr2 = new MatchResult(2, 0);
        assertEquals(2, mr2.getHomeGoals());
    }

    @Test
    void testGoal() {
        Goal g = new Goal();
        g.setMinute(45);
        assertEquals(45, g.getMinute());

        Goal g2 = new Goal(90);
        assertEquals(90, g2.getMinute());
    }

    @Test
    void testCard() {
        Card c = new Card();
        c.setMinute(30); c.setType(CardType.YELLOW);
        assertEquals(30, c.getMinute()); assertEquals(CardType.YELLOW, c.getType());

        Card c2 = new Card(60, CardType.RED);
        assertEquals(60, c2.getMinute()); assertEquals(CardType.RED, c2.getType());
    }

    // ---------- team ----------
    @Test
    void testTeam() {
        Team t = new Team();
        assertNotNull(t.getId()); assertNotNull(t.getPlayers());
        t.setName("Eagles"); t.setCrestUrl("http://crest"); t.setUniformColor("Blue");
        assertEquals("Eagles", t.getName()); assertEquals("http://crest", t.getCrestUrl());
        assertEquals("Blue", t.getUniformColor());

        Team t2 = new Team("Lions", "Red");
        assertNotNull(t2.getId()); assertEquals("Lions", t2.getName());

        Player p = new Player("John", "john@mail.com", "pass");
        t.addPlayer(p);
        assertEquals(1, t.getPlayers().size());
        t.addPlayer(null); // should not add
        assertEquals(1, t.getPlayers().size());
        t.addPlayer(p); // duplicate, should not add
        assertEquals(1, t.getPlayers().size());
        t.removePlayer(p);
        assertEquals(0, t.getPlayers().size());
    }

    @Test
    void testLineup() {
        Lineup l = new Lineup();
        l.setFormation(Formation.F442);
        assertEquals(Formation.F442, l.getFormation());

        Lineup l2 = new Lineup(Formation.F433);
        assertEquals(Formation.F433, l2.getFormation());
    }

    @Test
    void testInvitation() {
        Invitation i = new Invitation();
        assertNotNull(i.getId()); assertEquals(InvitationStatus.PENDING, i.getStatus());
        i.accept(); assertEquals(InvitationStatus.ACCEPTED, i.getStatus());

        Invitation i2 = new Invitation();
        i2.reject(); assertEquals(InvitationStatus.REJECTED, i2.getStatus());

        Invitation i3 = new Invitation();
        i3.setStatus(InvitationStatus.ACCEPTED);
        assertEquals(InvitationStatus.ACCEPTED, i3.getStatus());
    }

    // ---------- tournament (model.tournament package) ----------
    @Test
    void testTournamentModel() {
        edu.eci.dosw.tech_cup.model.tournament.Tournament t = new edu.eci.dosw.tech_cup.model.tournament.Tournament();
        assertNotNull(t.getId()); assertEquals(TournamentStatus.DRAFT, t.getStatus()); assertNotNull(t.getTeams());
        t.setName("World Cup"); t.setStartDate(new Date()); t.setEndDate(new Date());
        t.setStatus(TournamentStatus.ACTIVE); t.setConfig(new TournamentConfig()); t.setTeams(new ArrayList<>());
        assertNotNull(t.getName()); assertNotNull(t.getStartDate()); assertNotNull(t.getEndDate());
        assertNotNull(t.getConfig());

        edu.eci.dosw.tech_cup.model.tournament.Tournament t2 = new edu.eci.dosw.tech_cup.model.tournament.Tournament("Cup", new Date(), new Date());
        assertNotNull(t2.getId());

        t.setStatus(TournamentStatus.DRAFT);
        t.startTournament(); assertEquals(TournamentStatus.ACTIVE, t.getStatus());
        t.finishTournament(); assertEquals(TournamentStatus.FINISHED, t.getStatus());

        // startTournament should not change if not DRAFT
        edu.eci.dosw.tech_cup.model.tournament.Tournament t3 = new edu.eci.dosw.tech_cup.model.tournament.Tournament();
        t3.setStatus(TournamentStatus.ACTIVE);
        t3.startTournament(); assertEquals(TournamentStatus.ACTIVE, t3.getStatus()); // unchanged

        // finishTournament from IN_PROGRESS
        edu.eci.dosw.tech_cup.model.tournament.Tournament t4 = new edu.eci.dosw.tech_cup.model.tournament.Tournament();
        t4.setStatus(TournamentStatus.IN_PROGRESS);
        t4.finishTournament(); assertEquals(TournamentStatus.FINISHED, t4.getStatus());
    }

    @Test
    void testField() {
        Field f = new Field();
        assertNotNull(f.getId());
        f.setName("Stadium A"); assertEquals("Stadium A", f.getName());

        Field f2 = new Field("Stadium B");
        assertNotNull(f2.getId()); assertEquals("Stadium B", f2.getName());
    }

    @Test
    void testTournamentConfig() {
        TournamentConfig tc = new TournamentConfig();
        tc.setRules("No fouls"); tc.setRegistrationDeadline(new Date());
        assertEquals("No fouls", tc.getRules()); assertNotNull(tc.getRegistrationDeadline());

        TournamentConfig tc2 = new TournamentConfig("Rules", new Date());
        assertEquals("Rules", tc2.getRules());
    }

    // ---------- user ----------
    @Test
    void testRoleModel() {
        Role r = new Role();
        assertNotNull(r.getId()); assertNotNull(r.getPermissions());
        r.setId(null); r.setName("ADMIN"); r.setDescription("Admin role"); r.setPermissions(null);
        assertEquals("ADMIN", r.getName()); assertEquals("Admin role", r.getDescription());

        Role r2 = new Role("PLAYER", "Player role");
        assertNotNull(r2.getId());
    }

    @Test
    void testPermissionModel() {
        Permission p = new Permission();
        assertNotNull(p.getId());
        p.setId(null); p.setName("READ"); p.setDescription("Read access");
        assertEquals("READ", p.getName()); assertEquals("Read access", p.getDescription());

        Permission p2 = new Permission("WRITE", "Write access");
        assertNotNull(p2.getId());
    }

    @Test
    void testPlayerModel() {
        Player p = new Player();
        p.setName("John"); p.setEmail("j@j.com"); p.setPassword("pass");
        assertEquals("John", p.getName()); assertEquals("j@j.com", p.getEmail());
        assertTrue(p.checkPassword("pass")); assertFalse(p.checkPassword("wrong"));

        Player p2 = new Player("Jane", "jane@j.com", "pw");
        assertTrue(p2.isAvailableForTeam()); assertFalse(p2.isUnderPlayingProcess());
        p2.setAvailableForTeam(false); p2.setUnderPlayingProcess(true);
        assertFalse(p2.isAvailableForTeam()); assertTrue(p2.isUnderPlayingProcess());
    }

    @Test
    void testAdministrator() {
        Administrator a = new Administrator();
        assertNotNull(a.getId());
        Administrator a2 = new Administrator("Admin", "admin@a.com", "pass");
        assertEquals("Admin", a2.getName());
    }

    @Test
    void testCaptain() {
        Captain c = new Captain();
        assertNotNull(c.getId());
        Captain c2 = new Captain("Cap", "cap@c.com", "pass");
        assertNotNull(c2.createTeam("MyTeam"));
    }

    @Test
    void testUserRoles() {
        Player p = new Player("Test", "test@t.com", "pass");
        Role r = new Role("PLAYER", "Player");
        p.setRoles(List.of(r));
        assertTrue(p.hasRole("PLAYER"));
        assertFalse(p.hasRole("ADMIN"));
        assertNotNull(p.getRoles());
        assertNotNull(p.getPassword());
    }

    @Test
    void testUserCheckPasswordNull() {
        Player p = new Player();
        // password is null
        assertFalse(p.checkPassword("anything"));
    }

    // ---------- enums ----------
    @Test
    void testEnums() {
        for (CardType v : CardType.values()) { assertNotNull(CardType.valueOf(v.name())); }
        for (Formation v : Formation.values()) { assertNotNull(Formation.valueOf(v.name())); }
        for (InvitationStatus v : InvitationStatus.values()) { assertNotNull(InvitationStatus.valueOf(v.name())); }
        for (MatchStatus v : MatchStatus.values()) { assertNotNull(MatchStatus.valueOf(v.name())); }
        for (PaymentStatus v : PaymentStatus.values()) { assertNotNull(PaymentStatus.valueOf(v.name())); }
        for (TournamentStatus v : TournamentStatus.values()) { assertNotNull(TournamentStatus.valueOf(v.name())); }
    }
}

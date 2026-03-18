package test.model.tournament;

import edu.eci.dosw.tech_cup.model.enums.TournamentStatus;
import edu.eci.dosw.tech_cup.model.team.Team;
import edu.eci.dosw.tech_cup.model.tournament.Field;
import edu.eci.dosw.tech_cup.model.tournament.Tournament;
import edu.eci.dosw.tech_cup.model.tournament.TournamentConfig;
import org.junit.jupiter.api.*;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tournament model tests")
class TournamentTest {
    @Nested
    @DisplayName("TournamentConfig")
    class TournamentConfigTests {

        @Test
        @DisplayName("No-arg constructor creates a config with null fields")
        void noArgConstructorProducesNullFields() {
            TournamentConfig cfg = new TournamentConfig();
            assertNull(cfg.getRules());
            assertNull(cfg.getRegistrationDeadline());
        }

        @Test
        @DisplayName("Full constructor stores rules and deadline")
        void fullConstructorStoresFields() {
            Date deadline = new Date();
            TournamentConfig cfg = new TournamentConfig("FIFA rules", deadline);
            assertEquals("FIFA rules", cfg.getRules());
            assertEquals(deadline,     cfg.getRegistrationDeadline());
        }

        @Test
        @DisplayName("setRules updates the rules field")
        void setRulesWorks() {
            TournamentConfig cfg = new TournamentConfig();
            cfg.setRules("Custom rules");
            assertEquals("Custom rules", cfg.getRules());
        }

        @Test
        @DisplayName("setRegistrationDeadline updates the deadline")
        void setDeadlineWorks() {
            TournamentConfig cfg = new TournamentConfig();
            Date d = new Date();
            cfg.setRegistrationDeadline(d);
            assertEquals(d, cfg.getRegistrationDeadline());
        }
    }

    @Nested
    @DisplayName("Field")
    class FieldTests {

        @Test
        @DisplayName("No-arg constructor assigns a non-null UUID")
        void noArgConstructorHasId() {
            Field f = new Field();
            assertNotNull(f.getId());
        }

        @Test
        @DisplayName("Full constructor stores name and assigns a UUID")
        void fullConstructorStoresName() {
            Field f = new Field("Main Stadium");
            assertNotNull(f.getId());
            assertEquals("Main Stadium", f.getName());
        }

        @Test
        @DisplayName("Two Field instances have distinct IDs")
        void distinctIds() {
            Field f1 = new Field("A");
            Field f2 = new Field("B");
            assertNotEquals(f1.getId(), f2.getId());
        }

        @Test
        @DisplayName("setName updates the name")
        void setNameWorks() {
            Field f = new Field("Old Name");
            f.setName("New Name");
            assertEquals("New Name", f.getName());
        }
    }

    @Nested
    @DisplayName("Tournament – construction")
    class TournamentConstructionTests {

        @Test
        @DisplayName("No-arg constructor sets DRAFT status and empty team list")
        void noArgConstructorDefaults() {
            Tournament t = new Tournament();
            assertNotNull(t.getId());
            assertEquals(TournamentStatus.DRAFT, t.getStatus());
            assertNotNull(t.getTeams());
            assertTrue(t.getTeams().isEmpty());
        }

        @Test
        @DisplayName("Full constructor stores name, dates and sets DRAFT status")
        void fullConstructorStoresFields() {
            Date start = new Date();
            Date end   = new Date(start.getTime() + 86_400_000L);
            Tournament t = new Tournament("Spring Cup", start, end);

            assertNotNull(t.getId());
            assertEquals("Spring Cup",          t.getName());
            assertEquals(start,                  t.getStartDate());
            assertEquals(end,                    t.getEndDate());
            assertEquals(TournamentStatus.DRAFT, t.getStatus());
            assertTrue(t.getTeams().isEmpty());
        }

        @Test
        @DisplayName("Two tournaments have different IDs")
        void distinctIds() {
            Tournament t1 = new Tournament("T1", new Date(), new Date());
            Tournament t2 = new Tournament("T2", new Date(), new Date());
            assertNotEquals(t1.getId(), t2.getId());
        }
    }

    @Nested
    @DisplayName("Tournament – setters")
    class TournamentSetterTests {

        private Tournament tournament;

        @BeforeEach
        void setUp() {
            tournament = new Tournament("Cup", new Date(), new Date());
        }

        @Test
        @DisplayName("setName updates name")
        void setNameWorks() {
            tournament.setName("Winter Cup");
            assertEquals("Winter Cup", tournament.getName());
        }

        @Test
        @DisplayName("setStartDate updates start date")
        void setStartDateWorks() {
            Date newDate = new Date(0);
            tournament.setStartDate(newDate);
            assertEquals(newDate, tournament.getStartDate());
        }

        @Test
        @DisplayName("setEndDate updates end date")
        void setEndDateWorks() {
            Date newDate = new Date(999_999L);
            tournament.setEndDate(newDate);
            assertEquals(newDate, tournament.getEndDate());
        }

        @Test
        @DisplayName("setStatus overrides the current status")
        void setStatusWorks() {
            tournament.setStatus(TournamentStatus.ACTIVE);
            assertEquals(TournamentStatus.ACTIVE, tournament.getStatus());
        }

        @Test
        @DisplayName("setConfig attaches a TournamentConfig")
        void setConfigWorks() {
            TournamentConfig cfg = new TournamentConfig("Rules", new Date());
            tournament.setConfig(cfg);
            assertSame(cfg, tournament.getConfig());
        }

        @Test
        @DisplayName("setTeams replaces the team list")
        void setTeamsWorks() {
            List<Team> teams = List.of(new Team("A", "Red"), new Team("B", "Blue"));
            tournament.setTeams(teams);
            assertEquals(2, tournament.getTeams().size());
        }
    }

    @Nested
    @DisplayName("Tournament – startTournament()")
    class StartTournamentTests {

        @Test
        @DisplayName("DRAFT → ACTIVE when startTournament() is called")
        void draftBecomesActive() {
            Tournament t = new Tournament("Cup", new Date(), new Date());
            assertEquals(TournamentStatus.DRAFT, t.getStatus());

            t.startTournament();

            assertEquals(TournamentStatus.ACTIVE, t.getStatus());
        }

        @Test
        @DisplayName("Calling startTournament() on ACTIVE has no effect")
        void activeRemainsActive() {
            Tournament t = new Tournament("Cup", new Date(), new Date());
            t.startTournament();  // DRAFT → ACTIVE
            t.startTournament();  // should stay ACTIVE

            assertEquals(TournamentStatus.ACTIVE, t.getStatus());
        }

        @Test
        @DisplayName("Calling startTournament() on FINISHED has no effect")
        void finishedRemainsFinished() {
            Tournament t = new Tournament("Cup", new Date(), new Date());
            t.setStatus(TournamentStatus.FINISHED);
            t.startTournament();

            assertEquals(TournamentStatus.FINISHED, t.getStatus());
        }

        @Test
        @DisplayName("Calling startTournament() on IN_PROGRESS has no effect")
        void inProgressRemainsInProgress() {
            Tournament t = new Tournament("Cup", new Date(), new Date());
            t.setStatus(TournamentStatus.IN_PROGRESS);
            t.startTournament();

            assertEquals(TournamentStatus.IN_PROGRESS, t.getStatus());
        }
    }

    @Nested
    @DisplayName("Tournament – finishTournament()")
    class FinishTournamentTests {

        @Test
        @DisplayName("ACTIVE → FINISHED when finishTournament() is called")
        void activeBecomesFinished() {
            Tournament t = new Tournament("Cup", new Date(), new Date());
            t.setStatus(TournamentStatus.ACTIVE);

            t.finishTournament();

            assertEquals(TournamentStatus.FINISHED, t.getStatus());
        }

        @Test
        @DisplayName("IN_PROGRESS → FINISHED when finishTournament() is called")
        void inProgressBecomesFinished() {
            Tournament t = new Tournament("Cup", new Date(), new Date());
            t.setStatus(TournamentStatus.IN_PROGRESS);

            t.finishTournament();

            assertEquals(TournamentStatus.FINISHED, t.getStatus());
        }

        @Test
        @DisplayName("DRAFT does not transition to FINISHED via finishTournament()")
        void draftNotFinished() {
            Tournament t = new Tournament("Cup", new Date(), new Date());
            assertEquals(TournamentStatus.DRAFT, t.getStatus());

            t.finishTournament();

            assertEquals(TournamentStatus.DRAFT, t.getStatus());
        }

        @Test
        @DisplayName("Calling finishTournament() twice is idempotent")
        void callingTwiceIsIdempotent() {
            Tournament t = new Tournament("Cup", new Date(), new Date());
            t.setStatus(TournamentStatus.ACTIVE);

            t.finishTournament();
            t.finishTournament(); // second call should have no effect

            assertEquals(TournamentStatus.FINISHED, t.getStatus());
        }
    }

    @Nested
    @DisplayName("Tournament – team list management")
    class TournamentTeamListTests {

        @Test
        @DisplayName("Teams can be added to the tournament's team list")
        void addTeam() {
            Tournament t = new Tournament("Cup", new Date(), new Date());
            Team team = new Team("Alpha", "Red");
            t.getTeams().add(team);

            assertEquals(1, t.getTeams().size());
            assertTrue(t.getTeams().contains(team));
        }

        @Test
        @DisplayName("Multiple teams can be added")
        void addMultipleTeams() {
            Tournament t = new Tournament("Cup", new Date(), new Date());
            t.getTeams().add(new Team("Alpha", "Red"));
            t.getTeams().add(new Team("Beta",  "Blue"));
            t.getTeams().add(new Team("Gamma", "Green"));

            assertEquals(3, t.getTeams().size());
        }

        @Test
        @DisplayName("Teams list is empty on a fresh Tournament")
        void teamsListIsEmptyInitially() {
            assertTrue(new Tournament().getTeams().isEmpty());
        }
    }

    @Nested
    @DisplayName("Tournament – full lifecycle")
    class TournamentLifecycleTests {

        @Test
        @DisplayName("DRAFT → ACTIVE → IN_PROGRESS → FINISHED via setStatus + finish")
        void fullLifecycle() {
            Tournament t = new Tournament("Cup", new Date(), new Date());
            assertEquals(TournamentStatus.DRAFT, t.getStatus());

            t.startTournament();
            assertEquals(TournamentStatus.ACTIVE, t.getStatus());

            t.setStatus(TournamentStatus.IN_PROGRESS);
            assertEquals(TournamentStatus.IN_PROGRESS, t.getStatus());

            t.finishTournament();
            assertEquals(TournamentStatus.FINISHED, t.getStatus());
        }
    }
}

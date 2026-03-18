package test.model.user;

import edu.eci.dosw.tech_cup.model.user.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

@DisplayName("User model tests")
class UserTest {

    static class ConcreteUser extends User {
        ConcreteUser() { super(); }
        ConcreteUser(String name, String email, String password) {
            super(name, email, password);
        }
    }

    @Nested
    @DisplayName("User – constructor & identity")
    class UserConstructorTests {

        @Test
        @DisplayName("Default constructor assigns a non-null UUID")
        void defaultConstructorAssignsId() {
            User u = new ConcreteUser();
            assertNotNull(u.getId(), "id must not be null after default constructor");
        }

        @Test
        @DisplayName("Full constructor sets all fields")
        void fullConstructorSetsFields() {
            User u = new ConcreteUser("Alice", "alice@example.com", "secret");
            assertNotNull(u.getId());
            assertEquals("Alice",             u.getName());
            assertEquals("alice@example.com", u.getEmail());
            assertEquals("secret",            u.getPassword());
        }

        @Test
        @DisplayName("Two users created sequentially have different IDs")
        void eachInstanceHasUniqueId() {
            User u1 = new ConcreteUser("A", "a@a.com", "p1");
            User u2 = new ConcreteUser("B", "b@b.com", "p2");
            assertNotEquals(u1.getId(), u2.getId());
        }
    }

    @Nested
    @DisplayName("User – setters")
    class UserSetterTests {

        @Test
        @DisplayName("setName updates the name")
        void setNameWorks() {
            User u = new ConcreteUser("Old", "e@e.com", "p");
            u.setName("New");
            assertEquals("New", u.getName());
        }

        @Test
        @DisplayName("setEmail updates the email")
        void setEmailWorks() {
            User u = new ConcreteUser("A", "old@e.com", "p");
            u.setEmail("new@e.com");
            assertEquals("new@e.com", u.getEmail());
        }

        @Test
        @DisplayName("setPassword updates the password")
        void setPasswordWorks() {
            User u = new ConcreteUser("A", "e@e.com", "old");
            u.setPassword("new");
            assertEquals("new", u.getPassword());
        }
    }

    @Nested
    @DisplayName("User – checkPassword")
    class CheckPasswordTests {

        @Test
        @DisplayName("Returns true for the correct password")
        void correctPasswordReturnsTrue() {
            User u = new ConcreteUser("A", "e@e.com", "myPass");
            assertTrue(u.checkPassword("myPass"));
        }

        @Test
        @DisplayName("Returns false for a wrong password")
        void wrongPasswordReturnsFalse() {
            User u = new ConcreteUser("A", "e@e.com", "myPass");
            assertFalse(u.checkPassword("wrongPass"));
        }

        @Test
        @DisplayName("Returns false when stored password is null")
        void nullStoredPasswordReturnsFalse() {
            User u = new ConcreteUser();
            assertFalse(u.checkPassword("anything"));
        }

        @Test
        @DisplayName("Returns false when argument is null")
        void nullArgumentReturnsFalse() {
            User u = new ConcreteUser("A", "e@e.com", "myPass");
            assertFalse(u.checkPassword(null));
        }

        @Test
        @DisplayName("Password check is case-sensitive")
        void passwordCheckIsCaseSensitive() {
            User u = new ConcreteUser("A", "e@e.com", "Pass");
            assertFalse(u.checkPassword("pass"));
        }
    }

    @Nested
    @DisplayName("Organizer")
    class OrganizerTests {

        @Test
        @DisplayName("No-arg constructor creates a valid Organizer")
        void noArgConstructor() {
            Organizer o = new Organizer();
            assertNotNull(o.getId());
        }

        @Test
        @DisplayName("Full constructor stores fields")
        void fullConstructor() {
            Organizer o = new Organizer("Bob", "bob@org.com", "p");
            assertEquals("Bob",         o.getName());
            assertEquals("bob@org.com", o.getEmail());
        }

        @Test
        @DisplayName("Organizer is a User")
        void isUser() {
            assertInstanceOf(User.class, new Organizer());
        }
    }

    @Nested
    @DisplayName("Administrator")
    class AdministratorTests {

        @Test
        @DisplayName("No-arg constructor creates a valid Administrator")
        void noArgConstructor() {
            Administrator a = new Administrator();
            assertNotNull(a.getId());
        }

        @Test
        @DisplayName("Full constructor stores fields")
        void fullConstructor() {
            Administrator a = new Administrator("Charlie", "charlie@admin.com", "pass");
            assertEquals("Charlie",          a.getName());
            assertEquals("charlie@admin.com", a.getEmail());
        }

        @Test
        @DisplayName("Administrator is a User")
        void isUser() {
            assertInstanceOf(User.class, new Administrator());
        }
    }

    @Nested
    @DisplayName("Referee")
    class RefereeTests {

        @Test
        @DisplayName("No-arg constructor creates a valid Referee")
        void noArgConstructor() {
            Referee r = new Referee();
            assertNotNull(r.getId());
        }

        @Test
        @DisplayName("Full constructor stores fields")
        void fullConstructor() {
            Referee r = new Referee("Dana", "dana@ref.com", "pass");
            assertEquals("Dana",        r.getName());
            assertEquals("dana@ref.com", r.getEmail());
        }

        @Test
        @DisplayName("Referee is a User")
        void isUser() {
            assertInstanceOf(User.class, new Referee());
        }
    }

    @Nested
    @DisplayName("Relative")
    class RelativeTests {

        @Test
        @DisplayName("No-arg constructor is valid")
        void noArgConstructor() {
            assertNotNull(new Relative().getId());
        }

        @Test
        @DisplayName("Full constructor stores fields")
        void fullConstructor() {
            Relative r = new Relative("Eve", "eve@rel.com", "pass");
            assertEquals("Eve", r.getName());
        }
    }

    @Nested
    @DisplayName("Teacher")
    class TeacherTests {

        @Test
        @DisplayName("No-arg constructor is valid")
        void noArgConstructor() {
            assertNotNull(new Teacher().getId());
        }

        @Test
        @DisplayName("Full constructor stores fields")
        void fullConstructor() {
            Teacher t = new Teacher("Frank", "frank@school.com", "pass");
            assertEquals("Frank", t.getName());
        }
    }

    @Nested
    @DisplayName("Graduated")
    class GraduatedTests {

        @Test
        @DisplayName("No-arg constructor is valid")
        void noArgConstructor() {
            assertNotNull(new Graduated().getId());
        }

        @Test
        @DisplayName("Full constructor stores fields")
        void fullConstructor() {
            Graduated g = new Graduated("Grace", "grace@uni.com", "pass");
            assertEquals("Grace", g.getName());
        }
    }

    @Nested
    @DisplayName("Student")
    class StudentTests {

        @Test
        @DisplayName("No-arg constructor is valid")
        void noArgConstructor() {
            assertNotNull(new Student().getId());
        }

        @Test
        @DisplayName("Full constructor stores fields")
        void fullConstructor() {
            Student s = new Student("Hank", "hank@uni.com", "pass");
            assertEquals("Hank", s.getName());
        }
    }

    @Nested
    @DisplayName("Staff")
    class StaffTests {

        @Test
        @DisplayName("No-arg constructor is valid")
        void noArgConstructor() {
            assertNotNull(new Staff().getId());
        }

        @Test
        @DisplayName("Full constructor stores fields")
        void fullConstructor() {
            Staff s = new Staff("Iris", "iris@club.com", "pass");
            assertEquals("Iris", s.getName());
        }
    }

    @Nested
    @DisplayName("Player")
    class PlayerTests {

        @Test
        @DisplayName("No-arg constructor creates Player with null SportProfile flags")
        void noArgConstructorHasDefaultFlags() {
            Player p = new Player();
            // No-arg sets nothing; flags are Java default false
            assertFalse(p.isAvailableForTeam());
            assertFalse(p.isUnderPlayingProcess());
        }

        @Test
        @DisplayName("Full constructor sets availableForTeam=true, underPlayingProcess=false")
        void fullConstructorSetsDefaults() {
            Player p = new Player("Jack", "jack@game.com", "pass");
            assertTrue(p.isAvailableForTeam());
            assertFalse(p.isUnderPlayingProcess());
        }

        @Test
        @DisplayName("setAvailableForTeam updates the flag")
        void setAvailableForTeam() {
            Player p = new Player("Jack", "jack@game.com", "pass");
            p.setAvailableForTeam(false);
            assertFalse(p.isAvailableForTeam());
        }

        @Test
        @DisplayName("setUnderPlayingProcess updates the flag")
        void setUnderPlayingProcess() {
            Player p = new Player("Jack", "jack@game.com", "pass");
            p.setUnderPlayingProcess(true);
            assertTrue(p.isUnderPlayingProcess());
        }

        @Test
        @DisplayName("Player implements SportProfile")
        void implementsSportProfile() {
            assertInstanceOf(SportProfile.class, new Player());
        }

        @Test
        @DisplayName("Player is a User")
        void isUser() {
            assertInstanceOf(User.class, new Player());
        }
    }

    @Nested
    @DisplayName("Captain")
    class CaptainTests {

        @Test
        @DisplayName("Captain extends Player (is-a Player)")
        void extendPlayer() {
            assertInstanceOf(Player.class, new Captain());
        }

        @Test
        @DisplayName("Captain implements SportProfile via Player")
        void implementsSportProfile() {
            assertInstanceOf(SportProfile.class, new Captain());
        }

        @Test
        @DisplayName("Captain is a User")
        void isUser() {
            assertInstanceOf(User.class, new Captain());
        }

        @Test
        @DisplayName("Full constructor stores inherited fields")
        void fullConstructorStoresFields() {
            Captain c = new Captain("Karen", "karen@team.com", "pass");
            assertEquals("Karen",           c.getName());
            assertEquals("karen@team.com",  c.getEmail());
            assertTrue(c.isAvailableForTeam());
        }

        @Test
        @DisplayName("createTeam returns a non-null UUID")
        void createTeamReturnsUUID() {
            Captain c = new Captain("Karen", "karen@team.com", "pass");
            UUID teamId = c.createTeam("Alpha FC");
            assertNotNull(teamId);
        }

        @Test
        @DisplayName("createTeam returns different IDs on each call")
        void createTeamReturnsDifferentIdsEachTime() {
            Captain c = new Captain("Karen", "karen@team.com", "pass");
            UUID id1 = c.createTeam("Team A");
            UUID id2 = c.createTeam("Team B");
            assertNotEquals(id1, id2);
        }

        @Test
        @DisplayName("SportProfile flags can be toggled on Captain")
        void sportProfileFlagsToggle() {
            Captain c = new Captain("Karen", "karen@team.com", "pass");
            c.setAvailableForTeam(false);
            assertFalse(c.isAvailableForTeam());

            c.setUnderPlayingProcess(true);
            assertTrue(c.isUnderPlayingProcess());
        }

        @Test
        @DisplayName("Captain inherits checkPassword from User")
        void inheritsCheckPassword() {
            Captain c = new Captain("Karen", "karen@team.com", "mySecret");
            assertTrue(c.checkPassword("mySecret"));
            assertFalse(c.checkPassword("wrong"));
        }
    }
}

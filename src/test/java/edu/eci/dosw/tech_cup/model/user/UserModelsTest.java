package edu.eci.dosw.tech_cup.model.user;

import org.junit.jupiter.api.Test;

class UserModelsTest {

    @Test
    void testModels() {
        Administrator a = new Administrator();
        a.setId(null); a.setName("N"); a.setEmail("E"); a.setPassword("P"); a.setRoles(null);
        a.getId(); a.getName(); a.getEmail(); a.getPassword(); a.getRoles();

        new Captain();
        new Graduated();
        new Organizer();
        new Player();
        new Referee();
        new Relative();
        new Staff();
        new Student();
        new Teacher();

        Role r = new Role();
        r.setId(null); r.setName("A"); r.setDescription("B"); r.setPermissions(null);
        r.getId(); r.getName(); r.getDescription(); r.getPermissions();

        Permission p = new Permission();
        p.setId(null); p.setName("A"); p.setDescription("B");
        p.getId(); p.getName(); p.getDescription();
    }
}

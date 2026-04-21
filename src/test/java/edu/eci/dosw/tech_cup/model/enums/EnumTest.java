package edu.eci.dosw.tech_cup.model.enums;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class EnumTest {

    @Test
    void testEnums() {
        assertNotNull(CardType.valueOf("YELLOW"));
        assertNotNull(CardType.values());

        assertNotNull(Formation.valueOf("F442"));
        assertNotNull(Formation.values());

        assertNotNull(InvitationStatus.valueOf("PENDING"));
        assertNotNull(InvitationStatus.values());

        assertNotNull(MatchStatus.valueOf("SCHEDULED"));
        assertNotNull(MatchStatus.values());

        assertNotNull(PaymentStatus.valueOf("PENDING"));
        assertNotNull(PaymentStatus.values());

        assertNotNull(TournamentStatus.valueOf("DRAFT"));
        assertNotNull(TournamentStatus.values());
    }
}

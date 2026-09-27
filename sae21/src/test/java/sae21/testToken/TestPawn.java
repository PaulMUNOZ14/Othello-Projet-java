package sae21.testToken;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.Team;
import model.tokens.Pawn;

public class TestPawn {

    @Test
    void testConstructor() {
        Pawn p = new Pawn(Team.WHITE);
        assertEquals(Team.WHITE, p.getTeam());
    }

    @Test
    void testChangeTeamDoesNotCrash() {
        Pawn p = new Pawn(Team.WHITE);
        assertDoesNotThrow(p::changeTeam);
    }

    @Test
    void testChangeTeamWhiteToBlack() {
        Pawn p = new Pawn(Team.WHITE);
        p.changeTeam();
        assertEquals(Team.BLACK, p.getTeam());
    }

    @Test
    void testChangeTeamBlackToWhite() {
        Pawn p = new Pawn(Team.BLACK);
        p.changeTeam();
        assertEquals(Team.WHITE, p.getTeam());
    }

    @Test
    void testCloneCreatesNewInstance() {
        Pawn p1 = new Pawn(Team.WHITE);
        Pawn p2 = (Pawn) p1.clone();

        assertNotSame(p1, p2);
        assertEquals(p1.getTeam(), p2.getTeam());
    }

    @Test
    void testCharRepr() {
        Pawn p1 = new Pawn(Team.WHITE);
        Pawn p2 = new Pawn(Team.BLACK);

        assertEquals("P", p1.charRepr());
        assertEquals("P", p2.charRepr());
    }
}
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
        assertDoesNotThrow(() -> p.changeTeam());
    }
    @Test
    void testChangeTeam() {
        Pawn p = new Pawn(Team.WHITE);
        p.changeTeam();
        assertEquals(Team.BLACK, p.getTeam());
    }
}
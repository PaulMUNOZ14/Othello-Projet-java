package sae21.testToken;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.Team;
import model.tokens.Ring;

public class TestRing {
	
    @Test
    void testConstructor() {
        Ring r = new Ring(Team.BLACK);
        assertEquals(Team.BLACK, r.getTeam());
    }
    @Test
    void testRingHasNoCrash() {
        Ring r = new Ring(Team.WHITE);
        assertDoesNotThrow(() -> {
            r.toString();
        });
    }
}
package sae21.testToken;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.Team;
import model.tokens.Pawn;
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
    
    @Test
    void testGetColor() {
        assertNotNull(Team.WHITE.getColor());
        assertEquals(java.awt.Color.WHITE, Team.WHITE.getColor());
        assertEquals(java.awt.Color.BLACK, Team.BLACK.getColor());
    }
    
    @Test
    void testClone() {
        Pawn p1 = new Pawn(Team.WHITE);
        Pawn p2 = (Pawn) p1.clone();

        assertEquals(p1.getTeam(), p2.getTeam());
        assertNotSame(p1, p2);
    }
    
    @Test
    void testCharRepr() {
        assertEquals("P", new Pawn(Team.WHITE).charRepr());
        assertEquals("P", new Pawn(Team.BLACK).charRepr());
    }
}
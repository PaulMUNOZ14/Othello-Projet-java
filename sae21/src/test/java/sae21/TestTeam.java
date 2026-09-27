package sae21;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.Team;

class TestTeam {

	@Test
	void testOther() {
		assertEquals(Team.WHITE, Team.BLACK.other());
		assertEquals(Team.BLACK, Team.WHITE.other());
	}
	
	@Test
	void testOtherConsistency() {
	    assertNotSame(Team.BLACK, Team.BLACK.other());
	    assertNotSame(Team.WHITE, Team.WHITE.other());
	}
	
	@Test
	void testGetColor() {
	    assertEquals(java.awt.Color.BLACK, Team.BLACK.getColor());
	    assertEquals(java.awt.Color.WHITE, Team.WHITE.getColor());
	}
}

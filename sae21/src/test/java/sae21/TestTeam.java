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
}

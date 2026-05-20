package sae21.testToken;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.Team;
import model.tokens.Token;

public class TestToken {

	static class FakeToken extends Token {
	    public FakeToken(Team team) {
	        super(team);
	    }

	    @Override
	    public String charRepr() {
	        return "F";
	    }

	    @Override
	    public Token clone() {
	        return new FakeToken(this.team);
	    }
	}
    @Test
    void testConstructor() {
        Token t = new FakeToken(Team.WHITE);
        assertEquals(Team.WHITE, t.getTeam());
    }
    @Test
    void testGetTeam() {
        Token t = new FakeToken(Team.BLACK);
        assertEquals(Team.BLACK, t.getTeam());
    }
    @Test
    void testSetTeamDoesNotCrash() {
        Token t = new FakeToken(Team.WHITE);

        assertDoesNotThrow(() -> t.setTeam(Team.BLACK));
    }
    @Test
    void testCharRepr() {
        Token t = new FakeToken(Team.WHITE);
        assertEquals("F", t.charRepr());
    }
    @Test
    void testClone() {
        Token t = new FakeToken(Team.WHITE);
        Token copy = t.clone();
        assertNotNull(copy);
        assertEquals(t.getTeam(), copy.getTeam());
        assertNotSame(t, copy);
    }
}
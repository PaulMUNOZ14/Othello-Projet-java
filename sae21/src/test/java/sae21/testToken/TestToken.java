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
    void testCharReprReturnsNull() {
        Token t = new FakeToken(Team.WHITE);

        assertNull(t.charRepr());
    }
    @Test
    void testCloneReturnsNull() {
        Token t = new FakeToken(Team.WHITE);
        assertNull(t.clone());
    }
}
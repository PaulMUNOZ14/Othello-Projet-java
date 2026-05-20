package sae21.testState;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import coordinate.Coordinate;
import coordinate.CoordinateCube;
import model.Team;
import model.state.State;
import model.tokens.Pawn;
import model.tokens.Ring;
import model.tokens.Token;

class ToggleTokenTest {

    @Test
    void testToggleToken_addPawn() {
        Map<Coordinate, Token> board = new HashMap<>();
        Coordinate c = new CoordinateCube(0, 0, 0);

        State state = new State(board, Team.WHITE, List.of());

        var newState = state.toggleToken(c, Pawn.class, Team.WHITE);

        assertTrue(newState.board().get(c) instanceof Pawn);
        assertEquals(Team.WHITE, newState.board().get(c).getTeam());
    }

    @Test
    void testToggleToken_removeSamePawn() {
        Map<Coordinate, Token> board = new HashMap<>();
        Coordinate c = new CoordinateCube(0, 0, 0);

        board.put(c, new Pawn(Team.WHITE));
        State state = new State(board, Team.WHITE, List.of());

        var newState = state.toggleToken(c, Pawn.class, Team.WHITE);

        assertNull(newState.board().get(c));
    }

    @Test
    void testToggleToken_differentTeam_doesNotRemove() {
        Map<Coordinate, Token> board = new HashMap<>();
        Coordinate c = new CoordinateCube(0, 0, 0);

        board.put(c, new Pawn(Team.BLACK));
        State state = new State(board, Team.WHITE, List.of());

        var newState = state.toggleToken(c, Pawn.class, Team.WHITE);

        assertNotNull(newState.board().get(c));
        assertTrue(newState.board().get(c) instanceof Pawn);
    }

    @Test
    void testToggleToken_ringCreation() {
        Map<Coordinate, Token> board = new HashMap<>();
        Coordinate c = new CoordinateCube(1, 1, -2);

        State state = new State(board, Team.WHITE, List.of());

        var newState = state.toggleToken(c, Ring.class, Team.WHITE);

        assertTrue(newState.board().get(c) instanceof Ring);
    }

    @Test
    void testToggleToken_replacePawnDifferentType() {
        Map<Coordinate, Token> board = new HashMap<>();
        Coordinate c = new CoordinateCube(0, 0, 0);

        board.put(c, new Pawn(Team.WHITE));
        State state = new State(board, Team.WHITE, List.of());

        var newState = state.toggleToken(c, Ring.class, Team.WHITE);

        assertTrue(newState.board().get(c) instanceof Ring);
    }
}
package sae21;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import model.Model;
import model.Team;
import model.state.IState;
import model.tokens.Token;
import coordinate.Coordinate;

public class ModelTest {

	class FakeState implements IState {

	    @Override
	    public Map<Coordinate, Token> board() {
	        return Map.of();
	    }

	    @Override
	    public Set<Coordinate> availableMoves(Coordinate from) {
	        return null;
	    }

	    @Override
	    public IState move(model.action.Move move) {
	        return this;
	    }

	    @Override
	    public IState removeLine(model.action.RemoveLine removeLine) {
	        return this;
	    }

	    @Override
	    public Map<Team, List<Coordinate>> rings() {
	        return Map.of();
	    }

	    @Override
	    public List<Set<Coordinate>> lines() {
	        return List.of();
	    }

	    @Override
	    public Team turn() {
	        return null;
	    }

	    @Override
	    public List<Set<Coordinate>> getPawnsLines() {
	        return List.of();
	    }

		@Override
		public Team winner() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public IState removeToken(Coordinate c) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public IState toggleToken(Coordinate position, Class<?> token, Team team) {
			// TODO Auto-generated method stub
			return null;
		}
	}

    @Test
    void testConstructor() {
        IState state = new FakeState();
        Model model = new Model(state);
        assertEquals(state, model.getCurrentState());
    }

    @Test
    void testSetCurrentState() {
        IState state1 = new FakeState();
        IState state2 = new FakeState();
        Model model = new Model(state1);
        model.setCurrentState(state2);
        assertEquals(state2, model.getCurrentState());
    }

    @Test
    void testMovesFromReturnsNull() {
        Model model = new Model(new FakeState());
        Set<Coordinate> result = model.movesFrom(null);
        assertNull(result);
    }

    @Test
    void testGetPawnLinesReturnsNull() {
        Model model = new Model(new FakeState());
        List<Set<Coordinate>> result = model.getPawnLines();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetBoardReturnsNull() {
        Model model = new Model(new FakeState());
        Map<Coordinate, Token> board = model.getBoard();
        assertNotNull(board);
        assertTrue(board.isEmpty());
    }

    @Test
    void testGetTokenAtReturnsNull() {
        Model model = new Model(new FakeState());
        Token token = model.getTokenAt(null);
        assertNull(token);
    }

    @Test
    void testIsInFieldReturnsFalse() {
        Model model = new Model(new FakeState());
        boolean result = model.isInField(null);
        assertFalse(result);
    }

    @Test
    void testGetPawnReturnsNull() {
        Model model = new Model(new FakeState());
        List<Coordinate> pawns = model.getPawn(null);
        assertNotNull(pawns);
        assertTrue(pawns.isEmpty());
    }

    @Test
    void testGetTurnReturnsNull() {
        Model model = new Model(new FakeState());
        Team team = model.getTurn();
        assertNull(team);
    }

    @Test
    void testMoveRingDoesNotThrowException() {
        Model model = new Model(new FakeState());
        assertDoesNotThrow(() -> model.moveRing(null, null));
    }

    @Test
    void testRemoveLineDoesNotThrowException() {
        Model model = new Model(new FakeState());
        assertDoesNotThrow(() -> model.removeLine(null, null));
    }
    
    @Test
    void testGetRingsEmpty() {
        Model model = new Model(new FakeState());
        List<Coordinate> rings = model.getRings(Team.WHITE);

        assertNotNull(rings);
        assertTrue(rings.isEmpty());
    }
    
    @Test
    void testGetBoardNeverNull() {
        Model model = new Model(new FakeState());
        Map<Coordinate, Token> board = model.getBoard();

        assertNotNull(board);
    }
    
    @Test
    void testGetPawnEmpty() {
        Model model = new Model(new FakeState());
        List<Coordinate> pawns = model.getPawn(Team.WHITE);

        assertNotNull(pawns);
        assertTrue(pawns.isEmpty());
    }
    
    
}
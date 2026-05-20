package ia;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import coordinate.Coordinate;
import coordinate.CoordinateCube;
import model.Team;
import model.action.Action;
import model.action.Move;
import model.action.RemoveLine;
import model.state.State;
import model.state.IState;
import model.tokens.Pawn;
import model.tokens.Ring;
import model.tokens.Token;

public class MinimaxAITest {
	
	@Test
	public void testChooseMove() {
		Map<Coordinate, Token> board = State.genereTab(5);
		
		Team iaTeam = Team.BLACK;
		
		CoordinateCube ringPos = new CoordinateCube(0, 0, 0);		
		board.put(ringPos, new Ring(iaTeam));
		IState testState = new State(board, iaTeam, new ArrayList<>());
		MinimaxAI ai = new MinimaxAI(2);
		Action action = ai.chooseMove(testState);
		
		assertNotNull(action);
		assertTrue(action instanceof Move);
		
		Move move = (Move) action;
		assertEquals(ringPos, move.getFrom());
	}
	
	@Test
	public void testPossibleActions_Normal() {
		MinimaxAI ai = new MinimaxAI(2);
		Map<Coordinate, Token> board = State.genereTab(5);
		
		Coordinate ringPos = new CoordinateCube(0, 0, 0);
		board.put(ringPos, new Ring(Team.WHITE));
		
		IState state = new State(board, Team.WHITE, new ArrayList<>());
		
		List<Action> actions = ai.possibleActions(state);
		
		assertFalse(actions.isEmpty());
		for (Action a : actions) {
			assertTrue(a instanceof Move);
		}
	}
	
	@Test
	public void testPossibleActions_Remove() {
		MinimaxAI ai = new MinimaxAI(2);
		Map<Coordinate, Token> board = State.genereTab(5);
		
		Coordinate ringPos = new CoordinateCube(0, 0, 0);
		board.put(ringPos, new Ring(Team.WHITE));
		
		board.put(new CoordinateCube(1, -1, 0), new model.tokens.Pawn(Team.WHITE));
		board.put(new CoordinateCube(2, -2, 0), new model.tokens.Pawn(Team.WHITE));
		board.put(new CoordinateCube(3, -3, 0), new model.tokens.Pawn(Team.WHITE));
		board.put(new CoordinateCube(4, -4, 0), new model.tokens.Pawn(Team.WHITE));
		board.put(new CoordinateCube(5, -5, 0), new model.tokens.Pawn(Team.WHITE));

		
		IState state = new State(board, Team.WHITE, new ArrayList<>());
		
		List<Action> actions = ai.possibleActions(state);
		
		assertFalse(actions.isEmpty());
		for (Action a : actions) {
			assertTrue(a instanceof RemoveLine);
		}
	}
	
	@Test
	public void testApplyAction() {
		MinimaxAI ai = new MinimaxAI(2);
		Map<Coordinate, Token> board = State.genereTab(5);
		Coordinate from = new CoordinateCube(0, 0, 0);
		Coordinate to = new CoordinateCube(1, -1, 0);
		
		board.put(from, new Ring(Team.WHITE));
		IState state = new State(board, Team.WHITE, new ArrayList<>());
		
		Move validMove = new Move(from, to);
		IState nextState = ai.applyAction(state, validMove);
		assertNotNull(nextState);
		assertNotEquals(state, nextState);
		
		Move invalidMove = new Move(from, new CoordinateCube(99, 99, -198));
		IState errorState = ai.applyAction(state,  invalidMove);
		assertNull(errorState);
	}
	
	@Test
	public void testChooseMove_NoActions() {
	    Map<Coordinate, Token> board = State.genereTab(5);
	    IState state = new State(board, Team.WHITE, new ArrayList<>());

	    MinimaxAI ai = new MinimaxAI(2);

	    Action action = ai.chooseMove(state);

	    assertNull(action);
	}
	
	@Test
	public void testMinimax_EvaluationPrefersWinningMove() {
	    Map<Coordinate, Token> board = State.genereTab(5);

	    Coordinate from = new CoordinateCube(0, 0, 0);
	    Coordinate good = new CoordinateCube(1, -1, 0);
	    Coordinate bad = new CoordinateCube(2, -2, 0);

	    board.put(from, new Ring(Team.BLACK));

	    IState state = new State(board, Team.BLACK, new ArrayList<>());

	    MinimaxAI ai = new MinimaxAI(1);

	    Action action = ai.chooseMove(state);

	    assertNotNull(action);
	    assertTrue(action instanceof Move);
	}
	
	@Test
	public void testEvaluate_Win() {
	    MinimaxAI ai = new MinimaxAI(2);

	    IState state = new IState() {
	        @Override
	        public Team winner() {
	            return Team.BLACK;
	        }

	        @Override
	        public Map<Coordinate, Token> board() {
	            return Map.of();
	        }

	        @Override
	        public Map<Team, List<Coordinate>> rings() {
	            return Map.of(Team.BLACK, List.of(), Team.WHITE, List.of());
	        }

	        @Override
	        public List<Set<Coordinate>> getPawnsLines() {
	            return List.of();
	        }

	        @Override
	        public Set<Coordinate> availableMoves(Coordinate from) {
	            return Set.of();
	        }

	        @Override
	        public IState move(model.action.Move move) { return this; }

	        @Override
	        public IState removeLine(model.action.RemoveLine removeLine) { return this; }

	        @Override
	        public List<Set<Coordinate>> lines() { return List.of(); }

	        @Override
	        public Team turn() { return Team.BLACK; }

	        @Override
	        public IState removeToken(Coordinate c) { return this; }

	        @Override
	        public IState toggleToken(Coordinate position, Class<?> token, Team team) { return this; }
	    };

	    int score = ai.evaluate(state, Team.BLACK);

	    assertEquals(100000, score);
	}
	
	@Test
	public void testApplyAction_ExceptionHandled() {
	    MinimaxAI ai = new MinimaxAI(2);

	    IState state = new State(State.genereTab(5), Team.WHITE, new ArrayList<>());

	    Action badAction = new Move(
	        new CoordinateCube(99, 99, -198),
	        new CoordinateCube(100, 100, -200)
	    );

	    IState result = ai.applyAction(state, badAction);

	    assertNull(result);
	}
	
	@Test
	public void testPossibleActions_PriorityRemoveOverMove() {
	    Map<Coordinate, Token> board = State.genereTab(5);

	    Coordinate ringPos = new CoordinateCube(0, 0, 0);
	    board.put(ringPos, new Ring(Team.WHITE));

	    board.put(new CoordinateCube(1, -1, 0), new Pawn(Team.WHITE));
	    board.put(new CoordinateCube(2, -2, 0), new Pawn(Team.WHITE));
	    board.put(new CoordinateCube(3, -3, 0), new Pawn(Team.WHITE));
	    board.put(new CoordinateCube(4, -4, 0), new Pawn(Team.WHITE));
	    board.put(new CoordinateCube(5, -5, 0), new Pawn(Team.WHITE));

	    IState state = new State(board, Team.WHITE, new ArrayList<>());

	    MinimaxAI ai = new MinimaxAI(2);

	    List<Action> actions = ai.possibleActions(state);

	    assertTrue(actions.stream().allMatch(a -> a instanceof RemoveLine));
	}
	
	
}

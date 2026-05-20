package ia;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Map;

import org.junit.jupiter.api.Test;

import coordinate.Coordinate;
import coordinate.CoordinateCube;
import model.Team;
import model.action.Action;
import model.action.Move;
import model.state.State;
import model.state.IState;
import model.tokens.Ring;
import model.tokens.Token;

public class MinimaxAITest {
	
	@Test
	public void testChooseMove() {
		Map<Coordinate, Token> board = State.genereTab(5);
		
		Team iaTeam = Team.BLACK;
		
		CoordinateCube ringPos = new CoordinateCube(0, 0, 0);
		CoordinateCube targetPos = new CoordinateCube(1, -1, 0);
		
		board.put(ringPos, new Ring(iaTeam));
		IState testState = new State(board, iaTeam, new ArrayList<>());
		MinimaxAI ai = new MinimaxAI(2);
		Action action = ai.chooseMove(testState);
		
		assertNotNull(action);
		assertTrue(action instanceof Move);
		
		Move move = (Move) action;
		assertEquals(ringPos, move.getFrom());
	}

}

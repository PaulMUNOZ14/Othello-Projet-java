package sae21.testStage;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import coordinate.Coordinate;
import coordinate.CoordinateCube;
import coordinate.DifferentAxisException;
import coordinate.Direction;
import coordinate.Mode;
import coordinate.Point;
import model.Team;
import model.action.Move;
import model.state.IState;
import model.state.State;
import model.tokens.Pawn;
import model.tokens.Ring;
import model.tokens.Token;

class TestStage {
	
	public static Map<Coordinate, Token> genereTab(int n){
		Map<Coordinate, Token> board = new HashMap<Coordinate, Token>();
		for (int i = -n; i <= n; i++) {
			for (int j = -n; j <= n; j++) {
				for (int k = -n; k <= n; k++) {
					if(Math.sqrt(i*i+j*j+k*k) < n) {
						board.put(new CoordinateCube(i, j, k), null);
					}
				}
			}
		}
		return board;
	}
	
	
	@Test
	void testMove() throws DifferentAxisException {
		Map<Coordinate, Token> board =  genereTab(10);
		board.replace(new CoordinateCube(0,0,0), new Ring(Team.BLACK));
		State state = new State(board, Team.BLACK, null);
		IState sta = state.move(new Move(new CoordinateCube(0, 0, 0), new CoordinateCube(2, 0, 0)));
		assertTrue(sta.board().get(new CoordinateCube(1, 0, 0)) instanceof Pawn);
	}

}

package model.factory;

import coordinate.Coordinate;
import coordinate.CoordinateCube;
import model.state.IState;
import model.state.State;
import model.tokens.Pawn;
import model.tokens.Ring;
import model.tokens.Token;
import model.Team;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Fabrique concrète pour la création de plateaux utilisant les coordonnées cubiques.
 */
public class FactoryCube implements IFactory {

    @Override
    public IState emptyState() {
        return new State(buildBaseBoard(), Team.WHITE, List.of());
    }

    public Map<Coordinate, Token> buildBaseBoard() {
    	int n = 10;
    	Map<Coordinate, Token> board = new HashMap<Coordinate, Token>();
		for (int i = -n; i <= n; i++) {
			for (int j = -n; j <= n; j++) {
				if(Math.sqrt(i*i+j*j+(-i-j)*(-i-j)) < n) {
					board.put(new CoordinateCube(i, j, -i-j), null);
				}
			}
		}
		return board;
    }

    @Override
    public IState stateForWhiteLineTest() {
    	IState s = new State(buildBaseBoard(), Team.WHITE, List.of());
        
        s = s.toggleToken(new CoordinateCube(0, 0, 0), Pawn.class, Team.WHITE);
        s = s.toggleToken(new CoordinateCube(1, -1, 0), Pawn.class, Team.WHITE);
        s = s.toggleToken(new CoordinateCube(2, -2, 0), Pawn.class, Team.WHITE);
        s = s.toggleToken(new CoordinateCube(3, -3, 0), Pawn.class, Team.WHITE);
        s = s.toggleToken(new CoordinateCube(4, -4, 0), Pawn.class, Team.WHITE);
        
        return s;
    }

    @Override
    public IState stateForBlackLineTest() {
    	IState s = new State(buildBaseBoard(), Team.BLACK, List.of());
        
        s = s.toggleToken(new CoordinateCube(0, 0, 0), Pawn.class, Team.BLACK);
        s = s.toggleToken(new CoordinateCube(1, -1, 0), Pawn.class, Team.BLACK);
        s = s.toggleToken(new CoordinateCube(2, -2, 0), Pawn.class, Team.BLACK);
        s = s.toggleToken(new CoordinateCube(3, -3, 0), Pawn.class, Team.BLACK);
        s = s.toggleToken(new CoordinateCube(4, -4, 0), Pawn.class, Team.BLACK);
        
        return s;
    }

    @Override
    public IState testState() {
    	IState s = new State(buildBaseBoard(), Team.WHITE, List.of());
        
        s = s.toggleToken(new CoordinateCube(0, 0, 0), Ring.class, Team.WHITE);
        s = s.toggleToken(new CoordinateCube(1, 0, -1), Pawn.class, Team.BLACK);
        
        return s;
    }

    @Override
    public IState doubleLineStateTest() {
    	IState s = new State(buildBaseBoard(), Team.WHITE, List.of());
        
        s = s.toggleToken(new CoordinateCube(0, 0, 0), Pawn.class, Team.WHITE);
        s = s.toggleToken(new CoordinateCube(1, -1, 0), Pawn.class, Team.WHITE);
        s = s.toggleToken(new CoordinateCube(2, -2, 0), Pawn.class, Team.WHITE);
        s = s.toggleToken(new CoordinateCube(3, -3, 0), Pawn.class, Team.WHITE);
        s = s.toggleToken(new CoordinateCube(4, -4, 0), Pawn.class, Team.WHITE);
        
        s = s.toggleToken(new CoordinateCube(0, 1, -1), Pawn.class, Team.WHITE);
        s = s.toggleToken(new CoordinateCube(1, 1, -2), Pawn.class, Team.WHITE);
        s = s.toggleToken(new CoordinateCube(2, 1, -3), Pawn.class, Team.WHITE);
        s = s.toggleToken(new CoordinateCube(3, 1, -4), Pawn.class, Team.WHITE);
        s = s.toggleToken(new CoordinateCube(4, 1, -5), Pawn.class, Team.WHITE);
        
        return s;
    }
}
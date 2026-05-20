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

	/**
	 * Fonction qui génère un state vide
	 */
    @Override
    public IState emptyState() {
        return new State(buildBaseBoard(), Team.WHITE, List.of());
    }

    /**
     * Fonction qui génère un board
     * @return le board généré
     */
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

    /**
     * Fonction qui renvoie un state pour un test de ligne blanche
     */
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

    /**
     * Fonction qui renvoie un state pour un test de ligne noir
     */
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

    /**
     * Fonction qui sert à faire un test de state
     */
    @Override
    public IState testState() {
    	IState s = new State(buildBaseBoard(), Team.WHITE, List.of());
        
        s = s.toggleToken(new CoordinateCube(0, 0, 0), Ring.class, Team.WHITE);
        s = s.toggleToken(new CoordinateCube(1, 0, -1), Pawn.class, Team.BLACK);
        
        return s;
    }

    /**
     * Fonction qui sert à faire un test de state
     */
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
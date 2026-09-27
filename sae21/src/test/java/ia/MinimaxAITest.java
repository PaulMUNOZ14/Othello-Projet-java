package ia;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import coordinate.Coordinate;
import coordinate.CoordinateCube;
import model.Team;
import model.action.Action;
import model.action.Move;
import model.action.RemoveLine;
import model.state.State;
import model.state.IState;
import model.tokens.Ring;
import model.tokens.Token;
import model.tokens.Pawn;
// Assurez-vous d'importer le bon package pour votre FactoryCube
import model.factory.FactoryCube; 

public class MinimaxAITest {
    
    /**
     * Méthode utilitaire pour générer un board de base vide 
     * en utilisant l'instance de FactoryCube.
     */
    private Map<Coordinate, Token> createEmptyBoard() {
        FactoryCube factory = new FactoryCube();
        return factory.buildBaseBoard();
    }
    
    @Test
    public void testChooseMove() {
        Map<Coordinate, Token> board = createEmptyBoard();
        
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
        Map<Coordinate, Token> board = createEmptyBoard();
        
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
        Map<Coordinate, Token> board = createEmptyBoard();
        
        Coordinate ringPos = new CoordinateCube(0, 0, 0);
        board.put(ringPos, new Ring(Team.WHITE));
        
        board.put(new CoordinateCube(1, -1, 0), new Pawn(Team.WHITE));
        board.put(new CoordinateCube(2, -2, 0), new Pawn(Team.WHITE));
        board.put(new CoordinateCube(3, -3, 0), new Pawn(Team.WHITE));
        board.put(new CoordinateCube(4, -4, 0), new Pawn(Team.WHITE));
        board.put(new CoordinateCube(5, -5, 0), new Pawn(Team.WHITE));

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
        Map<Coordinate, Token> board = createEmptyBoard();
        Coordinate from = new CoordinateCube(0, 0, 0);
        Coordinate to = new CoordinateCube(1, -1, 0);
        
        board.put(from, new Ring(Team.WHITE));
        IState state = new State(board, Team.WHITE, new ArrayList<>());
        
        Move validMove = new Move(from, to);
        IState nextState = ai.applyAction(state, validMove);
        assertNotNull(nextState);
        assertNotEquals(state, nextState);
        
        Move invalidMove = new Move(from, new CoordinateCube(99, 99, -198));
        IState errorState = ai.applyAction(state, invalidMove);
        assertNull(errorState);
    }
}
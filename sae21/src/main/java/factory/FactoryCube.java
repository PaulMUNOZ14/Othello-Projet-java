package factory;

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
        return new State(new HashMap<>(), Team.WHITE, List.of());
    }

    private Map<Coordinate, Token> buildBaseBoard() {
        return new HashMap<>();
    }

    @Override
    public IState stateForWhiteLineTest() {
        State s = new State(buildBaseBoard(), Team.WHITE, List.of());
        
        s = s.toggleToken(new CoordinateCube(0, 0, 0), Pawn.class, Team.WHITE);
        s = s.toggleToken(new CoordinateCube(1, -1, 0), Pawn.class, Team.WHITE);
        s = s.toggleToken(new CoordinateCube(2, -2, 0), Pawn.class, Team.WHITE);
        s = s.toggleToken(new CoordinateCube(3, -3, 0), Pawn.class, Team.WHITE);
        s = s.toggleToken(new CoordinateCube(4, -4, 0), Pawn.class, Team.WHITE);
        
        return s;
    }

    @Override
    public IState stateForBlackLineTest() {
        State s = new State(buildBaseBoard(), Team.BLACK, List.of());
        
        s = s.toggleToken(new CoordinateCube(0, 0, 0), Pawn.class, Team.BLACK);
        s = s.toggleToken(new CoordinateCube(1, -1, 0), Pawn.class, Team.BLACK);
        s = s.toggleToken(new CoordinateCube(2, -2, 0), Pawn.class, Team.BLACK);
        s = s.toggleToken(new CoordinateCube(3, -3, 0), Pawn.class, Team.BLACK);
        s = s.toggleToken(new CoordinateCube(4, -4, 0), Pawn.class, Team.BLACK);
        
        return s;
    }

    @Override
    public IState testState() {
        State s = new State(buildBaseBoard(), Team.WHITE, List.of());
        
        s = s.toggleToken(new CoordinateCube(0, 0, 0), Ring.class, Team.WHITE);
        s = s.toggleToken(new CoordinateCube(1, 0, -1), Pawn.class, Team.BLACK);
        
        return s;
    }

    @Override
    public IState doubleLineStateTest() {
        State s = new State(buildBaseBoard(), Team.WHITE, List.of());
        
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
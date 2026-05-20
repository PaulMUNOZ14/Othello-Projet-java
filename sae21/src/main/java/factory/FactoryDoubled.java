package factory;

import coordinate.Coordinate;
import coordinate.CoordinateDoubled;
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
 * Fabrique concrète pour la création de plateaux utilisant les coordonnées doublées.
 */
public class FactoryDoubled implements IFactory {

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
        
        s = s.toggleToken(new CoordinateDoubled(5, 1), Pawn.class, Team.WHITE);
        s = s.toggleToken(new CoordinateDoubled(5, 3), Pawn.class, Team.WHITE);
        s = s.toggleToken(new CoordinateDoubled(5, 5), Pawn.class, Team.WHITE);
        s = s.toggleToken(new CoordinateDoubled(5, 7), Pawn.class, Team.WHITE);
        s = s.toggleToken(new CoordinateDoubled(5, 9), Pawn.class, Team.WHITE);
        
        return s;
    }

    @Override
    public IState stateForBlackLineTest() {
        State s = new State(buildBaseBoard(), Team.BLACK, List.of());
        
        s = s.toggleToken(new CoordinateDoubled(5, 1), Pawn.class, Team.BLACK);
        s = s.toggleToken(new CoordinateDoubled(5, 3), Pawn.class, Team.BLACK);
        s = s.toggleToken(new CoordinateDoubled(5, 5), Pawn.class, Team.BLACK);
        s = s.toggleToken(new CoordinateDoubled(5, 7), Pawn.class, Team.BLACK);
        s = s.toggleToken(new CoordinateDoubled(5, 9), Pawn.class, Team.BLACK);
        
        return s;
    }

    @Override
    public IState testState() {
        State s = new State(buildBaseBoard(), Team.WHITE, List.of());
        
        s = s.toggleToken(new CoordinateDoubled(5, 9), Ring.class, Team.WHITE);
        s = s.toggleToken(new CoordinateDoubled(6, 10), Pawn.class, Team.BLACK);
        
        return s;
    }

    @Override
    public IState doubleLineStateTest() {
        State s = new State(buildBaseBoard(), Team.WHITE, List.of());
        
        s = s.toggleToken(new CoordinateDoubled(4, 2), Pawn.class, Team.WHITE);
        s = s.toggleToken(new CoordinateDoubled(4, 4), Pawn.class, Team.WHITE);
        s = s.toggleToken(new CoordinateDoubled(4, 6), Pawn.class, Team.WHITE);
        s = s.toggleToken(new CoordinateDoubled(4, 8), Pawn.class, Team.WHITE);
        s = s.toggleToken(new CoordinateDoubled(4, 10), Pawn.class, Team.WHITE);
        
        s = s.toggleToken(new CoordinateDoubled(6, 2), Pawn.class, Team.WHITE);
        s = s.toggleToken(new CoordinateDoubled(6, 4), Pawn.class, Team.WHITE);
        s = s.toggleToken(new CoordinateDoubled(6, 6), Pawn.class, Team.WHITE);
        s = s.toggleToken(new CoordinateDoubled(6, 8), Pawn.class, Team.WHITE);
        s = s.toggleToken(new CoordinateDoubled(6, 10), Pawn.class, Team.WHITE);
        
        return s;
    }
}
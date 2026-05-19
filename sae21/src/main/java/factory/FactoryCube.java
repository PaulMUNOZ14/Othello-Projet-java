package factory;

import hexagonalCoordinate.Coordinate;
import hexagonalCoordinate.CoordinateDoubled;
import model.state.IState;
import model.state.State;
import model.tokens.Pawn;
import model.tokens.Ring;
import model.tokens.Token;
import model.action.Team;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FactoryDoubled implements IFactory {

    @Override
    public IState emptyState() {
        return new State(new HashMap<>(), Team.WHITE, List.of());
    }

    private Map<Coordinate, Token> buildBaseBoard() {
        Map<Coordinate, Token> board = new HashMap<>();
        // TODO coordonnées communes on utilisera new CoordinateCube(q, r, s)ici
        return board;
    }

    @Override
    public IState stateForWhiteLineTest() {
        Map<Coordinate, Token> board = buildBaseBoard();
        // TODO
        return new State(board, Team.WHITE, List.of());
    }

    @Override
    public IState stateForBlackLineTest() {
        Map<Coordinate, Token> board = buildBaseBoard();
        // TODO
        return new State(board, Team.BLACK, List.of());
    }

    @Override
    public IState testState() {
        Map<Coordinate, Token> board = buildBaseBoard();
        // TODO
        return new State(board, Team.WHITE, List.of());
    }

    @Override
    public IState doubleLineStateTest() {
        Map<Coordinate, Token> board = buildBaseBoard();
        // TODO
        return new State(board, Team.WHITE, List.of());
    }
}
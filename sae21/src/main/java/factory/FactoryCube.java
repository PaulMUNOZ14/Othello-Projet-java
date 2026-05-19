package factory;

import hexagonalCoordinate.Coordinate;
import hexagonalCoordinate.CoordinateCube;
import model.state.IState;
import model.state.State;
import model.tokens.Pawn;
import model.tokens.Ring;
import model.tokens.Token;
import model.action.Team;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Fabrique concrète pour la création de plateaux utilisant les coordonnées cubiques (CoordinateCube).
 */
public class FactoryCube implements IFactory {

    @Override
    public IState emptyState() {
        return new State(new HashMap<>(), Team.WHITE, List.of());
    }

    /**
     * Initialise la base du plateau avec les éléments communs à tous les tests.
     * @return Une Map associant chaque coordonnée à son jeton (Token).
     */
    private Map<Coordinate, Token> buildBaseBoard() {
        Map<Coordinate, Token> board = new HashMap<>();
        // TODO: coordonnées communes
        return board;
    }

    @Override
    public IState stateForWhiteLineTest() {
        Map<Coordinate, Token> board = buildBaseBoard();
        // TODO: ligne blanche
        return new State(board, Team.WHITE, List.of());
    }

    @Override
    public IState stateForBlackLineTest() {
        Map<Coordinate, Token> board = buildBaseBoard();
        // TODO: ligne noire
        return new State(board, Team.BLACK, List.of());
    }

    @Override
    public IState testState() {
        Map<Coordinate, Token> board = buildBaseBoard();
        // TODO: état de test global
        return new State(board, Team.WHITE, List.of());
    }

    @Override
    public IState doubleLineStateTest() {
        Map<Coordinate, Token> board = buildBaseBoard();
        // TODO: double ligne
        return new State(board, Team.WHITE, List.of());
    }
}
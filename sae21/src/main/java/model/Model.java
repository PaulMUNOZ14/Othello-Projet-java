package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import coordinate.Coordinate;
import coordinate.DifferentAxisException;

import model.action.Move;
import model.action.RemoveLine;

import model.state.IState;
import model.tokens.Pawn;
import model.tokens.Token;

public class Model {

    private IState currentState;

    public Model(IState currentState) {
        this.currentState = currentState;
    }

    public void setCurrentState(IState currentState) {
        this.currentState = currentState;
    }

    public Set<Coordinate> movesFrom(Coordinate from) {
        return currentState.availableMoves(from);
    }

    public void moveRing(Coordinate from, Coordinate to) {
        try {
            currentState = currentState.move(new Move(from, to));
        } catch (DifferentAxisException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Set<Coordinate>> getPawnLines() {
        return currentState.getPawnsLines();
    }

    public void removeLine(Set<Coordinate> line, Coordinate ring) {
        if (line == null || ring == null) return;
        currentState = currentState.removeLine(new RemoveLine(line, ring));
    }

    public Map<Coordinate, Token> getBoard() {
        Map<Coordinate, Token> board = currentState.board();
        return board == null ? Map.of() : board;
    }

    public Token getTokenAt(Coordinate c) {
        if (c == null) return null;
        Map<Coordinate, Token> board = currentState.board();
        if (board == null) return null;
        return board.get(c);
    }

    public boolean isInField(Coordinate c) {
        if (c == null) return false;
        Map<Coordinate, Token> board = currentState.board();
        if (board == null) return false;
        return board.containsKey(c);
    }

    public List<Coordinate> getRings(Team team) {
        Map<Team, List<Coordinate>> rings = currentState.rings();
        if (rings == null) return List.of();
        return rings.getOrDefault(team, List.of());
    }

    public List<Coordinate> getPawn(Team team) {

        List<Coordinate> pawns = new ArrayList<>();

        Map<Coordinate, Token> board = currentState.board();

        if (board == null) return pawns;

        for (Coordinate c : board.keySet()) {

            Token t = board.get(c);

            if (t instanceof Pawn &&
                t.getTeam() == team) {

                pawns.add(c);
            }
        }

        return pawns;
    }

    public Team getTurn() {
        return currentState.turn();
    }

    public IState getCurrentState() {
        return currentState;
    }
}
package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import model.Model;

public class ModelTest {

    class FakeState implements IState {

    }

    @Test
    void testConstructor() {

        IState state = new FakeState();

        Model model = new Model(state);

        assertEquals(state, model.getCurrentState());
    }

    @Test
    void testSetCurrentState() {

        IState state1 = new FakeState();
        IState state2 = new FakeState();

        Model model = new Model(state1);

        model.setCurrentState(state2);

        assertEquals(state2, model.getCurrentState());
    }

    @Test
    void testMovesFromReturnsNull() {

        Model model = new Model(new FakeState());

        Coordinate c = null;

        Set<Coordinate> result = model.movesFrom(c);

        assertNull(result);
    }

    @Test
    void testGetPawnLinesReturnsNull() {

        Model model = new Model(new FakeState());

        List<Set<Coordinate>> result = model.getPawnLines();

        assertNull(result);
    }

    @Test
    void testGetBoardReturnsNull() {

        Model model = new Model(new FakeState());

        Map<Coordinate, Token> board = model.getBoard();

        assertNull(board);
    }

    @Test
    void testGetTokenAtReturnsNull() {

        Model model = new Model(new FakeState());

        Token token = model.getTokenAt(null);

        assertNull(token);
    }

    @Test
    void testIsInFieldReturnsFalse() {

        Model model = new Model(new FakeState());

        boolean result = model.isInField(null);

        assertFalse(result);
    }

    @Test
    void testGetPawnReturnsNull() {

        Model model = new Model(new FakeState());

        List<Coordinate> pawns = model.getPawn(null);

        assertNull(pawns);
    }

    @Test
    void testGetTurnReturnsNull() {

        Model model = new Model(new FakeState());

        Team team = model.getTurn();

        assertNull(team);
    }

    @Test
    void testMoveRingDoesNotThrowException() {

        Model model = new Model(new FakeState());

        assertDoesNotThrow(() -> {
            model.moveRing(null, null);
        });
    }

    @Test
    void testRemoveLineDoesNotThrowException() {

        Model model = new Model(new FakeState());

        assertDoesNotThrow(() -> {
            model.removeLine(null, null);
        });
    }
}
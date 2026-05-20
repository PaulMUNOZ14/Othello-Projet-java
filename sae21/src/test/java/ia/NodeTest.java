package ia;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.state.IState;
import model.action.Action;
import model.Team;
import model.state.State;
import model.tokens.Ring;
import coordinate.CoordinateCube;

import java.util.ArrayList;
import java.util.Map;

public class NodeTest {

    @Test
    public void testConstructorRoot() {
        IState state = new FakeState();
        Node node = new Node(state);

        assertEquals(state, node.getState());
        assertNull(node.getParent());
        assertNull(node.getAction());
        assertTrue(node.isRoot());
    }

    @Test
    public void testConstructorWithParentAndAction() {
        IState state = new FakeState();
        Node parent = new Node(state);

        Action action = new model.action.Move(
                new CoordinateCube(0,0,0),
                new CoordinateCube(1,0,-1)
        );

        Node child = new Node(state, parent, action);

        assertEquals(state, child.getState());
        assertEquals(parent, child.getParent());
        assertEquals(action, child.getAction());
        assertFalse(child.isRoot());
    }

    static class FakeState implements IState {
        @Override public Map board() { return Map.of(); }
        @Override public java.util.Set availableMoves(coordinate.Coordinate c) { return java.util.Set.of(); }
        @Override public IState move(model.action.Move m) { return this; }
        @Override public IState removeLine(model.action.RemoveLine r) { return this; }
        @Override public Map rings() { return Map.of(); }
        @Override public java.util.List lines() { return java.util.List.of(); }
        @Override public Team turn() { return Team.WHITE; }
        @Override public java.util.List getPawnsLines() { return java.util.List.of(); }
        @Override public Team winner() { return null; }
        @Override public IState removeToken(coordinate.Coordinate c) { return this; }
        @Override public IState toggleToken(coordinate.Coordinate p, Class<?> t, Team team) { return this; }
    }
}
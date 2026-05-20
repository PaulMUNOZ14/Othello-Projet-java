package model.action;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Set;
import coordinate.Coordinate;
import coordinate.CoordinateDoubled;

public class RemoveLineTest {

    @Test
    public void testRemoveLineGettersAndSetters() {
        Set<Coordinate> line = new HashSet<>();
        Coordinate ring = null;

        RemoveLine removeAction = new RemoveLine(line, ring);

        assertEquals(line, removeAction.getLine());
        assertEquals(ring, removeAction.getRing());

        Set<Coordinate> newLine = new HashSet<>();
        Coordinate newRing = null;
        
        removeAction.setLine(newLine);
        removeAction.setRing(newRing);
        
        assertEquals(newLine, removeAction.getLine());
        assertEquals(newRing, removeAction.getRing());
    }
    
    @Test
    public void testRemoveLineGetters() {
        Set<Coordinate> line = new HashSet<>();
        Coordinate c1 = new CoordinateDoubled(1, 2);
        Coordinate c2 = new CoordinateDoubled(3, 4);
        Coordinate ring = new CoordinateDoubled(5, 6);

        line.add(c1);
        line.add(c2);

        RemoveLine removeAction = new RemoveLine(line, ring);

        assertEquals(line, removeAction.getLine());
        assertEquals(ring, removeAction.getRing());
    }
    
    @Test
    public void testSetLine() {
        Set<Coordinate> line = new HashSet<>();
        line.add(new CoordinateDoubled(1, 1));

        Set<Coordinate> newLine = new HashSet<>();
        newLine.add(new CoordinateDoubled(2, 2));

        RemoveLine removeAction = new RemoveLine(line, new CoordinateDoubled(3, 3));

        removeAction.setLine(newLine);

        assertEquals(newLine, removeAction.getLine());
    }
    
    @Test
    public void testSetRing() {
        RemoveLine removeAction = new RemoveLine(
            new HashSet<>(),
            new CoordinateDoubled(1, 1)
        );

        Coordinate newRing = new CoordinateDoubled(9, 9);

        removeAction.setRing(newRing);

        assertEquals(newRing, removeAction.getRing());
    }
    
    @Test
    public void testRemoveLineContentIsPreserved() {
        Set<Coordinate> line = new HashSet<>();
        Coordinate c1 = new CoordinateDoubled(1, 1);
        Coordinate c2 = new CoordinateDoubled(2, 2);

        line.add(c1);
        line.add(c2);

        Coordinate ring = new CoordinateDoubled(3, 3);

        RemoveLine action = new RemoveLine(line, ring);

        assertTrue(action.getLine().contains(c1));
        assertTrue(action.getLine().contains(c2));
        assertEquals(2, action.getLine().size());
    }
    
    
}
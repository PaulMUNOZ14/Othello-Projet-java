package model.action;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Set;
import hexagonalCoordinate.Coordinate;

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
}
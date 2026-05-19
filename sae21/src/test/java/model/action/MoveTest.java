package model.action;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import coordinate.Coordinate;

public class MoveTest {

    @Test
    public void testMoveGettersAndSetters() {
        Coordinate startCoord = null; 
        Coordinate endCoord = null;

        Move move = new Move(startCoord, endCoord);

        assertEquals(startCoord, move.getFrom());
        assertEquals(endCoord, move.getTo());

        Coordinate newEndCoord = null;
        move.setTo(newEndCoord);
        assertEquals(newEndCoord, move.getTo());
    }
}
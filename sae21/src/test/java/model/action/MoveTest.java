package model.action;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import coordinate.Coordinate;
import coordinate.CoordinateDoubled;

public class MoveTest {

	@Test
	public void testMoveGetters() {
	    Coordinate start = new CoordinateDoubled(1, 2);
	    Coordinate end = new CoordinateDoubled(3, 4);

	    Move move = new Move(start, end);

	    assertEquals(start, move.getFrom());
	    assertEquals(end, move.getTo());
	}
	
	@Test
	public void testMoveSetTo() {
	    Coordinate start = new CoordinateDoubled(1, 2);
	    Coordinate end = new CoordinateDoubled(3, 4);
	    Coordinate newEnd = new CoordinateDoubled(5, 6);

	    Move move = new Move(start, end);

	    move.setTo(newEnd);

	    assertEquals(newEnd, move.getTo());
	}
	
	@Test
	public void testMoveSetFrom() {
	    Coordinate start = new CoordinateDoubled(1, 2);
	    Coordinate newStart = new CoordinateDoubled(7, 8);
	    Coordinate end = new CoordinateDoubled(3, 4);

	    Move move = new Move(start, end);

	    move.setFrom(newStart);

	    assertEquals(newStart, move.getFrom());
	}
	
	@Test
	public void testMoveStateChange() {
	    Move move = new Move(
	        new CoordinateDoubled(1, 1),
	        new CoordinateDoubled(2, 2)
	    );

	    Move copy = new Move(
	        new CoordinateDoubled(1, 1),
	        new CoordinateDoubled(2, 2)
	    );

	    assertNotSame(move, copy);
	    assertEquals(move.getFrom(), copy.getFrom());
	    assertEquals(move.getTo(), copy.getTo());
	}
}
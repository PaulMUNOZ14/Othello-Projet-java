package testCoordinate;

import static org.junit.jupiter.api.Assertions.*;
import java.security.InvalidParameterException;
import java.util.List;
import org.junit.jupiter.api.Test;

import coordinate.Coordinate;
import coordinate.CoordinateDoubled;
import coordinate.Mode;
import coordinate.*;

public class CoordinateDoubledTest {

	@Test
	void testTo2DCoordinate() {
		CoordinateDoubled coord = new CoordinateDoubled(9, 5);
		Point p = coord.to2DCoordinate(Mode.POINTY);
		assertEquals(5, p.x(), "La colonne x devrait être 5");
		assertEquals(9, p.y(), "La ligne y devrait être 9");
	}
	
	@Test
	void testInvalidDirectionPointy() {
	    CoordinateDoubled c = new CoordinateDoubled(9, 5);

	    assertThrows(InvalidParameterException.class,
	            () -> c.N(Mode.POINTY));
	}
	
	@Test
	void testValidDirectionPointy() {
		CoordinateDoubled center = new CoordinateDoubled(9, 5);
		CoordinateDoubled e = (CoordinateDoubled) center.E(Mode.POINTY);
		
		Point p = e.to2DCoordinate(Mode.POINTY);
		assertEquals(7, p.x());
		assertEquals(9, p.y());
	}
	
	@Test
	void testDirectionPointyO() {
	    CoordinateDoubled c = new CoordinateDoubled(9, 5);
	    CoordinateDoubled res = (CoordinateDoubled) c.O(Mode.POINTY);

	    assertEquals(3, res.to2DCoordinate(Mode.POINTY).x());
	    assertEquals(9, res.to2DCoordinate(Mode.POINTY).y());
	}

	@Test
	void testDirectionPointyNE() {
	    CoordinateDoubled c = new CoordinateDoubled(9, 5);
	    CoordinateDoubled res = (CoordinateDoubled) c.NE(Mode.POINTY);

	    assertEquals(6, res.to2DCoordinate(Mode.POINTY).x());
	    assertEquals(8, res.to2DCoordinate(Mode.POINTY).y());
	}

	@Test
	void testDirectionPointyNO() {
	    CoordinateDoubled c = new CoordinateDoubled(9, 5);
	    CoordinateDoubled res = (CoordinateDoubled) c.NO(Mode.POINTY);

	    assertEquals(4, res.to2DCoordinate(Mode.POINTY).x());
	    assertEquals(8, res.to2DCoordinate(Mode.POINTY).y());
	}

	@Test
	void testDirectionPointySE() {
	    CoordinateDoubled c = new CoordinateDoubled(9, 5);
	    CoordinateDoubled res = (CoordinateDoubled) c.SE(Mode.POINTY);

	    assertEquals(6, res.to2DCoordinate(Mode.POINTY).x());
	    assertEquals(10, res.to2DCoordinate(Mode.POINTY).y());
	}

	@Test
	void testDirectionPointySO() {
	    CoordinateDoubled c = new CoordinateDoubled(9, 5);
	    CoordinateDoubled res = (CoordinateDoubled) c.SO(Mode.POINTY);

	    assertEquals(4, res.to2DCoordinate(Mode.POINTY).x());
	    assertEquals(10, res.to2DCoordinate(Mode.POINTY).y());
	}
	
	@Test
	void testInvalidDirectionFlat() {
		CoordinateDoubled center = new CoordinateDoubled(9, 5);
		
		assertThrows(InvalidParameterException.class, () -> center.E(Mode.FLAT));
	}
	
	@Test
	void testDirectionFlatN() {
	    CoordinateDoubled c = new CoordinateDoubled(9, 5);
	    CoordinateDoubled res = (CoordinateDoubled) c.N(Mode.FLAT);

	    assertEquals(5, res.to2DCoordinate(Mode.FLAT).x());
	    assertEquals(7, res.to2DCoordinate(Mode.FLAT).y());
	}

	@Test
	void testDirectionFlatS() {
	    CoordinateDoubled c = new CoordinateDoubled(9, 5);
	    CoordinateDoubled res = (CoordinateDoubled) c.S(Mode.FLAT);

	    assertEquals(5, res.to2DCoordinate(Mode.FLAT).x());
	    assertEquals(11, res.to2DCoordinate(Mode.FLAT).y());
	}
	
	@Test
	void testBetwweenValidAxisHorizontal() throws DifferentAxisException{
		CoordinateDoubled start = new CoordinateDoubled(9, 5);
		CoordinateDoubled end = new CoordinateDoubled(9, 11);
		
		List<Coordinate> path = start.between(Mode.POINTY, end);
		assertEquals(2, path.size());
		
		CoordinateDoubled firstStep = (CoordinateDoubled) path.get(0);
		assertEquals(7, firstStep.to2DCoordinate(Mode.POINTY).x());
		assertEquals(9, firstStep.to2DCoordinate(Mode.POINTY).y());
	}
	
	@Test
	void testBetweenInvalidAxis() {
		CoordinateDoubled start = new CoordinateDoubled(9, 5);
		CoordinateDoubled end = new CoordinateDoubled(8, 8);
		
		assertThrows(DifferentAxisException.class, () -> start.between(Mode.POINTY, end));
	}
	
	@Test
	void testBetweenDiagonal() throws DifferentAxisException {
	    CoordinateDoubled start = new CoordinateDoubled(9, 5);
	    CoordinateDoubled end = new CoordinateDoubled(7, 7);

	    List<Coordinate> path = start.between(Mode.POINTY, end);

	    assertEquals(1, path.size());

	    CoordinateDoubled middle = (CoordinateDoubled) path.get(0);

	    assertEquals(8, middle.to2DCoordinate(Mode.POINTY).y());
	    assertEquals(6, middle.to2DCoordinate(Mode.POINTY).x());
	}
	
	@Test
	void testBetweenAdjacentReturnsEmpty() throws DifferentAxisException {
	    CoordinateDoubled start = new CoordinateDoubled(9, 5);
	    CoordinateDoubled end = new CoordinateDoubled(9, 7);

	    List<Coordinate> path = start.between(Mode.POINTY, end);

	    assertTrue(path.isEmpty());
	}
	
	@Test
	void testGetNeighborsCount() {
		CoordinateDoubled center = new CoordinateDoubled(9, 5);
		
		List<Coordinate> neighbors = center.getNeighbors(Mode.POINTY);
		assertEquals(6, neighbors.size());
	}
	
	@Test
	void testGetNeighborsContainsCorrectCoordinates() {
	    CoordinateDoubled c = new CoordinateDoubled(9, 5);

	    List<Coordinate> neighbors = c.getNeighbors(Mode.POINTY);

	    assertTrue(neighbors.contains(new CoordinateDoubled(9, 7)));
	    assertTrue(neighbors.contains(new CoordinateDoubled(9, 3)));
	}
	
	@Test
	void testEquals() {
	    CoordinateDoubled a = new CoordinateDoubled(9, 5);
	    CoordinateDoubled b = new CoordinateDoubled(9, 5);

	    assertEquals(a, b);
	}

	@Test
	void testHashCode() {
	    CoordinateDoubled a = new CoordinateDoubled(9, 5);
	    CoordinateDoubled b = new CoordinateDoubled(9, 5);

	    assertEquals(a.hashCode(), b.hashCode());
	}
	
	@Test
	void testToString() {
	    CoordinateDoubled c = new CoordinateDoubled(9, 5);

	    assertTrue(c.toString().contains("x=5"));
	    assertTrue(c.toString().contains("y=9"));
	}
	
}

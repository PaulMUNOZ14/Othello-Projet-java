package test.java.hexagonalCoordinate;

import static org.junit.jupiter.api.Assertions.*;
import java.security.InvalidParameterException;
import java.util.List;
import org.junit.jupiter.api.Test;
import hexagonalCoordinate.*;

public class CoordinateDoubledTest {

	@Test
	void testTo2DCoordinate() {
		CoordinateDoubled coord = new CoordinateDoubled(9, 5);
		Point p = coord.to2DCoordinate();
		assertEquals(5, p.x(), "La colonne x devrait être 5");
		assertEquals(9, p.y(), "La ligne y devrait être 9");
	}
	
	@Test
	void testValidDirectionPointy() {
		CoordinateDoubled center = new CoordinateDoubled(9, 5);
		CoordinateDoubled e = (CoordinateDoubled) center.E(Mode.POINTY);
		
		Point p = e.to2DCoordinate();
		assertEquals(7, p.x());
		assertEquals(9, p.y());
	}
	
	@Test
	void testInvalidDirectionFlat() {
		CoordinateDoubled center = new CoordinateDoubled(9, 5);
		
		assertThrows(InvalidParameterException.class, () -> center.E(Mode.FLAT));
	}
	
	@Test
	void testBetwweenValidAxisHorizontal() throws DifferentAxisException{
		CoordinateDoubled start = new CoordinateDoubled(9, 5);
		CoordinateDoubled end = new CoordinateDoubled(9, 11);
		
		List<Coordinate> path = start.between(Mode.POINTY, end);
		assertEquals(2, path.size());
		
		CoordinateDoubled firstStep = (CoordinateDoubled) path.get(0);
		assertEquals(7, firstStep.to2DCoordinate().x());
		assertEquals(9, firstStep.to2DCoordinate().y());
	}
	
	@Test
	void testBetweenInvalidAxis() {
		CoordinateDoubled start = new CoordinateDoubled(9, 5);
		CoordinateDoubled end = new CoordinateDoubled(8, 8);
		
		assertThrows(DifferentAxisException.class, () -> start.between(Mode.POINTY, end));
	}
	
	@Test
	void testGetNeighborsCount() {
		CoordinateDoubled center = new CoordinateDoubled(9, 5);
		
		List<Coordinate> neighbors = center.getNeighbors(Mode.POINTY);
		assertEquals(6, neighbors.size());
	}
	
}

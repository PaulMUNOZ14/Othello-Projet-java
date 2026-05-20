package testCoordinate;

import static org.junit.jupiter.api.Assertions.*;
import java.security.InvalidParameterException;
import java.util.List;
import org.junit.jupiter.api.Test;

import coordinate.Coordinate;
import coordinate.CoordinateCube;
import coordinate.Mode;
import coordinate.*;

public class CoordinateCubeTest {

	@Test
	void testConstructorValid() {
		// q + r + s = 0 doit fonctionner
		assertDoesNotThrow(() -> new CoordinateCube(1, -1, 0));
		assertDoesNotThrow(() -> new CoordinateCube(0, 0, 0));
	}
	
	@Test
	void testConstructorInvalid() {
		// q + r + s != 0 doit lever une exception
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> 
				new CoordinateCube(1, 1, 1));
		assertTrue(exception.getMessage().contains("q + r + s = 0"));
	}
	
	@Test
	void testTo2DCoordinate() {
		CoordinateCube center = new CoordinateCube(0, 0, 0);
		Point p = center.to2DCoordinate(Mode.POINTY);
		assertEquals(0, p.x());
		assertEquals(0, p.y());
	}
	
	@Test
	void testValidDirectionPointy() {
		CoordinateCube center = new CoordinateCube(0, 0, 0);
		CoordinateCube ne = (CoordinateCube) center.NE(Mode.POINTY);
		
		// Normalement q+1, r-1, s en POINTY
		assertEquals(1, ne.getQ());
		assertEquals(-1, ne.getR());
		assertEquals(0, ne.getS());
		
	}
	
	@Test
	void testInvalidDirectionPointy() {
		CoordinateCube center = new CoordinateCube(0, 0, 0);
		assertThrows(InvalidParameterException.class, () -> center.N(Mode.POINTY));
	}
	
	@Test
	void testValidDirectionFlat(){
		CoordinateCube center = new CoordinateCube(0, 0, 0);
		CoordinateCube n = (CoordinateCube) center.N(Mode.FLAT);
		
		// Normalement q, r-1, s+1 en FLAT
		assertEquals(0, n.getQ());
		assertEquals(-1, n.getR());
		assertEquals(1, n.getS());
	}
	
	@Test
	void testBetweenValidAxis() throws DifferentAxisException {
		CoordinateCube start = new CoordinateCube(0, 0, 0);
		CoordinateCube end = new CoordinateCube(2, 0, -2);
		
		List<Coordinate> path = start.between(Mode.POINTY, end);
		assertEquals(1, path.size());
		
		CoordinateCube firstStep = (CoordinateCube) path.get(0);
		assertEquals(1, firstStep.getQ());
		assertEquals(0, firstStep.getR());
		assertEquals(-1, firstStep.getS());
	}
	
	@Test
	void testBetweenInvalidAxis() {
		CoordinateCube start = new CoordinateCube(0, 0, 0);
		CoordinateCube end = new CoordinateCube(1, -2, 1);
		
		assertThrows(DifferentAxisException.class, () -> start.between(Mode.POINTY, end));
	}
	
}

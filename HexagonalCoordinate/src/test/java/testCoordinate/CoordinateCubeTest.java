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
	void testTo2DCoordinatePointy() {
	    CoordinateCube c = new CoordinateCube(1, -1, 0);
	    Point p = c.to2DCoordinate(Mode.POINTY);
	    assertNotNull(p);
	}

	@Test
	void testTo2DCoordinateFlat() {
	    CoordinateCube c = new CoordinateCube(1, -1, 0);
	    Point p = c.to2DCoordinate(Mode.FLAT);

	    assertNotNull(p);
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
	void testValidDirectionPointy_E() {
	    CoordinateCube c = new CoordinateCube(0, 0, 0);
	    CoordinateCube res = (CoordinateCube) c.E(Mode.POINTY);

	    assertEquals(1, res.getQ());
	    assertEquals(0, res.getR());
	    assertEquals(-1, res.getS());
	}

	@Test
	void testValidDirectionPointy_O() {
	    CoordinateCube c = new CoordinateCube(0, 0, 0);
	    CoordinateCube res = (CoordinateCube) c.O(Mode.POINTY);

	    assertEquals(-1, res.getQ());
	    assertEquals(0, res.getR());
	    assertEquals(1, res.getS());
	}

	@Test
	void testValidDirectionPointy_NE() {
	    CoordinateCube c = new CoordinateCube(0, 0, 0);
	    CoordinateCube res = (CoordinateCube) c.NE(Mode.POINTY);

	    assertEquals(1, res.getQ());
	    assertEquals(-1, res.getR());
	    assertEquals(0, res.getS());
	}

	@Test
	void testValidDirectionPointy_NO() {
	    CoordinateCube c = new CoordinateCube(0, 0, 0);
	    CoordinateCube res = (CoordinateCube) c.NO(Mode.POINTY);

	    assertEquals(0, res.getQ());
	    assertEquals(-1, res.getR());
	    assertEquals(1, res.getS());
	}

	@Test
	void testValidDirectionPointy_SE() {
	    CoordinateCube c = new CoordinateCube(0, 0, 0);
	    CoordinateCube res = (CoordinateCube) c.SE(Mode.POINTY);

	    assertEquals(0, res.getQ());
	    assertEquals(1, res.getR());
	    assertEquals(-1, res.getS());
	}

	@Test
	void testValidDirectionPointy_SO() {
	    CoordinateCube c = new CoordinateCube(0, 0, 0);
	    CoordinateCube res = (CoordinateCube) c.SO(Mode.POINTY);

	    assertEquals(-1, res.getQ());
	    assertEquals(1, res.getR());
	    assertEquals(0, res.getS());
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
	void testValidDirectionFlat_N() {
	    CoordinateCube c = new CoordinateCube(0, 0, 0);
	    CoordinateCube res = (CoordinateCube) c.N(Mode.FLAT);

	    assertEquals(0, res.getQ());
	    assertEquals(-1, res.getR());
	    assertEquals(1, res.getS());
	}

	@Test
	void testValidDirectionFlat_S() {
	    CoordinateCube c = new CoordinateCube(0, 0, 0);
	    CoordinateCube res = (CoordinateCube) c.S(Mode.FLAT);

	    assertEquals(0, res.getQ());
	    assertEquals(1, res.getR());
	    assertEquals(-1, res.getS());
	}

	@Test
	void testValidDirectionFlat_NE() {
	    CoordinateCube c = new CoordinateCube(0, 0, 0);
	    CoordinateCube res = (CoordinateCube) c.NE(Mode.FLAT);

	    assertEquals(1, res.getQ());
	    assertEquals(-1, res.getR());
	    assertEquals(0, res.getS());
	}

	@Test
	void testValidDirectionFlat_NO() {
	    CoordinateCube c = new CoordinateCube(0, 0, 0);
	    CoordinateCube res = (CoordinateCube) c.NO(Mode.FLAT);

	    assertEquals(-1, res.getQ());
	    assertEquals(0, res.getR());
	    assertEquals(1, res.getS());
	}

	@Test
	void testValidDirectionFlat_SE() {
	    CoordinateCube c = new CoordinateCube(0, 0, 0);
	    CoordinateCube res = (CoordinateCube) c.SE(Mode.FLAT);

	    assertEquals(1, res.getQ());
	    assertEquals(0, res.getR());
	    assertEquals(-1, res.getS());
	}

	@Test
	void testValidDirectionFlat_SO() {
	    CoordinateCube c = new CoordinateCube(0, 0, 0);
	    CoordinateCube res = (CoordinateCube) c.SO(Mode.FLAT);

	    assertEquals(-1, res.getQ());
	    assertEquals(1, res.getR());
	    assertEquals(0, res.getS());
	}
	
	@Test
	void testInvalidDirectionFlat() {
	    CoordinateCube c = new CoordinateCube(0, 0, 0);

	    assertThrows(IllegalArgumentException.class,
	        () -> c.N(Mode.POINTY));
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
	
	@Test
	void testBetweenDistanceGreaterThanTwo() throws DifferentAxisException {
	    CoordinateCube start = new CoordinateCube(0, 0, 0);
	    CoordinateCube end = new CoordinateCube(3, 0, -3);

	    List<Coordinate> path = start.between(Mode.POINTY, end);

	    assertEquals(2, path.size());
	}
	
	@Test
	void testBetweenAdjacentReturnsEmpty() throws DifferentAxisException {
	    CoordinateCube start = new CoordinateCube(0, 0, 0);
	    CoordinateCube end = new CoordinateCube(1, -1, 0);

	    List<Coordinate> path = start.between(Mode.POINTY, end);

	    assertTrue(path.isEmpty());
	}
	
	@Test
	void testBetweenOrderCorrect() throws DifferentAxisException {
	    CoordinateCube start = new CoordinateCube(0, 0, 0);
	    CoordinateCube end = new CoordinateCube(2, -2, 0);

	    List<Coordinate> path = start.between(Mode.POINTY, end);

	    assertEquals(new CoordinateCube(1, -1, 0), path.get(0));
	}
	
	@Test
	void testEquals() {
	    CoordinateCube a = new CoordinateCube(1, -1, 0);
	    CoordinateCube b = new CoordinateCube(1, -1, 0);

	    assertEquals(a, b);
	}

	@Test
	void testHashCode() {
	    CoordinateCube a = new CoordinateCube(1, -1, 0);
	    CoordinateCube b = new CoordinateCube(1, -1, 0);

	    assertEquals(a.hashCode(), b.hashCode());
	}
}

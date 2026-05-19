package coordinate;

import java.security.InvalidParameterException;
import java.util.*;

/**
 * 
 */
public abstract class Coordinate {

	public Coordinate() {
	}
	

    public abstract Point to2DCoordinate();
    public abstract Coordinate toDir(Mode mode, Direction direction);
    public abstract List<Coordinate> between(Mode mode, Coordinate to) throws DifferentAxisException;
    
    /**
     * @param mode 
     * @return
     */
    public List<Coordinate> getNeighbors(Mode mode) {
        List<Coordinate> neighbors = new ArrayList<>();
        Direction[] dirs;
        
        if (mode == Mode.POINTY) {
        	dirs = new Direction[] {
        			Direction.NO,
        			Direction.NE,
        			Direction.E,
        			Direction.SE,
        			Direction.SO,
        			Direction.O
        	};
        } else {
        	dirs = new Direction[] {
        			Direction.NO,
        			Direction.N,
        			Direction.NE,
        			Direction.SE,
        			Direction.S,
        			Direction.SO
        	};
        }
        
        for (Direction d : dirs) {
        	neighbors.add(this.toDir(mode, d));
        }
        return neighbors;
    }
    
    private Coordinate checkAndMove(Mode mode, Direction dir, Mode forbiddenMode, String dirName) {
    	if (mode == forbiddenMode) {
    		throw new InvalidParameterException(dirName + " n'accepte pas le mode " + forbiddenMode);
    	}
    	return toDir(mode, dir);
    }
    
    public Coordinate NO(Mode mode) {
        return toDir(mode, Direction.NO);
    }

    public Coordinate NE(Mode mode) {
    	return toDir(mode, Direction.NE);
    }

    public Coordinate E(Mode mode) {
    	return checkAndMove(mode, Direction.E, Mode.FLAT, "E");
    }

    public Coordinate O(Mode mode) {
    	return checkAndMove(mode, Direction.O, Mode.FLAT, "O");
    }

    public Coordinate N(Mode mode) {
    	return checkAndMove(mode, Direction.N, Mode.POINTY, "N");
    }

    public Coordinate S(Mode mode) {
    	return checkAndMove(mode, Direction.S, Mode.POINTY, "S");
    }

    public Coordinate SO(Mode mode) {
    	return toDir(mode, Direction.SO);
    }

    public Coordinate SE(Mode mode) {
    	return toDir(mode, Direction.SE);
    }

}
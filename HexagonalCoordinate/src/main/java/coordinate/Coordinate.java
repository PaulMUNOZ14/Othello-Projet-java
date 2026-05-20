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
     * Calcule la coordonnée voisine dans une direction donnée.
     * @param dir La direction du mouvement souhaité.
     * @return Une nouvelle instance de CoordinateCube représentant le voisin.
     * @throws IllegalArgumentException si la direction est nulle.
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
    
    /**
     * Vérifie si un déplacement est possible puis l'effectue
     * @param mode le mode du plateau
     * @param dir La direction du mouvement souhaité.
     * @param forbiddenMode le mode du plateau non accepté
     * @param dirName le nom de la direction
     * @return les coordonnées après mouvement
     * @throws InvalidParameterException si le mode n'est pas bon
     */
    private Coordinate checkAndMove(Mode mode, Direction dir, Mode forbiddenMode, String dirName) {
    	if (mode == forbiddenMode) {
    		throw new InvalidParameterException(dirName + " n'accepte pas le mode " + forbiddenMode);
    	}
    	return toDir(mode, dir);
    }
    
    // NO, NE, E, O, N, S, SO, SE permettent de faire un mouvement dans la direction donnée dans le nom de la fonction
    // N = Nord, S = Sud, O = Ouest, E = Est, NO = Nord-Ouest, NE = Nord-Est, SO = Sud-Ouest & SE = Sud-Est
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
package coordinate;

import java.util.ArrayList;
import java.util.List;

public class CoordinateCube extends Coordinate {

    private int q;
    private int r;
    private int s;

    /**
     * @param q 
     * @param r 
     * @param s
     */
    public CoordinateCube(int q, int r, int s) {
        if (q + r + s != 0) {
        	throw new IllegalArgumentException("La contrainte q + r + s = 0 n'est pas respectée.");
        }
        this.q = q;
        this.r = r;
        this.s = s;
    }

    public int getQ() {
        return q;
    }

    public int getR() {
        return r;
    }

    public int getS() {
        return s;
    }

	@Override
	public Point to2DCoordinate() {
		int x = 2 * q + r + 9;
		int y = r + 5;
		return new Point(x, y);
	}

	@Override
	public Coordinate toDir(Mode mode, Direction direction) {
		if (mode == Mode.POINTY) {
			return switch (direction) {
				case E -> new CoordinateCube(q + 1, r, s - 1);
				case O -> new CoordinateCube(q - 1, r, s + 1);
				case NE -> new CoordinateCube(q + 1, r - 1, s);
				case NO -> new CoordinateCube(q, r - 1, s + 1);
				case SE -> new CoordinateCube(q, r + 1, s - 1);
				case SO -> new CoordinateCube(q - 1, r + 1, s);
				default -> throw new IllegalArgumentException("Direction invalide pour POINTY");
			};
		} else {
			return switch (direction) {
            case N -> new CoordinateCube(q, r - 1, s + 1);
            case S -> new CoordinateCube(q, r + 1, s - 1);
            case NE -> new CoordinateCube(q + 1, r - 1, s);
            case NO -> new CoordinateCube(q - 1, r, s + 1);
            case SE -> new CoordinateCube(q + 1, r, s - 1);
            case SO -> new CoordinateCube(q - 1, r + 1, s);
            default -> throw new IllegalArgumentException("Direction invalide pour FLAT");
        };
		}
	}

	@Override
	public List<Coordinate> between(Mode mode, Coordinate to) throws DifferentAxisException {
		if (!(to instanceof CoordinateCube target)) throw new DifferentAxisException("Type incompatible.");
		
		if (this.q != target.q && this.r != target.r && this.s != target.s) {
			throw new DifferentAxisException("Les coordonnées ne sont pas sur le même axe.");
		}
		
		List<Coordinate> result = new ArrayList<>();
		int dist = Math.max(Math.max(Math.abs(this.q = target.q), Math.abs(this.r - target.r)), Math.abs(this.s - target.s));
		
		for (int i = 1; i < dist; i++) {
			int newQ = this.q + (target.q - this.q) * i / dist;
			int newR = this.r + (target.r - this.r) * i / dist;
			int newS = this.s + (target.s - this.s) * i / dist;
			result.add(new CoordinateCube(newQ, newR, newS));
		}
		
		return result;
	}

}
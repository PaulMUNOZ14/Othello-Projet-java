package coordinate;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class CoordinateDoubled extends Coordinate {
	
    private int y;
    private int x;

	/**
     * @param y 
     * @param x
     */
    public CoordinateDoubled(int y, int x) {
        this.y = y;
        this.x = x;
    }

	@Override
	public Point to2DCoordinate() {
		return new Point(x, y);
	}

	@Override
	public Coordinate toDir(Mode mode, Direction direction) {
		if (mode == Mode.POINTY) {
            return switch (direction) {
                case E -> new CoordinateDoubled(y, x + 2);
                case O -> new CoordinateDoubled(y, x - 2);
                case NE -> new CoordinateDoubled(y - 1, x + 1);
                case NO -> new CoordinateDoubled(y - 1, x - 1);
                case SE -> new CoordinateDoubled(y + 1, x + 1);
                case SO -> new CoordinateDoubled(y + 1, x - 1);
                default -> throw new IllegalArgumentException("Direction invalide pour POINTY");
            };
        } else {
            return switch (direction) {
                case N -> new CoordinateDoubled(y - 2, x);
                case S -> new CoordinateDoubled(y + 2, x);
                case NE -> new CoordinateDoubled(y - 1, x + 1);
                case NO -> new CoordinateDoubled(y - 1, x - 1);
                case SE -> new CoordinateDoubled(y + 1, x + 1);
                case SO -> new CoordinateDoubled(y + 1, x - 1);
                default -> throw new IllegalArgumentException("Direction invalide pour FLAT");
            };
        }
	}

	@Override
	public List<Coordinate> between(Mode mode, Coordinate to) throws DifferentAxisException {
		if (!(to instanceof CoordinateDoubled target)) throw new DifferentAxisException("Type incompatible.");
        
        int dy = target.y - this.y;
        int dx = target.x - this.x;

        if (mode == Mode.POINTY) {
            if (dy != 0 && Math.abs(dy) != Math.abs(dx)) throw new DifferentAxisException("Pas sur le même axe.");
            if (dy == 0 && dx % 2 != 0) throw new DifferentAxisException("Pas sur le même axe.");
        } else {
            if (dx != 0 && Math.abs(dy) != Math.abs(dx)) throw new DifferentAxisException("Pas sur le même axe.");
            if (dx == 0 && dy % 2 != 0) throw new DifferentAxisException("Pas sur le même axe.");
        }

        int dist;
        if (dy == 0) dist = Math.abs(dx) / 2;
        else if (dx == 0) dist = Math.abs(dy) / 2;
        else dist = Math.abs(dy);

        List<Coordinate> result = new ArrayList<>();
        for (int i = 1; i < dist; i++) {
            int stepY = this.y + (dy * i / dist);
            int stepX = this.x + (dx * i / dist);
            result.add(new CoordinateDoubled(stepY, stepX));
        }
        return result;
	}

}
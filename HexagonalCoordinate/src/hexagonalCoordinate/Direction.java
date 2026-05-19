package hexagonalCoordinate;

/**
 * 
 */
public enum Direction {

	NO,
	N,
	NE,
	E,
	SE,
	S,
	SO,
	O;

    /**
     * @return
     */
    public Direction opposite() {
        return switch (this) {
        	case NO -> SE;
        	case N -> S;
        	case NE -> SO;
        	case E -> O;
        	case SE -> NO;
        	case S -> N;
        	case SO -> NE;
        	case O -> E;
        };
    }

}
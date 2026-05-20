package model.state;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import coordinate.Coordinate;
import coordinate.CoordinateCube;
import coordinate.Mode;
import model.Team;
import model.tokens.Pawn;
import model.tokens.Token;

public class StateTest {

	@Test
	public void testGetPawnsLines_HorizontalLine_POINTY() {
		Map<Coordinate, Token> board = new HashMap<>();
		
		//création d'une ligne de 5 pions noirs alignés vers l'Est
		//en coordonnées cubiques, aller à l'Est modifie q (+1) et s (-1)
		board.put(new CoordinateCube(0, 0, 0), new Pawn(Team.BLACK));
		board.put(new CoordinateCube(1, 0, -1), new Pawn(Team.BLACK));
		board.put(new CoordinateCube(2, 0, -2), new Pawn(Team.BLACK));
		board.put(new CoordinateCube(3, 0, -3), new Pawn(Team.BLACK));
		board.put(new CoordinateCube(4, 0, -4), new Pawn(Team.BLACK));

		//ajout d'un pion blanc hors de la ligne pour s'assurer qu'il n'est pas compté
		board.put(new CoordinateCube(0, 1, -1), new Pawn(Team.WHITE));

		//création de l'état avec notre plateau de test et le mode POINTY
		State state = new State(board, Team.BLACK, new ArrayList<>());

		//exécution de la méthode (Act)
		List<Set<Coordinate>> lines = state.getPawnsLines();

		//vérifications
		assertNotNull(lines, "La liste retournée ne doit pas être null");
		assertEquals(1, lines.size(), "La méthode doit détecter exactement 1 ligne de 5 pions");
		assertEquals(5, lines.get(0).size(), "La ligne détectée doit contenir exactement 5 coordonnées");
	}
}
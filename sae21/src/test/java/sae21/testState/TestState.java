package sae21.testState;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import coordinate.Coordinate;
import coordinate.CoordinateCube;
import coordinate.DifferentAxisException;
import coordinate.Direction;
import coordinate.Mode;
import coordinate.Point;
import model.Team;
import model.action.Move;
import model.state.IState;
import model.state.State;
import model.tokens.Pawn;
import model.tokens.Ring;
import model.tokens.Token;

class TestState {
	
	public static Map<Coordinate, Token> genereTab(int n){
		Map<Coordinate, Token> board = new HashMap<Coordinate, Token>();
		for (int i = -n; i <= n; i++) {
			for (int j = -n; j <= n; j++) {
				if(Math.sqrt(i*i+j*j+(-i-j)*(-i-j)) < n) {
					board.put(new CoordinateCube(i, j, -i-j), null);
				}
			}
		}
		return board;
	}
	
	public static void afficheBoard(Map<Coordinate, Token> board, int n) {
	    // On parcourt les lignes du haut vers le bas
	    // Dans un système hexagonal, la "ligne" peut être représentée par la coordonnée j (ou r)
	    for (int j = -n; j <= n; j++) {
	        
	        // 1. Gérer l'indentation pour donner l'effet de nid d'abeille
	        // Plus on descend, plus on décale, ou inversement selon l'axe.
	        // Ici, on ajoute des espaces proportionnellement à la position pour aligner les hexagones.
	        int espaces = Math.abs(j);
	        for (int e = 0; e < espaces; e++) {
	            System.out.print("  "); // Deux espaces pour un décalage fluide
	        }

	        // 2. Parcourir les colonnes (i) de gauche à droite
	        for (int i = -n; i <= n; i++) {
	            // On reconstruit la clé pour chercher dans la Map
	            CoordinateCube coord = new CoordinateCube(i, j, -i-j);
	            
	            // On vérifie si la coordonnée fait partie du plateau
	            if (board.containsKey(coord)) {
	                Token token = board.get(coord);
	                
	                if (token == null) {
	                    System.out.print("[ . ] "); // Case vide
	                } else if (token instanceof Pawn) {
	                    System.out.print("[ P ] "); // Un Pion
	                } else if (token instanceof Ring) {
	                    System.out.print("[ R ] "); // Un Anneau
	                } else {
	                    System.out.print("[ ? ] "); // Sécurité si autre type de Token
	                }
	            } 
	        }
	        // Fin de la ligne, on passe à la suivante
	        System.out.println();
	    }
	}
	
	
	@Test
	void testMove() throws DifferentAxisException {
		Map<Coordinate, Token> board =  genereTab(5);
		System.out.println(board);
		board.replace(new CoordinateCube(0,0,0), new Ring(Team.BLACK));
		System.out.println(board);
		State state = new State(board, Team.BLACK, null);
		System.out.println(board);
		afficheBoard(board, 5);
		IState sta = state.move(new Move(new CoordinateCube(0, 0, 0), new CoordinateCube(2, 0, -2)));
		assertTrue(sta.board().get(new CoordinateCube(1, 0, -1)) instanceof Pawn);
	}
	
	@Test
	void testLineOfFive() {
	    Map<Coordinate, Token> board = new HashMap<>();
	    Team team = Team.BLACK;
	    Coordinate c1 = new CoordinateCube(0,0,0);
	    Coordinate c2 = new CoordinateCube(1,0,-1);
	    Coordinate c3 = new CoordinateCube(2,0,-2);
	    Coordinate c4 = new CoordinateCube(3,0,-3);
	    Coordinate c5 = new CoordinateCube(4,0,-4);
	    board.put(c1, new Pawn(team));
	    board.put(c2, new Pawn(team));
	    board.put(c3, new Pawn(team));
	    board.put(c4, new Pawn(team));
	    board.put(c5, new Pawn(team));
	    State state = new State(board, team, List.of());
	    List<Set<Coordinate>> lines = state.lines();
	    assertEquals(1, lines.size());
	}
	
	@Test
	void testLineOfSix() {
	    Map<Coordinate, Token> board = new HashMap<>();
	    Team team = Team.BLACK;
	    List<Coordinate> coords = List.of(
	        new CoordinateCube(0,0,0),
	        new CoordinateCube(1,0,-1),
	        new CoordinateCube(2,0,-2),
	        new CoordinateCube(3,0,-3),
	        new CoordinateCube(4,0,-4),
	        new CoordinateCube(5,0,-5)
	    );
	    for (Coordinate c : coords) {
	        board.put(c, new Pawn(team));
	    }
	    State state = new State(board, team, List.of());
	    List<Set<Coordinate>> lines = state.lines();
	    assertEquals(2, lines.size());
	}
	
	@Test
	void testMixedTeamsNoLine() {
	    Map<Coordinate, Token> board = new HashMap<>();
	    board.put(new CoordinateCube(0,0,0), new Pawn(Team.BLACK));
	    board.put(new CoordinateCube(1,0,-1), new Pawn(Team.WHITE));
	    board.put(new CoordinateCube(2,0,-2), new Pawn(Team.BLACK));
	    board.put(new CoordinateCube(3,0,-3), new Pawn(Team.BLACK));
	    board.put(new CoordinateCube(4,0,-4), new Pawn(Team.BLACK));
	    State state = new State(board, Team.BLACK, List.of());
	    List<Set<Coordinate>> lines = state.lines();
	    assertEquals(0, lines.size());
	}
	
	@Test
	void testLineContent() {
	    Map<Coordinate, Token> board = new HashMap<>();
	    Team team = Team.BLACK;
	    Coordinate c1 = new CoordinateCube(0,0,0);
	    Coordinate c2 = new CoordinateCube(1,0,-1);
	    Coordinate c3 = new CoordinateCube(2,0,-2);
	    Coordinate c4 = new CoordinateCube(3,0,-3);
	    Coordinate c5 = new CoordinateCube(4,0,-4);
	    board.put(c1, new Pawn(team));
	    board.put(c2, new Pawn(team));
	    board.put(c3, new Pawn(team));
	    board.put(c4, new Pawn(team));
	    board.put(c5, new Pawn(team));
	    State state = new State(board, team, List.of());
	    List<Set<Coordinate>> lines = state.lines();
	    Set<Coordinate> expected = Set.of(c1, c2, c3, c4, c5);
	    assertTrue(lines.contains(expected));
	}
	
	@Test
	void testWinner_none() {
	    Map<Coordinate, Token> board = new HashMap<>();
	    List<Coordinate> white = List.of(
	        new CoordinateCube(0, 0, 0),
	        new CoordinateCube(1, -1, 0),
	        new CoordinateCube(2, -2, 0),
	        new CoordinateCube(3, -3, 0),
	        new CoordinateCube(4, -4, 0)
	    );
	    List<Coordinate> black = List.of(
	        new CoordinateCube(0, 1, -1),
	        new CoordinateCube(1, 0, -1),
	        new CoordinateCube(2, -1, -1),
	        new CoordinateCube(3, -2, -1),
	        new CoordinateCube(4, -3, -1)
	    );
	    for (Coordinate c : white) {
	        board.put(c, new Ring(Team.WHITE));
	    }
	    for (Coordinate c : black) {
	        board.put(c, new Ring(Team.BLACK));
	    }
	    State state = new State(board, Team.WHITE, List.of());
	    assertNull(state.winner());
	}
	
	@Test
	void testWinner_whiteWins() {
	    Map<Coordinate, Token> board = new HashMap<>();
	    board.put(new CoordinateCube(0, 0, 0), new Ring(Team.WHITE));
	    board.put(new CoordinateCube(1, -1, 0), new Ring(Team.WHITE));
	    board.put(new CoordinateCube(-2, 2, 0), new Ring(Team.BLACK));
	    board.put(new CoordinateCube(-1, 1, 0), new Ring(Team.BLACK));
	    board.put(new CoordinateCube(0, 0, 0), new Ring(Team.BLACK));
	    board.put(new CoordinateCube(1, -1, 0), new Ring(Team.BLACK));
	    board.put(new CoordinateCube(2, -2, 0), new Ring(Team.BLACK));
	    State state = new State(board, Team.WHITE, List.of());
	    assertEquals(Team.WHITE, state.winner());
	}
	
	@Test
	void testWinner_blackWins() {
	    Map<Coordinate, Token> board = new HashMap<>();
	    board.put(new CoordinateCube(0, 1, -1), new Ring(Team.BLACK));
	    board.put(new CoordinateCube(1, 0, -1), new Ring(Team.BLACK));
	    board.put(new CoordinateCube(-2, 2, 0), new Ring(Team.WHITE));
	    board.put(new CoordinateCube(-1, 1, 0), new Ring(Team.WHITE));
	    board.put(new CoordinateCube(0, 0, 0), new Ring(Team.WHITE));
	    board.put(new CoordinateCube(1, -1, 0), new Ring(Team.WHITE));
	    board.put(new CoordinateCube(2, -2, 0), new Ring(Team.WHITE));
	    State state = new State(board, Team.WHITE, List.of());
	    assertEquals(Team.BLACK, state.winner());
	}
	
	@Test
	void testWinner_twoRemoved_noWin() {
	    Map<Coordinate, Token> board = new HashMap<>();
	    board.put(new CoordinateCube(0, 0, 0), new Ring(Team.WHITE));
	    board.put(new CoordinateCube(1, -1, 0), new Ring(Team.WHITE));
	    board.put(new CoordinateCube(2, -2, 0), new Ring(Team.WHITE));
	    board.put(new CoordinateCube(-2, 2, 0), new Ring(Team.BLACK));
	    board.put(new CoordinateCube(-1, 1, 0), new Ring(Team.BLACK));
	    board.put(new CoordinateCube(0, 1, -1), new Ring(Team.BLACK));
	    board.put(new CoordinateCube(1, 0, -1), new Ring(Team.BLACK));
	    board.put(new CoordinateCube(2, -1, -1), new Ring(Team.BLACK));
	    State state = new State(board, Team.WHITE, List.of());
	    assertNull(state.winner());
	}
	
	@Test
	void testWinner_emptyBoard() {
	    Map<Coordinate, Token> board = new HashMap<>();
	    State state = new State(board, Team.WHITE, List.of());
	    assertNull(state.winner());
	}
	
	/*@Test
	void testWinner_draw_noMoves() {
	    Map<Coordinate, Token> board = new HashMap<>();
	    Coordinate white = new CoordinateCube(0, 0, 0);
	    board.put(white, new Ring(Team.WHITE));
	    board.put(new CoordinateCube(1, -1, 0), new Ring(Team.BLACK));
	    board.put(new CoordinateCube(-1, 1, 0), new Ring(Team.BLACK));
	    board.put(new CoordinateCube(1, 0, -1), new Pawn(Team.BLACK));
	    board.put(new CoordinateCube(-1, 0, 1), new Pawn(Team.BLACK));
	    State state = new State(board, Team.WHITE, List.of());
	    assertNull(state.winner());
	}*/
	
	@Test
	void testIsInField_true() {
	    Map<Coordinate, Token> board = new HashMap<>();
	    Coordinate c1 = new CoordinateCube(0, 0, 0);
	    board.put(c1, new Pawn(Team.BLACK));
	    State state = new State(board, Team.BLACK, List.of());
	    assertTrue(state.isInField(c1));
	}
	
	@Test
	void testIsInField_false() {

	    Map<Coordinate, Token> board = new HashMap<>();

	    Coordinate c1 = new CoordinateCube(0, 0, 0);

	    State state = new State(board, Team.BLACK, List.of());

	    assertFalse(state.isInField(c1));
	}
	
	@Test
	void testIsInField_null() {

	    State state = new State(new HashMap<>(), Team.BLACK, List.of());

	    assertFalse(state.isInField(null));
	}
	
	@Test
	void testHashCode_consistency() {
	    Map<Coordinate, Token> board = new HashMap<>();
	    board.put(new CoordinateCube(0,0,0), new Pawn(Team.BLACK));
	    State s1 = new State(board, Team.BLACK, List.of());
	    State s2 = new State(new HashMap<>(board), Team.BLACK, List.of());
	    assertEquals(s1, s2);
	    assertEquals(s1.hashCode(), s2.hashCode());
	}
	
	@Test
	void testHashCode_differentStates() {
	    Map<Coordinate, Token> board1 = new HashMap<>();
	    board1.put(new CoordinateCube(0,0,0), new Pawn(Team.BLACK));
	    Map<Coordinate, Token> board2 = new HashMap<>();
	    board2.put(new CoordinateCube(1,0,-1), new Pawn(Team.BLACK));
	    State s1 = new State(board1, Team.BLACK, List.of());
	    State s2 = new State(board2, Team.BLACK, List.of());
	    assertNotEquals(s1.hashCode(), s2.hashCode());
	}
	
	@Test
	void testHashCode_differentStates2() {
	    Map<Coordinate, Token> board1 = new HashMap<>();
	    board1.put(new CoordinateCube(0,0,0), new Pawn(Team.BLACK));
	    Map<Coordinate, Token> board2 = new HashMap<>();
	    board2.put(new CoordinateCube(1,0,-1), new Pawn(Team.BLACK));
	    State s1 = new State(board1, Team.BLACK, List.of());
	    State s2 = new State(board2, Team.BLACK, List.of());
	    assertNotEquals(s1.hashCode(), s2.hashCode());
	}
	
	@Test
	void testEquals_true() {
	    Map<Coordinate, Token> board = new HashMap<>();
	    Coordinate c1 = new CoordinateCube(0, 0, 0);
	    board.put(c1, new Pawn(Team.BLACK));
	    List<Set<Coordinate>> lines = List.of();
	    State s1 = new State(board, Team.BLACK, lines);
	    State s2 = new State(new HashMap<>(board), Team.BLACK, lines);
	    assertEquals(s1, s2);
	}
	
	@Test
	void testEquals_false_board() {
	    Map<Coordinate, Token> board1 = new HashMap<>();
	    board1.put(new CoordinateCube(0,0,0), new Pawn(Team.BLACK));
	    Map<Coordinate, Token> board2 = new HashMap<>();
	    board2.put(new CoordinateCube(1,0,-1), new Pawn(Team.BLACK));
	    State s1 = new State(board1, Team.BLACK, List.of());
	    State s2 = new State(board2, Team.BLACK, List.of());
	    assertNotEquals(s1, s2);
	}
	
	@Test
	void testEquals_false_turn() {
	    Map<Coordinate, Token> board = new HashMap<>();
	    board.put(new CoordinateCube(0,0,0), new Pawn(Team.BLACK));
	    State s1 = new State(board, Team.BLACK, List.of());
	    State s2 = new State(board, Team.WHITE, List.of());
	    assertNotEquals(s1, s2);
	}
}

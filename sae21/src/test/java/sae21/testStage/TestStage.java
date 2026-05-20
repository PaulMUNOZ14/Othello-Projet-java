package sae21.testStage;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

class TestStage {
	
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

}

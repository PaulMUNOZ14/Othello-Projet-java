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
	    for (int j = -n; j <= n; j++) {
	        
	        // 1. Gérer l'indentation pour l'effet de grille hexagonale (nid d'abeille)
	        int espaces = Math.abs(j);
	        for (int e = 0; e < espaces; e++) {
	            System.out.print("  "); // Deux espaces pour un décalage fluide
	        }

	        // 2. Parcourir les colonnes de gauche à droite
	        for (int i = -n; i <= n; i++) {
	            CoordinateCube coord = new CoordinateCube(i, j, -i-j);
	            
	            // On vérifie si la coordonnée fait partie du plateau
	            if (board.containsKey(coord)) {
	                Token token = board.get(coord);
	                
	                if (token == null) {
	                    System.out.print("[ . ] "); // Case vide
	                } else {
	                    // On récupère l'équipe (WHITE ou BLACK)
	                    String t = (token.getTeam() == Team.WHITE) ? "W" : "B";
	                    
	                    if (token instanceof Ring) {
	                        // Ex: [ROB] pour Ring Orange/Oeil Black ou simplement [R.B]
	                        System.out.print("[" + t + "R ] "); // Exemple : [WR ] ou [BR ]
	                    } else if (token instanceof Pawn) {
	                        System.out.print("[" + t + "P ] "); // Exemple : [WP ] ou [BP ]
	                    } else {
	                        System.out.print("[ ? ] "); // Sécurité
	                    }
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
		board.replace(new CoordinateCube(0,0,0), new Ring(Team.BLACK));
		State state = new State(board, Team.BLACK, null);
		IState sta = state.move(new Move(new CoordinateCube(0, 0, 0), new CoordinateCube(2, 0, -2)));
		assertTrue(sta.board().get(new CoordinateCube(1, 0, -1)) instanceof Pawn);
		assertTrue(sta.board().get(new CoordinateCube(1, 0, -1)).getTeam() == Team.BLACK);
		afficheBoard(board, 5);
		
	}

}

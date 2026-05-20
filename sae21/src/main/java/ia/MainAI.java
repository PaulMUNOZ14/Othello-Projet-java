package ia;

import java.util.Scanner;
import model.Team;
import model.state.State;
import model.action.Action;
import model.action.Move;
import model.action.RemoveLine;
import coordinate.Direction;
import coordinate.CoordinateCube;
import coordinate.Coordinate;
import coordinate.Mode;
import model.factory.FactoryCube;
import ui.CUIMain;

public class MainAI {
	/**
	 * Fonction principale pour utiliser l'IA
	 * @param args
	 */
	public static void main(String[] args) {
		
		State gameState = (State) new FactoryCube().testState();
		MinimaxAI ai = new MinimaxAI(3);
        Scanner scanner = new Scanner(System.in);
        
        boolean gameRunning = true;
        Team currentTurn = Team.WHITE;
        
        System.out.println("_________DEBUT DE LA PARTIE CONTRE L'IA_________");
        System.out.println("format de saisie : q r Direction (ex : 0 0 NE)");
        System.out.println("Tapez 'exit' pour quitter\n");
        
        while (gameRunning && gameState != null && gameState.winner() == null) {
        	CUIMain.afficherPlateauDynamique(gameState.board(), Mode.POINTY);
        	if (currentTurn == Team.WHITE) {
        		System.out.println("\nC'est à votre tour (blancs), veuillez entrer votre coup > ");
        		String coup = scanner.nextLine();
        		
        		if(coup.trim().equalsIgnoreCase("exit")) {
        			gameRunning = false;
        			System.out.println("Arret de la partie.");
        			break;
        		}
        		
        		try {
        			String[] parties = coup.trim().split("\\s+");
        			if (parties.length == 3) {
        				int q = Integer.parseInt(parties[0]);
        				int r = Integer.parseInt(parties[1]);
        				int s = -q - r; //application contrainte q + r + s = 0
        				
        				Direction dir = Direction.valueOf(parties[2].toUpperCase());
        				
        				Coordinate depart = new CoordinateCube(q, r, s);
        				Coordinate arrivee = depart.toDir(Mode.POINTY, dir);
        				
        				Move moveAction = new Move(depart, arrivee);
        				
        				gameState = (State) (gameState.move(moveAction));
        				
        				currentTurn = Team.BLACK;
        			}
        			else {
        				System.out.println("Erreur : Format attendu 'q r Direction'");
        			}	
        		}
        		catch(IllegalArgumentException e) {
        			System.out.println("Erreur de saisie ou direction invalide : " + e.getMessage());
        		}
        		catch(Exception e) {
        			System.out.println("Erreur lors du déplacement : " + e.getMessage());
        		}
        		
        	}
        	else {
                System.out.println("\nL'IA (Noirs) réfléchit...");
                Action aiMove = ai.chooseMove(gameState);
                
                if (aiMove != null) {
                	System.out.println("L'IA a joué.");
                	try {
                		if (aiMove instanceof Move) {
                			gameState = (State) (gameState.move((Move) aiMove));
                		}
                		else if (aiMove instanceof RemoveLine) {
                			gameState = (State) (gameState.removeLine((RemoveLine) aiMove));
                		}
                	}
                	catch (Exception e) {
                		System.out.println("Erreur lors de l'exécution du coup de l'IA : " + e.getMessage());
                	}
                }
                else {
               		System.out.println("L'IA ne trouve plus de coup possible");
               		gameRunning = false;
                }
                
                currentTurn = Team.WHITE;
            }
        }
        
        System.out.println("\n_________PARTIE TERMINÉE_________");
        if (gameState != null && gameState.winner() != null) {
        	System.out.println("Le vainqueur est : " + gameState.winner());
        } else if (gameState != null) {
        	System.out.println("Match Nul");
        }
        
        scanner.close();
	}
}
package model.state;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import coordinate.Coordinate;
import coordinate.CoordinateCube;
import coordinate.DifferentAxisException;
import coordinate.Direction;
import coordinate.Mode;
import model.Team;
import model.action.Move;
import model.action.RemoveLine;
import model.tokens.*;

public record State(Map<Coordinate, Token> board, Team turn, List<Set<Coordinate>> lines) implements IState{
	

	@Override
	public IState move(Move move) throws DifferentAxisException {
		if(!board.containsKey(move.getFrom())) throw new IndexOutOfBoundsException("case hors platau");
		if(!board.containsKey(move.getTo())) throw new IndexOutOfBoundsException("case hors platau");
	    Token ring = board.get(move.getFrom());
	    if (!(ring instanceof Ring || board.get(ring).getTeam() != turn)) {
	        throw new IllegalArgumentException("Pas d'anneau du joueur à l'endroit donné");
	    }

	    if (board.get(move.getTo()) != null) {
	        throw new IllegalArgumentException("Arrivée occupée");
	    }

	    List<Coordinate> entre = move.getFrom().between(Mode.POINTY, move.getTo());
	    

	    if (! availableMoves(move.getFrom()).contains(move.getTo())) throw new IllegalArgumentException("coup impossible");

	    Map<Coordinate, Token> new_board = new HashMap<>(board);
	    
	    for (Coordinate coordinate : entre) {
	        Token piece = board.get(coordinate);
	        if (piece instanceof Pawn) {
	            new_board.put(coordinate, new Pawn(piece.getTeam().other()));
	        } else if (piece == null) {
	        	new_board.put(coordinate, new Pawn(ring.getTeam()));
	        }
	        System.out.println(coordinate);
	    }

	    new_board.put(move.getFrom(), new Pawn(ring.getTeam()));

	    new_board.put(move.getTo(), ring);

	    Team nextTurn = this.turn.other(); 

	    return new State(new_board, nextTurn, lines);
	}

	@Override
	public IState removeLine(RemoveLine removeLine) {

	    if (removeLine.getLine().size() != 5) {
	        throw new RuntimeException(
	            "Taille de ligne impossible : " + removeLine.getLine().size()
	        );
	    }

	    if (!(board.get(removeLine.getRing()) instanceof Ring)) {
	        throw new RuntimeException(
	            "La coordonnée spécifiée ne contient pas un anneau"
	        );
	    }

	    Team teamLine = null;

	    for (Coordinate coo : removeLine.getLine()) {

	        if (!board.containsKey(coo)) {
	            throw new RuntimeException("Case de la ligne hors plateau");
	        }

	        Token piece = board.get(coo);

	        if (!(piece instanceof Pawn)) {
	            throw new RuntimeException(
	                "La ligne ne doit contenir que des pions"
	            );
	        }

	        if (teamLine == null) {
	            teamLine = piece.getTeam();
	        } else if (piece.getTeam() != teamLine) {
	            throw new RuntimeException(
	                "La ligne contient plusieurs couleurs"
	            );
	        }
	    }

	    Map<Coordinate, Token> new_board = new HashMap<>(board);

	    for (Coordinate co : removeLine.getLine()) {
	        new_board.put(co, null); 
	    }

	    new_board.put(removeLine.getRing(), null); 

	    return new State(new_board, turn, lines);
	}

	@Override
	public Set<Coordinate> availableMoves(Coordinate from) {
	    ArrayList<Direction> dir = new ArrayList<Direction>();
	    dir.add(Direction.NE);
	    dir.add(Direction.NO);
	    dir.add(Direction.SE);
	    dir.add(Direction.SO);
	    dir.add(Direction.E);
	    dir.add(Direction.O);
	    Set<Coordinate> moves = new HashSet<Coordinate>();
	    for (Direction direction : dir) {
			boolean continu = true;
			boolean pawn_encountered = false;
			Coordinate position = from;
			
			
			while(continu && board.containsKey(position.toDir(Mode.POINTY, direction))) {
				position = position.toDir(Mode.POINTY, direction);
				if(board.get(position) == null) {
					moves.add(position);
					if (pawn_encountered) continu = false;
				}
				else if(board.get(position) instanceof Ring){
					continu = false;
				} else if(board.get(position) instanceof Pawn){
					pawn_encountered = true;
				}
			}
		}
	    System.out.println(moves);
	    return moves;
	}

	@Override
	public Map<Coordinate, Token> board() {
		return board;
	}

	@Override
	public Map<Team, List<Coordinate>> rings() {
		Map<Team, List<Coordinate>> anneaux = new HashMap<Team, List<Coordinate>>();
		anneaux.put(Team.BLACK, new ArrayList<Coordinate>());
		anneaux.put(Team.WHITE, new ArrayList<Coordinate>());
		for (Coordinate coordinates : board.keySet()) {
			if (board.get(coordinates) instanceof Ring) {
				anneaux.get(board.get(coordinates).getTeam()).add(coordinates);
			}
		}
		return anneaux;
	}

	@Override
	public List<Set<Coordinate>> lines() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Team turn() {
		return turn;
	}

	@Override
	//liste finale qui contiendra toutes les lignes de 5 trouvées
	public List<Set<Coordinate>> getPawnsLines() {
		List<Set<Coordinate>> allLines = new ArrayList<>();
		Direction[] directionsToTest = {Direction.E, Direction.SE, Direction.SO};
		
		//boucle sur toutes les coordonnées enregistrées dans notre plateau
		for (Coordinate startCoord : this.board.keySet()) {
			Token token = this.board.get(startCoord);
		
			//si la case est vide ou si ce n'est pas un Pion (si c'est un Anneau par exemple) alors on ignore
			if (token == null || !(token instanceof Pawn)) {
	            continue;
	        }
			
			//Test des 3 directions a partir de ce point de départ 
			Team currentTeam = token.getTeam();
			for (Direction dir : directionsToTest) {
	            Set<Coordinate> currentLine = new HashSet<>();
	            currentLine.add(startCoord);
	            Coordinate currentCoord = startCoord;
	            
	            for (int i = 0; i < 4; i++) {
	            	//on avance d'une case dans la direction testée
	                try {
	                	//a verifier
	                	currentCoord = currentCoord.toDir(Mode.POINTY, dir);
	                	
	                	Token nextToken = this.board.get(currentCoord);

						//si c'est bien un pion de la même équipe, on l'ajoute à la ligne
						if (nextToken != null && (nextToken instanceof Pawn) && nextToken.getTeam() == currentTeam) {
							currentLine.add(currentCoord);
						} else {
							break; //la ligne est brisée
						}
	                } catch (Exception e) {
	                	break; //on est sorti des limites du terrain
	                }
	            }
	            
	            //si on a exactement 5 pions, on sauvegarde cette ligne
	            if (currentLine.size() == 5) {
					allLines.add(currentLine);
				}
			}
		}
		
		return allLines;
	}
	
	
	
	
	
	/**
	 * RemoveToken
	 * Crée un nouvel état en supprimant le jeton situé à la coordonnée indiquée.
	 * Respecte l'immuabilité en clonant le plateau avant d'effectuer la suppression.
	 * * @param coordinate La coordonnée de la case à vider.
	 * @return Une nouvelle instance de State avec le plateau mis à jour.
	 */
	public State removeToken(Coordinate coordinate) {
	    Map<Coordinate, Token> new_board = new HashMap<>(this.board);
	    new_board.remove(coordinate);
	    return new State(new_board, this.turn, this.lines);
	}
	
	
	
	/**
	 * ToggleToken
	 * Ajoute ou supprime dynamiquement un jeton sur le plateau via la réflexion Java.
	 * Si un jeton de la même classe et de la même équipe est déjà présent sur la case, il est retiré.
	 * Sinon, un nouveau jeton est instancié à la volée et placé sur le plateau.
	 * * @param coordinate La coordonnée cible sur le plateau.
	 * @param tokenClass La classe du jeton à créer (ex: Pawn.class ou Ring.class).
	 * @param team L'équipe (Team) à assigner au jeton.
	 * @return Une nouvelle instance de State avec le plateau mis à jour.
	 * @throws RuntimeException Si la génération de l'instance par réflexion échoue.
	 */
	
	public State toggleToken(Coordinate coordinate, Class<? extends Token> tokenClass, Team team) {
	    try {
	        java.lang.reflect.Constructor<?> constructor = tokenClass.getConstructors()[0];
	        Token newToken = (Token) constructor.newInstance(team);
	        
	        Map<Coordinate, Token> new_board = new HashMap<>(this.board);
	        Token existingToken = new_board.get(coordinate);
	        
	        if (existingToken != null && existingToken.getClass().equals(tokenClass) && existingToken.getTeam() == team) {
	            new_board.remove(coordinate);
	        } else {
	            new_board.put(coordinate, newToken);
	        }
	        
	        return new State(new_board, this.turn, this.lines);
	        
	    } catch (Exception e) {
	        throw new RuntimeException("Erreur lors de la création du token par réflexion", e);
	    }
	}
	
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
	
}

package model.state;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
	    if (ring == null || !(ring instanceof Ring) || ring.getTeam() != turn) {
	        throw new IllegalArgumentException("Pas d'anneau du joueur à l'endroit donné");
	    }

	    if (board.get(move.getTo()) != null) {
	        throw new IllegalArgumentException("Arrivée occupée");
	    }

	    List<Coordinate> entre = move.getFrom().between(Mode.POINTY, move.getTo());
	    

	    if (! availableMoves(move.getFrom()).contains(move.getTo())) throw new IllegalArgumentException("coup impossible");

	    Map<Coordinate, Token> new_board = new HashMap<>(board);
	    
	    // Parcours uniquement les cases intermédiaires situées STRICTEMENT entre le départ et l'arrivée
	    for (Coordinate coordinate : entre) {
	        if (coordinate.equals(move.getFrom()) || coordinate.equals(move.getTo())) {
	            continue;
	        }
	        Token piece = board.get(coordinate);
	        if (piece instanceof Pawn) {
	            // Règle du retournement : on inverse l'équipe du pion survolé
	            new_board.put(coordinate, new Pawn(piece.getTeam().other()));
	        } 
	        // CORRECTION : L'anneau ne doit rien faire si la case (piece) est null. 
	        // On a supprimé le 'else if (piece == null)' qui semait des pions à l'infini.
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
	    List<Set<Coordinate>> result = new ArrayList<>();
	    Direction[] directions = {
	        Direction.E,
	        Direction.NE,
	        Direction.SE
	    };
	    for (Coordinate start : board.keySet()) {
	        Token token = board.get(start);
	        if (!(token instanceof Pawn pawn))
	            continue;
	        Team team = pawn.getTeam();
	        for (Direction dir : directions) {
	        	Coordinate previous = start.toDir(Mode.POINTY, dir.opposite());
	        	Token previousToken = board.get(previous);
	        	if (previousToken instanceof Pawn prevPawn
	        	        && prevPawn.getTeam() == team) {
	        	    continue;
	        	}
	            List<Coordinate> aligned = new ArrayList<>();
	            aligned.add(start);
	            Coordinate current = start.toDir(Mode.POINTY, dir);
	            while (board.containsKey(current)) {
	                Token t = board.get(current);
	                if (!(t instanceof Pawn p))
	                    break;
	                if (p.getTeam() != team)
	                    break;
	                aligned.add(current);
	                current = current.toDir(Mode.POINTY, dir);
	            }
	            	if (aligned.size() >= 5) {
	                for (int i = 0; i <= aligned.size() - 5; i++) {
	                    Set<Coordinate> line = new HashSet<>();
	                    for (int j = 0; j < 5; j++) {
	                        line.add(aligned.get(i + j));
	                    }
	                    if (!result.contains(line)) {
	                        result.add(line);
	                    }
	                }
	            }
	        }
	    }
	    return result;
	}

	@Override
	public Team turn() {
		return turn;
	}

	@Override
	public Team winner() {
	    Map<Team, List<Coordinate>> r = rings();
	    int whiteRemaining = r.get(Team.WHITE).size();
	    int blackRemaining = r.get(Team.BLACK).size();
	    if (whiteRemaining == 0 && blackRemaining == 0) {
	        return null;
	    }
	    if (5 - whiteRemaining >= 3) {
	        return Team.WHITE;
	    }
	    if (5 - blackRemaining >= 3) {
	        return Team.BLACK;
	    }
	    boolean canPlay = false;

	    for (Coordinate pos : r.get(turn)) {
	        if (!availableMoves(pos).isEmpty()) {
	            canPlay = true;
	            break;
	        }
	    }
	    if (!canPlay) {
	        return null;
	    }
	    return null;
	}
	
	@Override
	public List<Set<Coordinate>> getPawnsLines() {
		List<Set<Coordinate>> allLines = new ArrayList<>();
		Direction[] directionsToTest = {Direction.E, Direction.SE, Direction.SO};
		
		for (Coordinate startCoord : this.board.keySet()) {
			Token token = this.board.get(startCoord);
		
			if (token == null || !(token instanceof Pawn)) {
	            continue;
	        }
			
			Team currentTeam = token.getTeam();
			for (Direction dir : directionsToTest) {
	            Set<Coordinate> currentLine = new HashSet<>();
	            currentLine.add(startCoord);
	            Coordinate currentCoord = startCoord;
	            
	            for (int i = 0; i < 4; i++) {
	                try {
	                	currentCoord = currentCoord.toDir(Mode.POINTY, dir);
	                	
	                	Token nextToken = this.board.get(currentCoord);

						if (nextToken != null && (nextToken instanceof Pawn) && nextToken.getTeam() == currentTeam) {
							currentLine.add(currentCoord);
						} else {
							break; 
						}
	                } catch (Exception e) {
	                	break; 
	                }
	            }
	            
	            if (currentLine.size() == 5) {
					allLines.add(currentLine);
				}
			}
		}
		
		return allLines;
	}
	
	public State removeToken(Coordinate coordinate) {
	    Map<Coordinate, Token> new_board = new HashMap<>(this.board);
	    new_board.remove(coordinate);
	    return new State(new_board, this.turn, this.lines);
	}

	public IState toggleToken(Coordinate position, Class<?> token, Team team) {
	    if (!this.board.containsKey(position)) {
	        throw new IllegalArgumentException("La coordonnée spécifiée est hors plateau");
	    }

	    Map<Coordinate, Token> new_board = new HashMap<>(this.board);
	    Token existingToken = new_board.get(position);

	    Token newToken;
	    if (token.equals(Pawn.class)) {
	        newToken = new Pawn(team);
	    } else if (token.equals(Ring.class)) {
	        newToken = new Ring(team);
	    } else {
	        throw new IllegalArgumentException("Type de token non supporté : " + token.getSimpleName());
	    }

	    if (existingToken != null && existingToken.getClass().equals(token) && existingToken.getTeam() == team) {
	        new_board.put(position, null); 
	    } else {
	        new_board.put(position, newToken); 
	    }

	    return new State(new_board, this.turn, this.lines);
	}

	
	public boolean isInField(Coordinate c) {
	    if (c == null) return false;
	    if (board == null) return false;
	    return board.containsKey(c);
	}

	@Override
	public int hashCode() {
		return Objects.hash(board, lines, turn);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		State other = (State) obj;
		return Objects.equals(board, other.board) && Objects.equals(lines, other.lines) && turn == other.turn;
	}
}
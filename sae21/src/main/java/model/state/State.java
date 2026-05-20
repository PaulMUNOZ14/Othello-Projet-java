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
		if(!board.containsKey(move.getFrom())) throw new IllegalArgumentException("case hors platau");
		if(!board.containsKey(move.getTo())) throw new IllegalArgumentException("case hors platau");
	    Token ring = board.get(move.getFrom());
	    if (!(ring instanceof Ring)) {
	        throw new IllegalArgumentException("Pas d'anneau à l'endroit donné");
	    }

	    if (board.get(move.getTo()) != null) {
	        throw new IllegalArgumentException("Arrivée occupée");
	    }

	    List<Coordinate> entre = move.getFrom().between(Mode.POINTY, move.getTo());
	    

	    boolean aRencontreVide = false;
	    
	    for (Coordinate coordinate : entre) {
	        Token piece = board.get(coordinate);
	        
	        if (piece instanceof Ring) {
	            throw new IllegalArgumentException("Impossible de sauter un anneau");
	        }
	        
	        if (piece == null) {
	            aRencontreVide = true;
	        } else if (piece instanceof Pawn && aRencontreVide) {
	            throw new IllegalArgumentException("Impossible de sauter par-dessus du vide puis un pion");
	        }
	    }

	    Map<Coordinate, Token> new_board = new HashMap<>(board);
	    
	    for (Coordinate coordinate : entre) {
	        Token piece = board.get(coordinate);
	        if (piece instanceof Pawn) {
	            new_board.put(coordinate, new Pawn(piece.getTeam().other()));
	        }
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
	        new_board.remove(co);
	    }

	    new_board.remove(removeLine.getRing());

	    return new State(new_board, turn, lines);
	}

	@Override
	public Set<Coordinate> availableMoves(Coordinate from) {
	    Map<Mode, ArrayList<Direction>> dir = new HashMap<Mode, ArrayList<Direction>>();
	    Mode mode = Mode.POINTY;
	    dir.put(Mode.FLAT, new ArrayList<Direction>());
	    dir.put(Mode.POINTY, new ArrayList<Direction>());
	    dir.get(Mode.POINTY).add(Direction.NE);
	    dir.get(Mode.POINTY).add(Direction.NO);
	    dir.get(Mode.POINTY).add(Direction.SE);
	    dir.get(Mode.POINTY).add(Direction.SO);
	    dir.get(Mode.POINTY).add(Direction.E);
	    dir.get(Mode.POINTY).add(Direction.O);
	    dir.get(Mode.FLAT).add(Direction.NE);
	    dir.get(Mode.FLAT).add(Direction.NO);
	    dir.get(Mode.FLAT).add(Direction.SE);
	    dir.get(Mode.FLAT).add(Direction.SO);
	    dir.get(Mode.FLAT).add(Direction.N);
	    dir.get(Mode.FLAT).add(Direction.S);
	    Set<Coordinate> moves = new HashSet<Coordinate>();
	    for (Direction direction : dir.get(mode)) {
			boolean continu = true;
			boolean pawn_encountered = false;
			Coordinate position = from;
			
			
			while(continu && board.containsKey(position.toDir(mode, direction))) {
				position = from.toDir(mode, direction);
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
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<Set<Coordinate>> getPawnsLines() {
		// TODO Auto-generated method stub
		return null;
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

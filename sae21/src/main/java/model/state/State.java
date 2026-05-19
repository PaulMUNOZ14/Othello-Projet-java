package model.state;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.management.RuntimeErrorException;

import coordinate.Coordinate;
import coordinate.DifferentAxisException;
import coordinate.Mode;
import model.Team;
import model.action.Move;
import model.action.RemoveLine;
import model.tokens.*;

public record State(Map<Coordinate, Token> board, Team turn, List<Set<Coordinate>> lines) implements IState{

	@Override
	public IState move(Move move) throws DifferentAxisException {
		if(!board.containsKey(move.getFrom())) throw new RuntimeErrorException(null, "case hors platau");
		if(!board.containsKey(move.getTo())) throw new RuntimeErrorException(null, "case hors platau");
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
	        throw new IllegalArgumentException(
	            "Taille de ligne impossible : " + removeLine.getLine().size()
	        );
	    }

	    if (!(board.get(removeLine.getRing()) instanceof Ring)) {
	        throw new IllegalArgumentException(
	            "La coordonnée spécifiée ne contient pas un anneau"
	        );
	    }

	    Team teamLine = null;

	    for (Coordinate coo : removeLine.getLine()) {

	        if (!board.containsKey(coo)) {
	            throw new IllegalArgumentException("Case de la ligne hors plateau");
	        }

	        Token piece = board.get(coo);

	        if (!(piece instanceof Pawn)) {
	            throw new IllegalArgumentException(
	                "La ligne ne doit contenir que des pions"
	            );
	        }

	        if (teamLine == null) {
	            teamLine = piece.getTeam();
	        } else if (piece.getTeam() != teamLine) {
	            throw new IllegalArgumentException(
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Coordinate, Token> board() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Team, List<Coordinate>> rings() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Set<Coordinate>> lines() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Team turn() {
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
	    Map<Coordinate, Token> board = currentState.board();
	    if (board == null) return false;
	    return board.containsKey(c);
	}
	
}

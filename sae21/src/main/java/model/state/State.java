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
	public List<Set<Coordinate>> getPawnsLines() {
		// TODO Auto-generated method stub
		return null;
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

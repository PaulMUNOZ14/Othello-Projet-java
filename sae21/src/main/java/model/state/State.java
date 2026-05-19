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
		if (!(board.get(move.getFrom()) instanceof Ring)) throw new IllegalArgumentException("Pas d'anneau à l'endroit donné");
		if (!(board.get(move.getFrom()) instanceof Token)) throw new IllegalArgumentException("Arrivée occupée");
		List<Coordinate> entre = move.getFrom().between(Mode.POINTY, move.getTo());
		for (Coordinate coordinate : entre) {
			if(board.get(coordinate) instanceof Ring) {
				throw new IllegalArgumentException("Anneau sur le chemin");
			}
			else if(board.get(coordinate) instanceof Ring && coordinate != entre.get(entre.size()-1)) {
				throw new IllegalArgumentException("Pion sur le chemin");
			}
		}
		Map<Coordinate, Token> new_board = new HashMap<Coordinate, Token>(board);
		return new State(new_board, turn, lines); //peut être faire passer le tour
	}

	@Override
	public IState removeLine(RemoveLine removeLine) {
		if (removeLine.getLine().size() != 5) throw new RuntimeErrorException(null, "taille de ligne impossible : " + removeLine.getLine().size());
		return null;
	}

	@Override
	public Set<Coordinate> availableMoves(Coordinate from) {
		// TODO Auto-generated method stub
		return null;
	}

	/*@Override
	public Map<Coordinate, Token> board() {
		// TODO Auto-generated method stub
		return null;
	}*/

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

package model.state;

import java.util.List;
import java.util.Map;
import java.util.Set;

import coordinate.*;
import model.Team;
import model.action.*;
import model.tokens.Token;

public interface IState {
	public IState move(Move move) throws DifferentAxisException;
	public IState removeLine(RemoveLine removeLine);
	public Set<Coordinate> availableMoves(Coordinate from);
	public Map<Coordinate, Token> board();
	public Map<Team, List<Coordinate>> rings();
	public List<Set<Coordinate>> lines();
	
	
	public Team turn();
	public Team winner();
	
	public List<Set<Coordinate>> getPawnsLines();
	
	
	
}

package model.state;

import java.util.List;
import java.util.Map;
import java.util.Set;

import hexagonalCoordinate.Coordinate;
import model.Team;
import model.action.*;

public interface IState {
	public IState move(Move move);
	public IState removeLine(RemoveLine removeLine);
	public Set<Coordinate> availableMoves(Coordinate from);
	//public Map<Coordinate, Token> board();
	public Map<Team, List<Coordinate>> rings();
	public List<Set<Coordinate>> lines();
	public Team turn();
	public List<Set<Coordinate>> getPawnsLines();
}

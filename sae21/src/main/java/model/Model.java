package model;

import java.util.List;
import java.util.Map;
import java.util.Set;

import coordinate.Coordinate;
import coordinate.Direction;
import coordinate.Mode;
import coordinate.Point;
import model.tokens.Token;

public class Model {

// git checkout state
// git pull
// git merge state	

	private IState currentState;

	public Model(IState currentState) {
		this.currentState = currentState;
	}

	public void setCurrentState(IState currentState) {
		this.currentState = currentState;
	}

	public Set<Coordinate> movesFrom(Coordinate from) {
	    return currentState.movesFrom(from);
	}
	
	public void moveRing(Coordinate from, Coordinate to) {
	    currentState.moveRing(from, to);
	}
    
    public List<Set<Coordinate>> getPawnLines () {
    	return null;
    }
    
    public void removeLine (Set<Coordinate> line, Coordinate ring) {
    	
    }
    
    public Map<Coordinate, Token> getBoard() {
        return currentState.getBoard();
    }
    
    public Token getTokenAt(Coordinate c) {
    	return null;
    }
    
    public boolean isInField (Coordinate c) {
    	return false;
    }
    
    public List<Coordinate> getRings(Team team){
    	return null;
    }
    
    public List<Coordinate> getPawn(Team team){
    	return null;
    }
    
    public Team getTurn() {
    	return null;
    }
    
	public IState getCurrentState() {
		return currentState;
	}
	
}

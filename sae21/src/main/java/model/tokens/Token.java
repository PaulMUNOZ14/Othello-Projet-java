package model.tokens;

import model.Team;

public abstract class Token {

    protected Team team;

    public Token(Team team) {
        this.team = team;
    }
    
    public Team getTeam() {
    	return team;
    }
    
    public void setTeam(Team team) {
    	this.team = team;
    }
    
    public abstract String charRepr();
    
    public abstract Token clone();
    
}
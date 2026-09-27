package model.tokens;

import model.Team;

public abstract class Token {

    protected Team team;

    /**
     * Constructeur
     * @param team l'équipe choisie
     */
    public Token(Team team) {
        this.team = team;
    }
    
    /**
     * Get team
     * @return Team
     */
    public Team getTeam() {
    	return team;
    }
    
    /**
     * Set team
     * @param team l'équipe à set
     */
    public void setTeam(Team team) {
    	this.team = team;
    }
    
    public abstract String charRepr();
    
    public abstract Token clone();
    
}
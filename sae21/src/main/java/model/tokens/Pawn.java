package model.tokens;

import model.Team;

public class Pawn extends Token{

	/**
	 * Constructeur
	 * @param color la couleur du pion
	 */
	public Pawn(Team color) {
		super(color);
	}

	/**
	 * Fonction qui permet de changer d'équipe
	 */
	public void changeTeam() {
        if (team == Team.WHITE) {
            team = Team.BLACK;
        } else {
            team = Team.WHITE;
        }
    }

	/**
	 * Fonction qui permet de cloner un pion
	 */
    @Override
    public Token clone() {
        return new Pawn(team);
    }

    /**
     * Fonction qui permet de dire si c'est un pawn
     */
    @Override
    public String charRepr() {
        return team == Team.WHITE ? "P" : "P";
    }
}

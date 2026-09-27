package model.tokens;

import model.Team;

public class Ring extends Token{

	/**
	 * Constructeur
	 * @param color la couleur de l'anneau
	 */
	public Ring(Team color) {
		super(color);
	}

	/**
	 * Fonction qui permet de cloner un anneau
	 */
    @Override
    public Token clone() {
        return new Ring(team);
    }

    /**
     * Fonction qui permet de dire que c'est un anneau
     */
    @Override
    public String charRepr() {
        return team == Team.WHITE ? "R" : "R";
    }

}

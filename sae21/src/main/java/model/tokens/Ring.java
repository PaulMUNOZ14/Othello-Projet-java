package model.tokens;

import model.Team;

public class Ring extends Token{

	public Ring(Team color) {
		super(color);
	}

    @Override
    public Token clone() {
        return new Ring(team);
    }

    @Override
    public String charRepr() {
        return team == Team.WHITE ? "R" : "R";
    }

}

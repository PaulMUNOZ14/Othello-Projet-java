package model.tokens;

import model.Team;

public class Pawn extends Token{

	public Pawn(Team color) {
		super(color);
	}

	public void changeTeam() {
        if (team == Team.WHITE) {
            team = Team.BLACK;
        } else {
            team = Team.WHITE;
        }
    }

    @Override
    public Token clone() {
        return new Pawn(team);
    }

    @Override
    public String charRepr() {
        return team == Team.WHITE ? "P" : "P";
    }
}

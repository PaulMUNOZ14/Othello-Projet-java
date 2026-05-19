package model;

import java.awt.Color;

public enum Team {
	BLACK(Color.BLACK),
	WHITE(Color.WHITE);
	
	private Color color;
	
	
	private Team(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	}
	
	public Team other() {
		return Team.values()[(ordinal()+1)%2];
	}
}

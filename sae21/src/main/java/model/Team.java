package model;

import java.awt.Color;

public enum Team {
	BLACK(Color.BLACK),
	WHITE(Color.WHITE);
	
	private Color color;
	
	/**
	 * Constructeur
	 * @param color la couleur
	 */
	private Team(Color color) {
		this.color = color;
	}

	/**
	 * Get color
	 * @return la couleur
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * Obtenir la team adverse
	 * @return la team adverse
	 */
	public Team other() {
		return Team.values()[(ordinal()+1)%2];
	}
}

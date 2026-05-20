package model.action;

import coordinate.Coordinate;

public class Move extends Action {
    
    private Coordinate from;
    private Coordinate to;

    /**
     * Constructeur
     * @param from d'où vient le mouvement
     * @param to d'où il va
     */
    public Move(Coordinate from, Coordinate to) {
        this.from = from;
        this.to = to;
    }

    /**
     * Obtenir d'où vient le mouvement
     * @return les coordonnées du mouvement
     */
    public Coordinate getFrom() {
        return from;
    }

    /**
     * Définir d'où vient le mouvement
     */
    public void setFrom(Coordinate from) {
        this.from = from;
    }

    /**
     * Obtenir d'où va le mouvement
     * @return les coordonnées du mouvement
     */
    public Coordinate getTo() {
        return to;
    }

    /**
     * Définir d'où va le mouvement
     * @param to d'où va le mouvement
     */
    public void setTo(Coordinate to) {
        this.to = to;
    }
}
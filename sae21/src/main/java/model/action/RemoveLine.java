package model.action;

import java.util.Set;
import coordinate.Coordinate;

public class RemoveLine extends Action {
    
    private Set<Coordinate> line;
    private Coordinate ring;

    /**
     * Fonction qui sert à retirer une ligne et un anneau
     * @param line la ligne à retirer
     * @param ring l'anneau à retirer
     */
    public RemoveLine(Set<Coordinate> line, Coordinate ring) {
        this.line = line;
        this.ring = ring;
    }

    /**
     * Permet d'obtenir une ligne
     * @return la ligne
     */
    public Set<Coordinate> getLine() {
        return line;
    }

    /**
     * Permet de définir une ligne
     * @param line la ligne
     */
    public void setLine(Set<Coordinate> line) {
        this.line = line;
    }

    /**
     * permet d'obtenir l'anneau
     * @return l'anneau
     */
    public Coordinate getRing() {
        return ring;
    }

    /**
     * permet de définir l'anneau
     * @param ring l'anneau
     */
    public void setRing(Coordinate ring) {
        this.ring = ring;
    }
}

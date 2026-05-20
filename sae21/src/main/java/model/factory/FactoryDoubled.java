package model.factory;

import coordinate.Coordinate;
import coordinate.CoordinateDoubled;
import model.state.IState;
import model.state.State;
import model.tokens.Pawn;
import model.tokens.Ring;
import model.tokens.Token;
import model.Team;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Fabrique concrète pour la création de plateaux utilisant les coordonnées doublées.
 */
public class FactoryDoubled implements IFactory {

    @Override
    public IState emptyState() {
        return new State(new HashMap<>(), Team.WHITE, List.of());
    }

    public Map<Coordinate, Token> buildBaseBoard() {
    	Map<Coordinate, Token> board = new HashMap<>();

        // Tableau définissant la colonne de départ (min) et de fin (max) pour chaque ligne de 0 à 10
        // indices : [colonne_min, colonne_max]
        int[][] bounds = {
            {6, 12},  // Ligne 0
            {3, 15},  // Ligne 1
            {2, 16},  // Ligne 2
            {1, 17},  // Ligne 3
            {0, 18},  // Ligne 4 (Milieu haut)
            {1, 17},  // Ligne 5 (Centre exact)
            {0, 18},  // Ligne 6 (Milieu bas)
            {1, 17},  // Ligne 7
            {2, 16},  // Ligne 8
            {3, 15},  // Ligne 9
            {6, 12}   // Ligne 10
        };

        // Parcours de toutes les lignes du plateau
        for (int r = 0; r < bounds.length; r++) {
            int colMin = bounds[r][0];
            int colMax = bounds[r][1];

            // On avance de 2 en 2 pour respecter le motif de l'image
            for (int c = colMin; c <= colMax; c += 2) {
                board.put(new CoordinateDoubled(r, c), null);
            }
        }

        return board;
    }

    @Override
    public IState stateForWhiteLineTest() {
        State s = new State(buildBaseBoard(), Team.WHITE, List.of());
        
        s = s.toggleToken(new CoordinateDoubled(5, 1), Pawn.class, Team.WHITE);
        s = s.toggleToken(new CoordinateDoubled(5, 3), Pawn.class, Team.WHITE);
        s = s.toggleToken(new CoordinateDoubled(5, 5), Pawn.class, Team.WHITE);
        s = s.toggleToken(new CoordinateDoubled(5, 7), Pawn.class, Team.WHITE);
        s = s.toggleToken(new CoordinateDoubled(5, 9), Pawn.class, Team.WHITE);
        
        return s;
    }

    @Override
    public IState stateForBlackLineTest() {
        State s = new State(buildBaseBoard(), Team.BLACK, List.of());
        
        s = s.toggleToken(new CoordinateDoubled(5, 1), Pawn.class, Team.BLACK);
        s = s.toggleToken(new CoordinateDoubled(5, 3), Pawn.class, Team.BLACK);
        s = s.toggleToken(new CoordinateDoubled(5, 5), Pawn.class, Team.BLACK);
        s = s.toggleToken(new CoordinateDoubled(5, 7), Pawn.class, Team.BLACK);
        s = s.toggleToken(new CoordinateDoubled(5, 9), Pawn.class, Team.BLACK);
        
        return s;
    }

    @Override
    public IState testState() {
        State s = new State(buildBaseBoard(), Team.WHITE, List.of());
        
        s = s.toggleToken(new CoordinateDoubled(5, 9), Ring.class, Team.WHITE);
        s = s.toggleToken(new CoordinateDoubled(6, 10), Pawn.class, Team.BLACK);
        
        return s;
    }

    @Override
    public IState doubleLineStateTest() {
        State s = new State(buildBaseBoard(), Team.WHITE, List.of());
        
        s = s.toggleToken(new CoordinateDoubled(4, 2), Pawn.class, Team.WHITE);
        s = s.toggleToken(new CoordinateDoubled(4, 4), Pawn.class, Team.WHITE);
        s = s.toggleToken(new CoordinateDoubled(4, 6), Pawn.class, Team.WHITE);
        s = s.toggleToken(new CoordinateDoubled(4, 8), Pawn.class, Team.WHITE);
        s = s.toggleToken(new CoordinateDoubled(4, 10), Pawn.class, Team.WHITE);
        
        s = s.toggleToken(new CoordinateDoubled(6, 2), Pawn.class, Team.WHITE);
        s = s.toggleToken(new CoordinateDoubled(6, 4), Pawn.class, Team.WHITE);
        s = s.toggleToken(new CoordinateDoubled(6, 6), Pawn.class, Team.WHITE);
        s = s.toggleToken(new CoordinateDoubled(6, 8), Pawn.class, Team.WHITE);
        s = s.toggleToken(new CoordinateDoubled(6, 10), Pawn.class, Team.WHITE);
        
        return s;
    }
}
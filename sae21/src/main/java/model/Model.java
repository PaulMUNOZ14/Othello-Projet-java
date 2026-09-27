package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import coordinate.Coordinate;
import coordinate.DifferentAxisException;

import model.action.Move;
import model.action.RemoveLine;

import model.state.IState;
import model.tokens.Pawn;
import model.tokens.Token;

public class Model {

    private IState currentState;

    /**
     * Constructeur
     * @param currentState l'état du jeu
     */
    public Model(IState currentState) {
        this.currentState = currentState;
    }

    /**
     * Fonction qui sert à définir le state
     * @param currentState le state
     */
    public void setCurrentState(IState currentState) {
        this.currentState = currentState;
    }

    /**
     * fonction qui sert à stocker le mouvement d'où viens la pièce
     * @param from les coordonnées avant le mouvement de la pièce
     * @return les coordonnées des mouvements possibles
     */
    public Set<Coordinate> movesFrom(Coordinate from) {
        return currentState.availableMoves(from);
    }

    /**
     * Fonction qui sert à déplacer un anneau
     * @param from d'ou il vient
     * @param to ou il va
     */
    public void moveRing(Coordinate from, Coordinate to) {
        try {
            currentState = currentState.move(new Move(from, to));
        } catch (DifferentAxisException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Fonction qui récupère les lignes de pions
     * @return les lignes de pions
     */
    public List<Set<Coordinate>> getPawnLines() {
        return currentState.getPawnsLines();
    }

    /**
     * Fonction qui supprime des lignes
     * @param line la ligne à retirer
     * @param ring l'anneau à retirer
     */
    public void removeLine(Set<Coordinate> line, Coordinate ring) {
        if (line == null || ring == null) return;
        currentState = currentState.removeLine(new RemoveLine(line, ring));
    }

    /**
     * Fonction qui sert à récupérer le board
     * @return le board
     */
    public Map<Coordinate, Token> getBoard() {
        Map<Coordinate, Token> board = currentState.board();
        return board == null ? Map.of() : board;
    }

    /**
     * Fonction qui sert à récupérer un token à une coordonnée
     * @param c la coordonnée
     * @return le token
     */
    public Token getTokenAt(Coordinate c) {
        if (c == null) return null;
        Map<Coordinate, Token> board = currentState.board();
        if (board == null) return null;
        return board.get(c);
    }

    /**
     * Fonction qui  sert à donner si la coordonnée est dans le tableau ou pas
     * @param c la coordonnée
     * @return vrai ou faux
     */
    public boolean isInField(Coordinate c) {
        if (c == null) return false;
        Map<Coordinate, Token> board = currentState.board();
        if (board == null) return false;
        return board.containsKey(c);
    }

    /**
     * Fonction qui sert à récupérer les anneaux d'une équipe
     * @param team l'équipe
     * @return les anneaux
     */
    public List<Coordinate> getRings(Team team) {
        Map<Team, List<Coordinate>> rings = currentState.rings();
        if (rings == null) return List.of();
        return rings.getOrDefault(team, List.of());
    }
    
    /**
     * Fonction qui sert à récupérer les pions d'une équipe
     * @param team l'équipe
     * @return les pions
     */
    public List<Coordinate> getPawn(Team team) {

        List<Coordinate> pawns = new ArrayList<>();

        Map<Coordinate, Token> board = currentState.board();

        if (board == null) return pawns;

        for (Coordinate c : board.keySet()) {

            Token t = board.get(c);

            if (t instanceof Pawn &&
                t.getTeam() == team) {

                pawns.add(c);
            }
        }

        return pawns;
    }

    /**
     * Fonction qui sert à obtenir le tour du joueur
     * @return à qui est le tour
     */
    public Team getTurn() {
        return currentState.turn();
    }

    /**
     * Obtenir l'état du jeu
     * @return le state
     */
    public IState getCurrentState() {
        return currentState;
    }
}
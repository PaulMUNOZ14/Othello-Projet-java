package sae21.testStage;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import coordinate.Coordinate;
import coordinate.CoordinateCube;
import model.Team;
import model.action.RemoveLine;
import model.state.*;
import model.tokens.Pawn;
import model.tokens.Ring;
import model.tokens.Token;

class RemoveLineTest {

	private Map<Coordinate, Token> board;
    private int n = 10;

    // Coordonnées de test pour former une ligne de 5 et un anneau isolé
    private final CoordinateCube r1 = new CoordinateCube(0, 0, 0);
    private final CoordinateCube p1 = new CoordinateCube(1, 0, -1);
    private final CoordinateCube p2 = new CoordinateCube(2, 0, -2);
    private final CoordinateCube p3 = new CoordinateCube(3, 0, -3);
    private final CoordinateCube p4 = new CoordinateCube(4, 0, -4);
    private final CoordinateCube p5 = new CoordinateCube(5, 0, -5);

    @BeforeEach
    public void setUp() {
        // Initialisation d'un plateau vide via ta méthode
        board = State.genereTab(n);
    }

    @Test
    public void testRemoveLine_Succes() {
        // Configuration : On place 5 pions blancs alignés et 1 anneau blanc
        board.put(r1, new Ring(Team.WHITE));
        board.put(p1, new Pawn(Team.WHITE));
        board.put(p2, new Pawn(Team.WHITE));
        board.put(p3, new Pawn(Team.WHITE));
        board.put(p4, new Pawn(Team.WHITE));
        board.put(p5, new Pawn(Team.WHITE));

        IState state = new State(board, Team.WHITE, new ArrayList<>());
        
        // Création de l'action de retrait (Adapte le constructeur selon ta classe RemoveLine)
        Set<Coordinate> ligneValide = Set.of(p1, p2, p3, p4, p5);
        RemoveLine action = new RemoveLine(ligneValide, r1);

        // Exécution
        IState prochainEtat = state.removeLine(action);
        Map<Coordinate, Token> nouveauBoard = prochainEtat.board();

        // Vérifications : Les cases doivent être vidées (null), mais TOUJOURS présentes dans la Map
        assertNull(nouveauBoard.get(r1), "L'anneau devrait être retiré (devenu null)");
        assertNull(nouveauBoard.get(p1), "Le pion 1 devrait être retiré (devenu null)");
        assertNull(nouveauBoard.get(p5), "Le pion 5 devrait être retiré (devenu null)");
        
        // /!\ Si tu utilises ma version corrigée avec .put(co, null), ce test doit passer :
        assertTrue(nouveauBoard.containsKey(p1), "La coordonnée doit rester dans la Map du plateau");
    }

    @Test
    public void testRemoveLine_Erreur_TailleLigneInvalide() {
        board.put(r1, new Ring(Team.WHITE));
        board.put(p1, new Pawn(Team.WHITE));
        board.put(p2, new Pawn(Team.WHITE));

        IState state = new State(board, Team.WHITE, new ArrayList<>());
        
        // Ligne de seulement 2 pions au lieu de 5
        Set<Coordinate> ligneIncomplete = Set.of(p1, p2);
        RemoveLine action = new RemoveLine(ligneIncomplete, r1);

        assertThrows(RuntimeException.class, () -> {
            state.removeLine(action);
        }, "Devrait lever une RuntimeException car la ligne ne fait pas 5 pions");
    }

    @Test
    public void testRemoveLine_Erreur_PasUnAnneauARetirer() {
        // On met un pion à la place de l'anneau demandé
        board.put(r1, new Pawn(Team.WHITE)); 
        board.put(p1, new Pawn(Team.WHITE));
        board.put(p2, new Pawn(Team.WHITE));
        board.put(p3, new Pawn(Team.WHITE));
        board.put(p4, new Pawn(Team.WHITE));
        board.put(p5, new Pawn(Team.WHITE));

        IState state = new State(board, Team.WHITE, new ArrayList<>());
        RemoveLine action = new RemoveLine(Set.of(p1, p2, p3, p4, p5), r1);

        assertThrows(RuntimeException.class, () -> {
            state.removeLine(action);
        }, "Devrait lever une RuntimeException car la coordonnée de l'anneau contient un Pion");
    }

    @Test
    public void testRemoveLine_Erreur_LigneNonUniforme() {
        board.put(r1, new Ring(Team.WHITE));
        // Ligne mixte : 4 Blancs et 1 Noir
        board.put(p1, new Pawn(Team.WHITE));
        board.put(p2, new Pawn(Team.WHITE));
        board.put(p3, new Pawn(Team.WHITE));
        board.put(p4, new Pawn(Team.WHITE));
        board.put(p5, new Pawn(Team.BLACK)); // <-- Couleur différente !

        IState state = new State(board, Team.WHITE, new ArrayList<>());
        RemoveLine action = new RemoveLine(Set.of(p1, p2, p3, p4, p5), r1);

        assertThrows(RuntimeException.class, () -> {
            state.removeLine(action);
        }, "Devrait lever une RuntimeException car la ligne contient plusieurs couleurs");
    }

    @Test
    public void testRemoveLine_Erreur_LigneContientUnAnneau() {
        board.put(r1, new Ring(Team.WHITE));
        board.put(p1, new Pawn(Team.WHITE));
        board.put(p2, new Pawn(Team.WHITE));
        board.put(p3, new Ring(Team.WHITE)); // <-- Un anneau au milieu de la ligne !
        board.put(p4, new Pawn(Team.WHITE));
        board.put(p5, new Pawn(Team.WHITE));

        IState state = new State(board, Team.WHITE, new ArrayList<>());
        RemoveLine action = new RemoveLine(Set.of(p1, p2, p3, p4, p5), r1);

        assertThrows(RuntimeException.class, () -> {
            state.removeLine(action);
        }, "Devrait lever une RuntimeException car la ligne ne doit contenir que des pions");
    }

}

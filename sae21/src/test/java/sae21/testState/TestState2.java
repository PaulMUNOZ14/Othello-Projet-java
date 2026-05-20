package sae21.testState;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.sun.jdi.connect.Connector.Argument;

import coordinate.Coordinate;
import coordinate.CoordinateCube;
import coordinate.DifferentAxisException;
import coordinate.Direction;
import coordinate.Mode;
import coordinate.Point;
import model.Team;
import model.action.Move;
import model.state.IState;
import model.state.State;
import model.tokens.Pawn;
import model.tokens.Ring;
import model.tokens.Token;

class TestState2 {
		
	private Map<Coordinate, Token> board;
    private int n = 5; // Taille standard du plateau YINSH

    @BeforeEach
    public void setUp() {
        // Initialisation d'un plateau vide avant chaque test
        board = State.genereTab(n); 
    }

    @Test
    public void testGenereTab_ContientOrigine() {
        CoordinateCube centre = new CoordinateCube(0, 0, 0);
        assertTrue(board.containsKey(centre), "Le centre du plateau devrait exister");
        assertNull(board.get(centre), "Le centre devrait être vide au départ");
    }

    @Test
    public void testAvailableMoves_CaseVideSansObstacle() {
        CoordinateCube depart = new CoordinateCube(0, 0, 0);
        Ring anneauBlanc = new Ring(Team.WHITE);
        board.put(depart, anneauBlanc);

        // Instanciation de State avec l'ArrayList vide pour le 3ème paramètre
        IState state = new State(board, Team.WHITE, List.of());
        Set<Coordinate> coupsPossibles = state.availableMoves(depart);

        assertFalse(coupsPossibles.isEmpty(), "L'anneau devrait avoir des mouvements disponibles");
        
        // Vérification sur l'axe EST
        CoordinateCube destinationEst = new CoordinateCube(1, 0, -1);
        assertTrue(coupsPossibles.contains(destinationEst), "L'anneau devrait pouvoir aller à l'Est");
    }

    @Test
    public void testAvailableMoves_BloqueParUnAnneau() {
        CoordinateCube depart = new CoordinateCube(0, 0, 0);
        CoordinateCube voisin = new CoordinateCube(1, 0, -1); 
        CoordinateCube derriereVoisin = new CoordinateCube(2, 0, -2);

        board.put(depart, new Ring(Team.WHITE));
        board.put(voisin, new Ring(Team.BLACK)); // Un autre anneau bloque la route

        IState state = new State(board, Team.WHITE, List.of());
        Set<Coordinate> coupsPossibles = state.availableMoves(depart);

        // Un anneau ne peut ni s'arrêter sur un autre anneau, ni le sauter
        assertFalse(coupsPossibles.contains(voisin), "On ne peut pas aller sur un autre anneau");
        assertFalse(coupsPossibles.contains(derriereVoisin), "On ne peut pas sauter un autre anneau");
    }

    @Test
    public void testMove_DeplacementEtRetournementPion() throws DifferentAxisException {
        CoordinateCube depart = new CoordinateCube(0, 0, 0);
        CoordinateCube casePion = new CoordinateCube(1, 0, -1);
        CoordinateCube arrivee = new CoordinateCube(2, 0, -2);

        Ring anneauBlanc = new Ring(Team.WHITE);
        board.put(depart, anneauBlanc);
        board.put(casePion, new Pawn(Team.BLACK));

        IState state = new State(board, Team.WHITE, List.of());
        Move coup = new Move(depart, arrivee);

        // Exécution du coup
        IState prochainEtat = state.move(coup);
        Map<Coordinate, Token> nouveauBoard = prochainEtat.board();

        // 1. L'anneau blanc est arrivé à destination
        assertEquals(anneauBlanc, nouveauBoard.get(arrivee), "L'anneau blanc doit être à l'arrivée");
        
        // 2. L'anneau a laissé un pion de sa couleur au départ
        assertTrue(nouveauBoard.get(depart) instanceof Pawn, "Le départ doit contenir un pion");
        assertEquals(Team.WHITE, nouveauBoard.get(depart).getTeam(), "Le pion de départ doit être Blanc");

        // 3. Le pion noir sauté a été retourné en pion blanc
        assertTrue(nouveauBoard.get(casePion) instanceof Pawn, "La case intermédiaire doit toujours être un pion");
        assertEquals(Team.WHITE, nouveauBoard.get(casePion).getTeam(), "Le pion sauté aurait dû devenir Blanc");
    }

    @Test
    public void testMove_CoupInvalide_LanceException() {
        CoordinateCube depart = new CoordinateCube(0, 0, 0);
        CoordinateCube arriveeInvalide = new CoordinateCube(8, 0, -8); 

        board.put(depart, new Ring(Team.WHITE));
        IState state = new State(board, Team.WHITE, List.of());
        Move coupInvalide = new Move(depart, arriveeInvalide);

        // Le move doit lever une exception si le coup est hors plateau
        assertThrows(IndexOutOfBoundsException.class, () -> {
            state.move(coupInvalide);
        }, "Un coup hors plateau doit lever une IndexOutOfBoundsException");
    }
}

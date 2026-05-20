package sae21.testState;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import coordinate.Coordinate;
import coordinate.CoordinateDoubled;
import model.Team;
import model.factory.FactoryDoubled;
import model.state.State;
import model.tokens.Pawn;
import model.tokens.Ring;
import model.tokens.Token;

class ToggleTokenTest {

    private Map<Coordinate, Token> board;
    private Coordinate coordCentre;
    private Coordinate coordVoisine;

    @BeforeEach
    public void setUp() {
        // On génère la géométrie de base (toutes les cases valides associées à null)
        FactoryDoubled facto = new FactoryDoubled();
        board = facto.buildBaseBoard();
        
        // Coordonnées valides correspondant au plateau de l'image
        coordCentre = new CoordinateDoubled(5, 9);
        coordVoisine = new CoordinateDoubled(5, 11);
    }

    @Test
    void testToggleToken_addPawn() {
        // Le centre (5, 9) est initialement vide (null) dans la map
        State state = new State(board, Team.WHITE, List.of());

        var newState = state.toggleToken(coordCentre, Pawn.class, Team.WHITE);

        assertNotNull(newState.board().get(coordCentre), "Un pion aurait dû être ajouté");
        assertTrue(newState.board().get(coordCentre) instanceof Pawn);
        assertEquals(Team.WHITE, newState.board().get(coordCentre).getTeam());
    }

    @Test
    void testToggleToken_removeSamePawn() {
        // On pré-remplit la case avec un pion blanc
        board.put(coordCentre, new Pawn(Team.WHITE));
        State state = new State(board, Team.WHITE, List.of());

        // Ajouter le même pion de la même équipe doit le retirer (remise à null)
        var newState = state.toggleToken(coordCentre, Pawn.class, Team.WHITE);

        assertNull(newState.board().get(coordCentre), "Le pion identique aurait dû être retiré (remis à null)");
    }

    @Test
    void testToggleToken_differentTeam_doesNotRemove() {
        // La case contient un pion NOIR
        board.put(coordCentre, new Pawn(Team.BLACK));
        State state = new State(board, Team.WHITE, List.of());

        // On bascule un pion BLANC sur cette même case : cela doit écraser/remplacer le noir
        var newState = state.toggleToken(coordCentre, Pawn.class, Team.WHITE);

        assertNotNull(newState.board().get(coordCentre));
        assertTrue(newState.board().get(coordCentre) instanceof Pawn);
        assertEquals(Team.WHITE, newState.board().get(coordCentre).getTeam(), "Le pion noir doit être remplacé par un blanc");
    }

    @Test
    void testToggleToken_ringCreation() {
        State state = new State(board, Team.WHITE, List.of());

        var newState = state.toggleToken(coordVoisine, Ring.class, Team.WHITE);

        assertNotNull(newState.board().get(coordVoisine));
        assertTrue(newState.board().get(coordVoisine) instanceof Ring, "La case doit maintenant contenir un Anneau");
    }

    @Test
    void testToggleToken_replacePawnDifferentType() {
        // La case contient un PION blanc
        board.put(coordCentre, new Pawn(Team.WHITE));
        State state = new State(board, Team.WHITE, List.of());

        // On bascule un ANNEAU blanc : la nature du token change, il doit donc être remplacé
        var newState = state.toggleToken(coordCentre, Ring.class, Team.WHITE);

        assertNotNull(newState.board().get(coordCentre));
        assertTrue(newState.board().get(coordCentre) instanceof Ring, "Le pion a été remplacé par un anneau");
        assertEquals(Team.WHITE, newState.board().get(coordCentre).getTeam());
    }
}
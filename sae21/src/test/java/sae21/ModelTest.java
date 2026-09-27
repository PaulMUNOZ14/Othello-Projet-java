package sae21;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import model.Model;
import model.Team;
import model.state.IState;
import model.tokens.Token;
import model.tokens.Ring;
import coordinate.Coordinate;
import coordinate.CoordinateCube;

// Importation de la factory
import model.factory.IFactory;
import model.factory.FactoryCube;

public class ModelTest {

    /**
     * Méthode utilitaire pour obtenir une factory propre pour chaque test.
     */
    private IFactory getFactory() {
        return new FactoryCube();
    }

    @Test
    void testConstructor() {
        IState state = getFactory().emptyState();
        Model model = new Model(state);
        assertEquals(state, model.getCurrentState());
    }

    @Test
    void testSetCurrentState() {
        IState state1 = getFactory().emptyState();
        IState state2 = getFactory().emptyState();
        Model model = new Model(state1);
        
        model.setCurrentState(state2);
        assertEquals(state2, model.getCurrentState());
    }

    @Test
    void testMovesFromWithNull() {
        Model model = new Model(getFactory().emptyState());
        // availableMoves(null) renvoie généralement un ensemble vide ou lève une exception 
        // selon votre code, on s'assure ici de tester son comportement sans crash non géré
        assertThrows(NullPointerException.class, () -> model.movesFrom(null));
    }

    @Test
    void testGetPawnLinesEmpty() {
        Model model = new Model(getFactory().emptyState());
        List<Set<Coordinate>> result = model.getPawnLines();
        
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetPawnLinesWithExistingLines() {
        // Test avec un état pré-configuré contenant une ligne blanche grâce à la factory
        Model model = new Model(getFactory().stateForWhiteLineTest());
        List<Set<Coordinate>> result = model.getPawnLines();
        
        assertNotNull(result);
        assertFalse(result.isEmpty(), "Devrait détecter la ligne blanche générée par la factory");
    }

    @Test
    void testGetBoardNotNullAndNotEmpty() {
        Model model = new Model(getFactory().emptyState());
        Map<Coordinate, Token> board = model.getBoard();
        
        assertNotNull(board);
        // Le plateau contient toutes les cases initialisées à null, la map n'est donc pas vide
        assertFalse(board.isEmpty()); 
    }

    @Test
    void testGetTokenAt() {
        Model model = new Model(getFactory().emptyState());
        Coordinate center = new CoordinateCube(0, 0, 0);
        
        // Au départ, sur un plateau vide, la case est valide mais ne contient rien
        assertNull(model.getTokenAt(center));
        assertNull(model.getTokenAt(null));
    }

    @Test
    void testIsInField() {
        Model model = new Model(getFactory().emptyState());
        Coordinate inside = new CoordinateCube(0, 0, 0);
        Coordinate outside = new CoordinateCube(99, -99, 0);
        
        assertTrue(model.isInField(inside));
        assertFalse(model.isInField(outside));
        assertFalse(model.isInField(null));
    }

    @Test
    void testGetPawn() {
        Model model = new Model(getFactory().stateForBlackLineTest());
        
        List<Coordinate> blackPawns = model.getPawn(Team.BLACK);
        List<Coordinate> whitePawns = model.getPawn(Team.WHITE);

        assertNotNull(blackPawns);
        assertFalse(blackPawns.isEmpty(), "L'état de test noir doit contenir des pions noirs");
        assertTrue(whitePawns.isEmpty(), "L'état de test noir ne doit pas contenir de pions blancs");
    }

    @Test
    void testGetTurn() {
        Model model = new Model(getFactory().emptyState());
        // Vérifie qu'un tour par défaut est bien défini et n'est pas null
        assertNotNull(model.getTurn());
    }

    @Test
    void testRemoveLineWithNullParameters() {
        Model model = new Model(getFactory().emptyState());
        
        // Doit retourner immédiatement sans lever d'exception grâce aux protections du Model
        assertDoesNotThrow(() -> model.removeLine(null, null));
        assertDoesNotThrow(() -> model.removeLine(new HashSet<>(), null));
    }

    @Test
    void testGetRingsEmpty() {
        Model model = new Model(getFactory().emptyState());
        List<Coordinate> rings = model.getRings(Team.WHITE);

        assertNotNull(rings);
        assertTrue(rings.isEmpty());
    }
}
package factory;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import coordinate.CoordinateDoubled;
import model.Team;
import model.factory.FactoryDoubled;
import model.factory.IFactory;
import model.state.IState;
import model.tokens.Pawn;
import model.tokens.Ring;

public class FactoryDoubleTest {

    @Test
    public void testFactoryDoubleCreation() {
        IFactory factory = new FactoryDoubled();
        
        assertNotNull(factory.emptyState(), "L'état vide ne devrait pas être null");
        assertNotNull(factory.stateForWhiteLineTest(), "L'état de test ne devrait pas être null");
    }
    
    @Test
    void testEmptyStateIsReallyEmptyOfTokens() {
        IFactory factory = new FactoryDoubled();
        IState state = factory.emptyState();

        assertNotNull(state);
        
        // On compte uniquement les cases qui contiennent un vrai jeton (pas null)
        long tokenCount = state.board().values().stream()
                .filter(token -> token != null)
                .count();

        assertEquals(0, tokenCount, "Un plateau vide ne doit contenir aucun jeton");
    }

    @Test
    void testEmptyStateCurrentPlayer() {
        IFactory factory = new FactoryDoubled();
        IState state = factory.emptyState();

        assertEquals(Team.WHITE, state.turn());
    }
    
    @Test
    void testWhiteLineContainsFiveWhitePawns() {
        IFactory factory = new FactoryDoubled();
        IState state = factory.stateForWhiteLineTest();

        // Vérification du nombre total de jetons sur le plateau
        long tokenCount = state.board().values().stream()
                .filter(token -> token != null)
                .count();
        assertEquals(5, tokenCount, "Le plateau devrait contenir exactement 5 pions");

        for (int i = 1; i <= 9; i += 2) {
            CoordinateDoubled c = new CoordinateDoubled(5, i);

            assertNotNull(state.board().get(c), "Le pion à la coordonnée " + c + " ne devrait pas être null");
            assertEquals(Team.WHITE, state.board().get(c).getTeam());
        }
    }
    
    @Test
    void testWhiteLineContainsPawnObjects() {
        IFactory factory = new FactoryDoubled();
        IState state = factory.stateForWhiteLineTest();

        for (int i = 1; i <= 9; i += 2) {
            CoordinateDoubled c = new CoordinateDoubled(5, i);

            assertNotNull(state.board().get(c));
            assertTrue(state.board().get(c) instanceof Pawn);
        }
    }
    
    @Test
    void testWhiteLineStateContainsALine() {
        IFactory factory = new FactoryDoubled();
        IState state = factory.stateForWhiteLineTest();

        assertFalse(state.lines().isEmpty());
    }
    
    @Test
    void testBlackLineContainsFiveBlackPawns() {
        IFactory factory = new FactoryDoubled();
        IState state = factory.stateForBlackLineTest();

        for (int i = 1; i <= 9; i += 2) {
            CoordinateDoubled c = new CoordinateDoubled(5, i);

            assertNotNull(state.board().get(c));
            assertEquals(Team.BLACK, state.board().get(c).getTeam());
        }
    }
    
    @Test
    void testTestStateContainsCorrectTokens() {
        IFactory factory = new FactoryDoubled();
        IState state = factory.testState();

        assertNotNull(state.board().get(new CoordinateDoubled(5, 9)));
        assertTrue(state.board().get(new CoordinateDoubled(5, 9)) instanceof Ring);
        
        assertNotNull(state.board().get(new CoordinateDoubled(6, 10)));
        assertTrue(state.board().get(new CoordinateDoubled(6, 10)) instanceof Pawn);
    }
    
    @Test
    void testTestStateTeams() {
        IFactory factory = new FactoryDoubled();
        IState state = factory.testState();

        assertNotNull(state.board().get(new CoordinateDoubled(5, 9)));
        assertEquals(Team.WHITE, state.board().get(new CoordinateDoubled(5, 9)).getTeam());

        assertNotNull(state.board().get(new CoordinateDoubled(6, 10)));
        assertEquals(Team.BLACK, state.board().get(new CoordinateDoubled(6, 10)).getTeam());
    }
    
    @Test
    void testDoubleLineStateContainsTwoLines() {
        IFactory factory = new FactoryDoubled();
        IState state = factory.doubleLineStateTest();

        assertEquals(2, state.lines().size());
    }
    
    @Test
    void testDoubleLineStateContainsTenPawns() {
        IFactory factory = new FactoryDoubled();
        IState state = factory.doubleLineStateTest();

        // Correction pour ne compter que les pions actifs
        long tokenCount = state.board().values().stream()
                .filter(token -> token != null)
                .count();

        assertEquals(10, tokenCount, "Le plateau devrait compter uniquement 10 pions actifs");
    }
    
    @Test
    void testDoubleLineAllWhite() {
        IFactory factory = new FactoryDoubled();
        IState state = factory.doubleLineStateTest();

        // On ignore les valeurs null pour ne pas déclencher de NullPointerException
        state.board().values().forEach(token -> {
            if (token != null) {
                assertEquals(Team.WHITE, token.getTeam());
            }
        });
    }
}
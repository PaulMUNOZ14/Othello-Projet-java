package factory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import coordinate.CoordinateCube;
import model.Team;
import model.factory.FactoryCube;
import model.factory.IFactory;
import model.state.IState;
import model.tokens.Pawn;
import model.tokens.Ring;

public class FactoryCubeTest {

    @Test
    public void testFactoryCubeCreation() {
        // on instancie la factory
        IFactory factory = new FactoryCube();
        
        // test : l'état vide n'est pas null
        assertNotNull(factory.emptyState(), "L'état vide ne devrait pas être null");
        
        // test : L'état pour le test de ligne blanche n'est pas null
        IState lineTest = factory.stateForWhiteLineTest();
        assertNotNull(lineTest, "L'état de test de ligne ne devrait pas être null");
        
    }
    
    @Test
    void testEmptyStateIsReallyEmpty() {
        IFactory factory = new FactoryCube();

        IState state = factory.emptyState();

        assertNotNull(state);
        assertTrue(state.board().isEmpty());
    }

    @Test
    void testEmptyStateCurrentPlayer() {
        IFactory factory = new FactoryCube();

        IState state = factory.emptyState();

        assertEquals(Team.WHITE, state.turn());
    }

    @Test
    void testWhiteLineContainsFiveWhitePawns() {
        IFactory factory = new FactoryCube();

        IState state = factory.stateForWhiteLineTest();

        assertEquals(5, state.board().size());

        for (int i = 0; i < 5; i++) {

            CoordinateCube c = new CoordinateCube(i, -i, 0);

            assertNotNull(state.board().get(c));

            assertEquals(
                    Team.WHITE,
                    state.board().get(c).getTeam()
            );
        }
    }

    @Test
    void testWhiteLineContainsPawnObjects() {
        IFactory factory = new FactoryCube();

        IState state = factory.stateForWhiteLineTest();

        for (int i = 0; i < 5; i++) {

            CoordinateCube c = new CoordinateCube(i, -i, 0);

            assertTrue(state.board().get(c) instanceof Pawn);
        }
    }

    @Test
    void testWhiteLineStateContainsALine() {
        IFactory factory = new FactoryCube();

        IState state = factory.stateForWhiteLineTest();

        assertFalse(state.lines().isEmpty());
    }

    @Test
    void testBlackLineContainsFiveBlackPawns() {
        IFactory factory = new FactoryCube();

        IState state = factory.stateForBlackLineTest();

        for (int i = 0; i < 5; i++) {

            CoordinateCube c = new CoordinateCube(i, -i, 0);

            assertEquals(
                    Team.BLACK,
                    state.board().get(c).getTeam()
            );
        }
    }

    @Test
    void testTestStateContainsCorrectTokens() {
        IFactory factory = new FactoryCube();

        IState state = factory.testState();

        assertTrue(
                state.board().get(new CoordinateCube(0,0,0))
                        instanceof Ring
        );

        assertTrue(
                state.board().get(new CoordinateCube(1,0,-1))
                        instanceof Pawn
        );
    }

    @Test
    void testTestStateTeams() {
        IFactory factory = new FactoryCube();

        IState state = factory.testState();

        assertEquals(
                Team.WHITE,
                state.board().get(new CoordinateCube(0,0,0)).getTeam()
        );

        assertEquals(
                Team.BLACK,
                state.board().get(new CoordinateCube(1,0,-1)).getTeam()
        );
    }

    @Test
    void testDoubleLineStateContainsTwoLines() {
        IFactory factory = new FactoryCube();

        IState state = factory.doubleLineStateTest();

        assertEquals(2, state.lines().size());
    }

    @Test
    void testDoubleLineStateContainsTenPawns() {
        IFactory factory = new FactoryCube();

        IState state = factory.doubleLineStateTest();

        assertEquals(10, state.board().size());
    }

    @Test
    void testDoubleLineAllWhite() {
        IFactory factory = new FactoryCube();

        IState state = factory.doubleLineStateTest();

        state.board().values().forEach(token ->
                assertEquals(Team.WHITE, token.getTeam())
        );
    }

    @Test
    void testToggleTokenReturnsNewState() {
        IFactory factory = new FactoryCube();

        IState original = factory.emptyState();

        IState modified = original.toggleToken(
                new CoordinateCube(0,0,0),
                Pawn.class,
                Team.WHITE
        );

        assertNotEquals(original, modified);
    }
    
}


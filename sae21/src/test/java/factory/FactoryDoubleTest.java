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
    void testEmptyStateIsReallyEmpty() {
        IFactory factory = new FactoryDoubled();

        IState state = factory.emptyState();

        assertNotNull(state);
        assertTrue(state.board().isEmpty());
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

        assertEquals(5, state.board().size());

        for (int i = 1; i <= 9; i += 2) {
            CoordinateDoubled c = new CoordinateDoubled(5, i);

            assertNotNull(state.board().get(c));
            assertEquals(Team.WHITE, state.board().get(c).getTeam());
        }
    }
    
    @Test
    void testWhiteLineContainsPawnObjects() {
        IFactory factory = new FactoryDoubled();

        IState state = factory.stateForWhiteLineTest();

        for (int i = 1; i <= 9; i += 2) {
            CoordinateDoubled c = new CoordinateDoubled(5, i);

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

            assertEquals(Team.BLACK, state.board().get(c).getTeam());
        }
    }
    
    @Test
    void testTestStateContainsCorrectTokens() {
        IFactory factory = new FactoryDoubled();

        IState state = factory.testState();

        assertTrue(state.board().get(new CoordinateDoubled(5, 9)) instanceof Ring);
        assertTrue(state.board().get(new CoordinateDoubled(6, 10)) instanceof Pawn);
    }
    
    @Test
    void testTestStateTeams() {
        IFactory factory = new FactoryDoubled();

        IState state = factory.testState();

        assertEquals(Team.WHITE,
            state.board().get(new CoordinateDoubled(5, 9)).getTeam());

        assertEquals(Team.BLACK,
            state.board().get(new CoordinateDoubled(6, 10)).getTeam());
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

        assertEquals(10, state.board().size());
    }
    
    @Test
    void testDoubleLineAllWhite() {
        IFactory factory = new FactoryDoubled();

        IState state = factory.doubleLineStateTest();

        state.board().values().forEach(token ->
            assertEquals(Team.WHITE, token.getTeam())
        );
    }
}
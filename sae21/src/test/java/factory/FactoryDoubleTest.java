package factory;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import model.factory.FactoryDoubled;
import model.factory.IFactory;
import model.state.IState;

public class FactoryDoubleTest {

    @Test
    public void testFactoryDoubleCreation() {
    	IFactory factory = new FactoryDoubled();
        
        assertNotNull(factory.emptyState(), "L'état vide ne devrait pas être null");
        assertNotNull(factory.stateForWhiteLineTest(), "L'état de test ne devrait pas être null");
    }
}
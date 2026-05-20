package factory;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import model.state.IState;

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
}


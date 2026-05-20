package sae21.testState;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import coordinate.Coordinate;
import coordinate.CoordinateDoubled;
import coordinate.DifferentAxisException;
import model.Team;
import model.action.Move;
import model.factory.FactoryDoubled;
import model.state.IState;
import model.state.State;
import model.tokens.Pawn;
import model.tokens.Ring;
import model.tokens.Token;

class TestState2 {
		
    private Map<Coordinate, Token> board;

    @BeforeEach
    public void setUp() {
        // FactoryDoubled construit un board avec des clés de type CoordinateDoubled
        FactoryDoubled facto = new FactoryDoubled();
        board = facto.buildBaseBoard(); 
    }

    @Test
    public void testGenereTab_ContientCentre() {
        // En coordonnées doublées/offset selon l'image du début : la ligne centrale est la 5
        CoordinateDoubled centre = new CoordinateDoubled(5, 9);
        
        assertTrue(board.containsKey(centre), "Le centre du plateau (5, 9) devrait exister");
        assertNull(board.get(centre), "Le centre devrait être vide (null) au départ");
    }

    @Test
    public void testAvailableMoves_CaseVideSansObstacle() {
        CoordinateDoubled depart = new CoordinateDoubled(5, 9);
        Ring anneauBlanc = new Ring(Team.WHITE);
        board.put(depart, anneauBlanc);

        IState state = new State(board, Team.WHITE, List.of());
        Set<Coordinate> coupsPossibles = state.availableMoves(depart);

        assertFalse(coupsPossibles.isEmpty(), "L'anneau devrait avoir des mouvements disponibles");
        
        // En mode FLAT/POINTY sur cette ligne, le voisin à l'Est avance de 2 colonnes : (5, 11)
        CoordinateDoubled destinationEst = new CoordinateDoubled(5, 11);
        assertTrue(coupsPossibles.contains(destinationEst), "L'anneau devrait pouvoir aller à l'Est (5, 11)");
    }

    @Test
    public void testAvailableMoves_BloqueParUnAnneau() {
        CoordinateDoubled depart = new CoordinateDoubled(5, 9);
        CoordinateDoubled voisin = new CoordinateDoubled(5, 11); 
        CoordinateDoubled derriereVoisin = new CoordinateDoubled(5, 13);

        board.put(depart, new Ring(Team.WHITE));
        board.put(voisin, new Ring(Team.BLACK)); // Un autre anneau bloque le passage

        IState state = new State(board, Team.WHITE, List.of());
        Set<Coordinate> coupsPossibles = state.availableMoves(depart);

        // Règles YINSH : Un anneau ne peut pas s'arrêter sur un autre anneau ni passer par-dessus
        assertFalse(coupsPossibles.contains(voisin), "On ne peut pas s'arrêter sur un autre anneau");
        assertFalse(coupsPossibles.contains(derriereVoisin), "On ne peut pas sauter un autre anneau");
    }

    @Test
    public void testMove_DeplacementEtRetournementPion() throws DifferentAxisException {
        CoordinateDoubled depart = new CoordinateDoubled(5, 9);
        CoordinateDoubled casePion = new CoordinateDoubled(5, 11);
        CoordinateDoubled arrivee = new CoordinateDoubled(5, 13);

        Ring anneauBlanc = new Ring(Team.WHITE);
        board.put(depart, anneauBlanc);
        board.put(casePion, new Pawn(Team.BLACK));

        IState state = new State(board, Team.WHITE, List.of());
        Move coup = new Move(depart, arrivee);

        // Exécution du déplacement
        IState prochainEtat = state.move(coup);
        Map<Coordinate, Token> nouveauBoard = prochainEtat.board();

        // 1. L'anneau blanc est bien arrivé à destination
        assertEquals(anneauBlanc, nouveauBoard.get(arrivee), "L'anneau blanc doit être à l'arrivée (5, 13)");
        
        // 2. L'anneau a laissé un pion de sa propre couleur à sa case de départ
        assertTrue(nouveauBoard.get(depart) instanceof Pawn, "Le départ doit contenir un pion");
        assertEquals(Team.WHITE, nouveauBoard.get(depart).getTeam(), "Le pion laissé au départ doit être Blanc");

        // 3. Le pion adverse (noir) sauté a été retourné dans la couleur de l'anneau (blanc)
        assertTrue(nouveauBoard.get(casePion) instanceof Pawn, "La case intermédiaire doit toujours être un pion");
        assertEquals(Team.WHITE, nouveauBoard.get(casePion).getTeam(), "Le pion sauté noir aurait dû être retourné en Blanc");
    }

    @Test
    public void testMove_CoupInvalide_LanceException() {
        CoordinateDoubled depart = new CoordinateDoubled(5, 9);
        // Une coordonnée totalement en dehors des limites maximales du plateau hexagonal de l'image
        CoordinateDoubled arriveeInvalide = new CoordinateDoubled(5, 99); 

        board.put(depart, new Ring(Team.WHITE));
        IState state = new State(board, Team.WHITE, List.of());
        Move coupInvalide = new Move(depart, arriveeInvalide);

        // Le déplacement vers une case hors-jeu doit lever une exception
        assertThrows(IndexOutOfBoundsException.class, () -> {
            state.move(coupInvalide);
        }, "Un coup hors plateau doit lever une IndexOutOfBoundsException");
    }
}
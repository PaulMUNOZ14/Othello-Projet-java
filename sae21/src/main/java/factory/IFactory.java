package factory;

import model.state.IState;

/**
 * Interface définissant la création des différents plateaux de jeu.
 * Utilisée pour centraliser l'initialisation des tests et des parties.
 */
public interface IFactory {

    /**
     * Génère un état de jeu générique pour les tests unitaires.
     * @return L'état de jeu initialisé.
     */
    IState testState();

    /**
     * Génère un plateau configuré avec un alignement de 5 pions noirs.
     * @return L'état de jeu prêt pour tester la ligne noire.
     */
    IState stateForBlackLineTest();

    /**
     * Génère un plateau configuré avec un alignement de 5 pions blancs.
     * @return L'état de jeu prêt pour tester la ligne blanche.
     */
    IState stateForWhiteLineTest();

    /**
     * Génère un plateau de jeu strictement vide (aucun pion ni anneau).
     * @return L'état de jeu vierge.
     */
    IState emptyState();

    /**
     * Génère un plateau contenant deux alignements simultanés.
     * @return L'état de jeu pour tester les cas complexes de lignes multiples.
     */
    IState doubleLineStateTest();
}
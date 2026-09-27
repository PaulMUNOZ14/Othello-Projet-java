package ia;

import model.state.IState;
import model.action.Action;

public class Node {
	
	private final IState state;
	private final Node parent;
	private final Action action;
	
	/**
	 * Constructeur avec juste state
	 * @param state l'état du jeu
	 */
	public Node(IState state) {
		this(state, null, null);
	}
	
	/**
	 * Constructeur complet
	 * @param state l'état du jeu
	 * @param parent le noeud parent
	 * @param action l'action qu'il faut faire
	 */
	public Node(IState state, Node parent, Action action) {
		this.state = state;
		this.parent = parent;
		this.action = action;
	}

	/**
	 * Get State
	 * @return IState
	 */
	public IState getState() {
		return state;
	}

	/**
	 * Get Parent
	 * @return Node
	 */
	public Node getParent() {
		return parent;
	}

	/**
	 * Get Action
	 * @return Action
	 */
	public Action getAction() {
		return action;
	}
	
	/**
	 * Vérifie si le noeud est une racine ou non
	 * @return boolean, vrai si c'est une racine, faux sinon.
	 */
	public boolean isRoot() {
		return this.parent == null;
	}

}
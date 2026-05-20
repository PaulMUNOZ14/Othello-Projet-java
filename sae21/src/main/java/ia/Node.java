package ia;

import model.state.IState;
import model.action.Action;

public class Node {
	
	private final IState state;
	private final Node parent;
	private final Action action;
	
	public Node(IState state) {
		this(state, null, null);
	}
	
	public Node(IState state, Node parent, Action action) {
		this.state = state;
		this.parent = parent;
		this.action = action;
	}

	public IState getState() {
		return state;
	}

	public Node getParent() {
		return parent;
	}

	public Action getAction() {
		return action;
	}
	
	/*
	 * Vérifie si le noeud est une racine ou non
	 * @return boolean, vrai si c'est une racine, faux sinon.
	 */
	public boolean isRoot() {
		return this.parent == null;
	}

}
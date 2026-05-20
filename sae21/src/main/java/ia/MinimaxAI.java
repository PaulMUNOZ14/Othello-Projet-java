package ia;

import model.Team;
import model.action.Action;
import model.action.Move;
import model.action.RemoveLine;
import model.state.IState;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import coordinate.Coordinate;

public class MinimaxAI implements AI {
	
	private final int maxDepth;
	
	public MinimaxAI(int maxDepth) {
		this.maxDepth = maxDepth;
	}
	
	
	/**
	 * Méthode qui permet d'obtenir le meilleur coup
	 * @param currentState l'état actuel du plateau
	 * @return L'action choisir par l'IA.
	 */
	public Action chooseMove(IState currentState) {
		Team myTeam = currentState.turn();
		
		Node root = new Node(currentState);
		
		Action bestAction = null;
		int bestValue = Integer.MIN_VALUE;
		
		List<Action> possibleActions = possibleActions(currentState);
		
		if(possibleActions.isEmpty()) {
			return null;
		}
		
		for(Action action : possibleActions) {
			IState nextState = applyAction(currentState, action);
			Node child = new Node(nextState, root, action);
			int value = minimax(child, this.maxDepth - 1, false, myTeam);
			
			if(value > bestValue) {
				bestValue = value;
				bestAction = action;
			}
		}
		
		return bestAction;
	
	}
	
	
	/**
	 * Minimax
	 * @param node le noeud actuel
	 * @param depth la profondeur
	 * @param isMaximizing True si c'est au tour de l'IA
	 * @param iaTeam la team de l'IA
	 * @return le score du noeud
	 */
	public int minimax(Node node, int depth, boolean isMaximizing, Team iaTeam) {
		if (depth == 0 || node.getState().winner() != null) {
			return evaluate(node.getState(), iaTeam);
		}
		
		if(isMaximizing) {
			int maxEval = Integer.MIN_VALUE;
			List<Action> actions = possibleActions(node.getState());
			
			for(Action action : actions) {
				IState nextState = applyAction(node.getState(), action);
				Node child = new Node(nextState, node, action);
				
				int eval = minimax(child, depth - 1, false, iaTeam);
				maxEval = Math.max(maxEval, eval);
			}
			return maxEval;
		} else {
			int minEval = Integer.MAX_VALUE;
			List<Action> actions = possibleActions(node.getState());
			
			for (Action action : actions) {
				IState nextState = applyAction(node.getState(), action);
				Node child = new Node(nextState, node, action);
				
				int eval = minimax(child, depth - 1, true, iaTeam);
				minEval = Math.min(minEval, eval);
			}
			return minEval;
		}
	}
	
	public List<Action> possibleActions(IState state){
		
		List<Action> actions = new ArrayList<>();
		
		List<Set<Coordinate>> formedLines = state.lines();
		
		if (!formedLines.isEmpty()) {
			List<Coordinate> playerRings = state.rings().get(state.turn());
			
			if(playerRings != null) {
				for(Set<Coordinate> line : formedLines) {
					for(Coordinate ringPos : playerRings) {
						actions.add(new RemoveLine(line, ringPos));
					}
				}
			}
			
			if (!actions.isEmpty()) {
				return actions;
			}
		}
		
		List<Coordinate> currentRings = state.rings().get(state.turn());
		if(currentRings != null) {
			for(Coordinate from : currentRings) {
				Set<Coordinate> destinations = state.availableMoves(from);
				for (Coordinate to : destinations) {
					actions.add(new Move(from, to));
				}
			}
		}
		
		return actions;
		
	}
	
	public IState applyAction(IState state, Action action) {
		try {
			if (action instanceof Move) {
				return state.move((Move) action);
			} else if (action instanceof RemoveLine) {
				return state.removeLine((RemoveLine) action);
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}
	
	
	/**
	 * Fonction qui attribue un barème pour obtenir le score
	 * @param state l'état du jeu à évaluer
	 * @return le score de l'état du jeu
	 */
	public int evaluate(IState state, Team iaTeam) {
		
		Team winner = state.winner();
		if(winner == iaTeam) return 100000;
		if(winner == iaTeam.other()) return -100000;
		
		int score = 0;
		Team opponent = iaTeam.other();
		
		int iaRingsCount = state.rings().get(iaTeam).size();
		int oppRingsCount = state.rings().get(opponent).size();
		
		int iaRemovedRings = 5 - iaRingsCount;
		int oppRemovedRings = 5 - oppRingsCount;
		
		score += iaRemovedRings * 1000;
		score -= oppRemovedRings * 1000;
		
		
		List<Set<Coordinate>> potentialLines = state.getPawnsLines();
		for(Set<Coordinate> line : potentialLines) {
			if (!line.isEmpty()) {
				Coordinate firstCoord = line.iterator().next();
				var token = state.board().get(firstCoord);
				if(token != null) {
					if (token.getTeam() == iaTeam) {
						score += 50;
					} else {
						score -= 50;
					}
				}
			}
		}
		
		for (Coordinate ringPos : state.rings().get(iaTeam)) {
			score += state.availableMoves(ringPos).size() * 0.5;
		}
		for (Coordinate ringPos : state.rings().get(opponent)) {
			score -= state.availableMoves(ringPos).size() * 0.5;
		}
				
		return score;
	}
	
}

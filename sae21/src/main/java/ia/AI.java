package ia;

import model.action.Action;
import model.state.IState;

public interface AI{
	public Action chooseMove(IState state);
}
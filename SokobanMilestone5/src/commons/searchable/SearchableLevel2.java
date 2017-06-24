package commons.searchable;

import java.util.HashMap;
import commons.Level;
import model.data.items.Position;
import model.data.items.iMoveable;
import model.policy.MySokobanPolicy;
import search.Action;
import search.State;
import search.iSearchable;

public class SearchableLevel2 implements iSearchable<SokobanState> 
{
	Level level;
	iMoveable item;
	Position pos1;
	Position pos2;

	public SearchableLevel2(Level level, iMoveable item, Position pos1, Position pos2) 
	{
		this.level = level;
		this.item = item;
		this.pos1 = pos1;
		this.pos2 = pos2;
	}

	@Override
	public State<SokobanState> getInitialState() 
	{
		SokobanState s = new SokobanState(level);
		State<SokobanState> state = new State<SokobanState>(s, 0);
		
		return state;
	}

	@Override
	public State<SokobanState> getGoalState() 
	{
		
		
		return null;
	}

	@Override
	public HashMap<Action, State<SokobanState>> getAllPossibleStates(State<SokobanState> state) 
	{
		
		return null;
	}

}

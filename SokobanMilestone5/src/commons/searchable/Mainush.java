package commons.searchable;

import model.data.items.Position;
import search.BFS;
import search.Solution;

public class Mainush 
{
	public static void main(String[] arg)
	{
		
		char[][] board = {{'#',' ','#'},
						  {'o','@',' '},
						  {' ',' ',' '},
						  {' ',' ','A'},
						  {'#','#','#'}};

		SearchablePlayerToBox searchableP = new SearchablePlayerToBox(board, new Position(3,2), new Position(3,0));

		//BFS<Position> bfs = new BFS<>();
		//Solution sol = bfs.search(searchableP);
		
		//if(sol != null)
		//	System.out.print(sol);
		
		//else
		//	System.out.println("NULL");
		
		System.out.println("********************************************************");


		SearchableBoxToTarget searchableB = new SearchableBoxToTarget(board, new Position(1,1), new Position(2,1), new BFS<>(), searchableP);
		
		BFS<Position> bfs2 = new BFS<>();
		Solution sol2 = bfs2.search(searchableB);
		
		if(sol2 != null)
			System.out.println(sol2);
		
		else
			System.out.println("NULL");
	}
}
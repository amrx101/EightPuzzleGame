
import java.util.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author amit
 */
public class EightPuzzle 
{
    // Min Priority Queue to store next states of the game 
    // Using JCF , own priority queue is implemented in C and wanted OOP feature 
    PriorityQueue <GameState> open = new PriorityQueue<GameState>() ;           
    // The closed GameState set.
    //  ArrayList screws runtime ? why??/////////
    HashSet <GameState> closed = new HashSet <GameState>();
    // Queue will help in printing paths from goal to end
    Queue <GameState > myQueue = new LinkedList <GameState>();  // Queue is an abstract class , cannot be instantiated
    ArrayList <GameState> child_list = new ArrayList<GameState>();  // array of states  to hold children
    public  void algo_implementataion(int [] first_state) 
    {
        // store the start time
        long start_time = System.currentTimeMillis();
        // Add initial GameState to queue.
        open.add(new GameState(first_state));
        // until there are states to explore
        while (!open.isEmpty())
        {
            // Get the lowest priority GameState.
            GameState currentState = open.remove();
            // add it to the visited states list
            myQueue.add(currentState);
            // If it's the goal, we're done.
            if (currentState.isGoal()) 
            {
                //calculate time took
                long end_time = System.currentTimeMillis();
                long run_time = end_time - start_time;
                //show the path
                currentState.print_all();
              //  show_path(myQueue);
                System.out.println("elapsed  (ms) = " + run_time);
                return;
            }
            closed.add(currentState);
            // Add successors to the queue.
            // a maximum of 4 movements are possible
            // given a hole 
            child_list.clear(); 
            child_list = currentState.get_child_array(currentState);   // get all children from current state
            for(int i = 0, j = child_list.size(); i < j; i++)
            {
                //insertChildren makes sure that the state was not in closed list
                // critical for optimization
                insertChildren(child_list.get(i));     // insert child into open list
            }
            child_list.clear();   // miss this line and runtime will increase as hell
        } 
    }
    // checks if Game state already visited, if not visisted then adds it to the priority queue
    // critical fector for optimization of A* search algorithm
   void insertChildren(GameState child)
   {
       // check if child is null or already visited 
       if( !closed.contains(child))
       {
           open.add(child);
       }
   }
   // Cannot figure out the bug in this method so use the recursive method 
   public static  void show_path(Queue <GameState> myQueue)
   {
       while(myQueue.size() >0)
       {
           myQueue.remove().printGameState();
       }
   }
}

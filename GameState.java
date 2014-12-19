
import java.util.*;

/* GameState class represents instances of the puzzle
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author amit
*/
 public class GameState  implements  Comparable
 {
     // data fields declaration 
        GameState parent;  // Previous GameState in solution chain.
        int [] board;        // board of the game 
        int hole_pos;     // Index of space (zero) in board  
        int g;            // Number of moves from start.
        int h;          // Heuristic value (difference from goal)
       
        
        int [] final_board = {  0,1, 2, 3, 4, 5, 6,7,8 };
         
        
        //Constructor for creating the initial state of the game 
        GameState(int [] first)
        {
            this.parent = null;   // first state previous state is always null
            this.board = first;  // board of the game
            this.hole_pos = get_hole_pos(first);         // hole in the board
            this.g = 0;                   // set g value 
            this.h = generic_compute_h(final_board, board);   // get manhattan distance of all the blocks
            
        }
        
        //Constructor overloading for all other GameState than the first
        GameState(GameState prev, int index)
        {
            this.parent = prev;   // aling parent 
            this. board = get_new_board(prev,index);    // get new board 
            this.hole_pos = index;           // hole 
            this.g = prev.g + 1;              // increment g value , 1 move more than parent's moves
            this.h = generic_compute_h(final_board, board);         //compute manhattan distance for the new board configuration
        }
        
        // given existing board and the target space gives new array alignment
        public static int [] get_new_board(GameState previous, int index)
        {
            int [] new_board = new int [9];
            for(int i = 0; i< 9; i++)
            {
                new_board[i] = previous.board[i];
            }
            int initial_zero = previous.hole_pos;
            new_board[initial_zero] = previous.board[index];
            new_board[index] = 0;
            return new_board;
        }
          //generates all possible future game state and adds it to the local list
        public  ArrayList<GameState> get_child_array(GameState current)
        {
            ArrayList <GameState> child_list = new ArrayList<GameState>();  // create a local instance of arraylist , no need to clear it and no  worry
            child_list.clear();   //  be extra cautious , this debuging took hours
            if (current.hole_pos % 3 < 2)
            {
                child_list.add(new GameState(current,current.hole_pos + 1));
            }
            if (current.hole_pos % 3 > 0)
            {
                child_list.add(new GameState (current,current.hole_pos - 1));
            }
            if (current.hole_pos < 6)
            {
                child_list.add(new GameState (current,current.hole_pos + 3));
            }
            if (current.hole_pos > 2 )
            {
                child_list.add(new GameState(current,current.hole_pos - 3));
            }
            return child_list; 
         }

        // Check if the current state is goal
        public boolean isGoal()
        {
            boolean val = true;
            int []  arr = this.board;
            // check if all alingment of final board and current board is same 
            // return true if yes , else return false
            for(int i = 0; i< 9; i++)
            {
                if (arr[i] != final_board[i])
                {
                    val = false;
                    return val;
                }
            }
            return val;
        }     
        // returns  position of zero in a game state array, return -1 if 
        public static int get_hole_pos (int [] arr)
        {
            int len = arr.length;
            for(int i = 0; i< len ;i++)
            {
                if(arr[i] == 0)
                    return i;
            }
            return -1;
        }

    // Return the Manhatten distance between tiles with indices a and b. a from goal state and b from intermediate state
        static int manhattenDistance(int a, int b) 
        {
            return Math.abs(a / 3 - b / 3) + Math.abs(a % 3 - b % 3);
        }
    // to be used in generic goal state game , error if returns -1
        public static int get_pos (int [] arr, int x)
        {
            for(int i = 0, j = arr.length; i < j; i++)
            {
                if (arr[i]== x)
                return i;
            }
            return - 1;
        }
    // to compute Manhattan distance for any goal state
        public static int generic_compute_h(int [] goal, int [] arr)
        {
            int h = 0;
            for(int i = 0,j = goal.length; i < j; i++)
            {
                // skip 0 in the goal state 
                if(goal[i] != 0)
                {
                    // if not zero calculate manhattan distances between indexces of goal state and intermediate state with same value
                    // i is the goal state index for some value x
                    //get_pos () returns the index for value x in the intermediate state array
                    h = h + manhattenDistance(i,get_pos(arr,goal[i]));
                }
            }
            return h;
         }
         public  int get_total_cost()
         {
             return h + g;
         } 
    @Override
    // implement to produce a minimum priority and minimum priority based sorting 
         public int compareTo(Object t)
         {
             if(this.get_total_cost() >((GameState)t).get_total_cost())
                 return 1;
             else if (this.get_total_cost() < ((GameState)t).get_total_cost()) return -1;
             else return 0;
         }
     @Override
         public boolean equals(Object obj)
         {
             if (obj instanceof GameState)
             {
                 GameState other = (GameState)obj;
                 return Arrays.equals(board, other.board);
             }
             return false;
         }
   
  
     //@Overrid
          public int hashCode()
          { 
              return Arrays.hashCode(board);
          }
        
          void printGameState() 
          {
              System.out.println("-------------------------------");
              System.out.println("F = " + get_total_cost() + " = g+h = " + g + "+" + h);
              for (int i = 0; i < 9; i = i + 3)
              {
                  System.out.println(board[i] + " " + board[i+1] + " " + board[i+2]);
              }       
              System.out.println("--------------------------------");
           }
        // Print the solution chain with start GameState first.
        
          /* thought of it earlier , gives java out of Memory error, so use recursion instead
          void print_all()
          {
              Stack <GameState> my = new Stack<GameState>();
              GameState x = this;
              while(this.parent != null)
              {
                  my.push(x);
                  if(x.parent != null)
                    x = x.parent;
              }
              while(!my.isEmpty())
              {
                  x = my.pop();
                  x.printGameState();
              }
          }
          */
          // woho recursion!!!!!!!!!!!!!!!!!!! impressed myself
          // idea of recursion  got from forums
          void print_path()
          {
               if (parent != null)
                   parent.print_path();
               System.out.println();
               printGameState();
          }
    }

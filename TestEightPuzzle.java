/*
 * Input : an instance of 8 x 8 puzzle, blank denoted by 0
 * Compilation : javac TestEightPuzzle.java
 * Execution   :java TestEightPuzzle
 * Dependencies :GameState.java,EightPuzzle.java
 * Created by: Amit Kumar <amrx101@gmail.com>,<amit.1.kumar@st.niituniversity.in>
 * and open the template in the editor.
 */


import javax.swing.*;
public class TestEightPuzzle {
    public static void main(String[] args) {
        String in = JOptionPane.showInputDialog("Enter intial state of puzzle");
        if(invalid_input(in))
        {  
            System.out.println("!!!Hey input is invalid man");
            System.out.println("Exiting.....................");
            System.exit(0);
        }
         int [] initial = new int [9];
         for(int i = 0,j = in.length(); i< j;i++)
         {
             char alpha = in.charAt(i);
             initial[i] = (int)(alpha -'0');   // @@##$# -'
             //System.out.print(initial[i]);
             
         }
        if(!is_solvable(initial))
        {
            System.out.println("!!!! Hey unsolvable puzzle");
            System.out.println("Exiting............");
            System.exit(0);
        }
        EightPuzzle x = new EightPuzzle();
        x.algo_implementataion(initial);
    }
    
    // determines whether a game state is solvable
     public static boolean is_solvable(int [] initial)
    {
        // calculate all inversions in the array 
        //inversion is when array[i] > array[j] and i < j
        int inversions = 0;
        int len = initial.length;
        for(int i = 0; i< len -1; i++)
        {
            int j ;
            for( j = i + 1; j < len; j++)
            {
                if (initial[i] > initial[j])
                    inversions++;
            }
        }
        // for odd width board if inversion is divisible by 2 , solution exists
        return (inversions % 2 == 0);
    }
    public static boolean invalid_input(String in)
    {
        return (in.length() != 9);
    }
    }
    


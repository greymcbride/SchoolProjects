/**
 * Grey McBride
 * CPSC 5002, Seattle University
 * This is free and unencumbered software released into the public domain.
 */
package gmcbride_p1x;


import java.util.Scanner; //import scanner

/**
 * Enumerated Type for Symbols
 */
enum Symbol {

   X_SYMBOL('X'), O_SYMBOL('O');
   private final char asChar;
   /**
    * Returns Symbol as a character
    * @return the Symbol as a character
    */
   public char asChar() {
      return asChar;
   }

   /**
    * Constructor
    * @param asChar the character value of the symbol
    */
   Symbol (char asChar) {
      this.asChar = asChar;
   }
}

/**
 * This class defines the TicTacToeX object as well as it's properties and
 * methods.
 * @author Grey McBride
 * @version 1.3
 */
public class TicTacToeX {
   private static final int X_VAL = 1; //constant x symbol's value in array
   private static final int O_VAL = -1; //constant  o symbol's value in array
   private static final int DEFAULT_SIZE = 3; //default board size
   private static final int COORDINATES = 2; //constant to store the value 2
   private int[][] boardStatus; //int array to store board state
   private int size; //int for size of board (always square)
   private int turnCount = 1; //counter for number of turns, starts at 1
   private int xWins = 0; //counter for number of times x has won
   private int oWins = 0; //counter for number of times o has won
   private int tieGames = 0; //counter for number of tie games

   /**
    * Constructor
    */
   public TicTacToeX() {
      this.size = DEFAULT_SIZE; //set size
      boardStatus = new int[size][size]; //make new board
   }

   /**
    * This method displays the formatted board to the user, using the board
    * status and enum types to print the proper symbol for the space
    */
   public void displayBoard() {
      //initial spacing
      System.out.print("\n\t");
      //print column labels
      for(int i = 0; i < size; i++) {
         System.out.printf("   %d", i);
      }
      //print row label
      for(int i = 0; i < size; i++) {
         System.out.printf("\n\t%d", i);
         //print proper spacing
         for (int j = 0; j < size; j++) {
            System.out.print("  ");
            //check which symbol to print
            if(boardStatus[i][j] == -1) {
               System.out.print(Symbol.O_SYMBOL.asChar());
            } else if(boardStatus[i][j] == 1) {
               System.out.print(Symbol.X_SYMBOL.asChar());
            } else {
               System.out.print(" ");
            }
            //print formatting
            System.out.print("|");
         }
         //proper spacing
         System.out.print("\n\t ");
         //print divider
         for(int k = 0; k < size; k++) {
            System.out.print("----");
         }
      }
   }

   /**
    * This method displays the stats at the games end, and also increments
    * the number of tie games based on a bool of whether a winner was found.
    * @param tieGame whether the game did not have a winner
    * @param c the character value of the winner
    */
   public void displayStats(boolean tieGame, char c) {
      //true if the game had no winner
      if (tieGame) {
         this.tieGames++;
      System.out.println("No Winner - it's a tie!");
      //Print winner
      }else {
         System.out.println(c + " has won this match!");
      }
      //Show final board
      this.displayBoard();
      //print the stats
      System.out.printf("\nGame Stats:\nX has won %d games.\nO has won %d " +
            "games.\nThere have been %d tie games.", this.xWins, this.oWins,
            this.tieGames);
   }

   /**
    * This method prompts the user for their marker placement, it is called
    * by placeMarker. It uses an int array to store the coordinates, and
    * returns a boolean to see whether the entry was invalid, so it would need
    * to be called again. An int array is used for the coordinates to pass
    * multiple values back to the placeMarker function.
    * @param rowCol int array of size 2 to store coordinates
    * @param userIn Scanner to read user input
    * @return whether the entry was a valid space and unoccupied
    */
   public boolean userEntry(int[] rowCol, Scanner userIn) {
      //prompt user for coordinates
      System.out.print("\nWhich row? ");
      //Input verification for row
      if(!userIn.hasNextInt()) {
         System.out.println("That's not a number.");
         userIn.next();
      } else {
         rowCol[0] = userIn.nextInt();
         System.out.print("\nWhich column? ");
         //input verification for column
         if(!userIn.hasNextInt()) {
            System.out.println("That's not a number.");
            userIn.next();
         } else {
            rowCol[1] = userIn.nextInt();
         }
         //input verification - in bounds & unoccupied
         if (rowCol[0] > size - 1 || rowCol[1] > size - 1 ||
               boardStatus[rowCol[0]][rowCol[1]] != 0) {
            System.out.print("\nThat space won't work, let's try again.");
            return false;
         } else return true;
      }
      return false;
   }

   /**
    * This method places a marker by calling the userEntry method after
    * checking the variable c it is passed to determine which value will be
    * assigned to the int array of the board status. An int array is used for
    * the coordinates to pass multiple values back from the userEntry function.
    * @param userIn Scanner for user input
    * @param c The Symbol (X or O) to be used, expressed as a character
    */
   public void placeMarker(Scanner userIn, char c) {
      turnCount++; //increment turn counter
      boolean goodEntry; //flag for whether the entry was valid
      int valToAdd; //the value to be placed into the
      int[] rowCol = new int[COORDINATES]; //int array to store coordinates
      //use c to determine which value to be added
      if(c == Symbol.X_SYMBOL.asChar()) {
         valToAdd = X_VAL;
      } else valToAdd = O_VAL;
      //Tell the users whose turn it is
      System.out.println("\n" + c + ", it is your turn.");
      //prompt for entry until a valid input is received
      do {
         goodEntry = userEntry(rowCol, userIn);
      } while (!goodEntry);
      //update boardStatus with the proper value at the proper coordinates
      boardStatus[rowCol[0]][rowCol[1]] = valToAdd;
   }

   /**
    * This method checks the rows and columns for the win condition using a
    * for loop and returns a boolean of whether a winning board state was found
    * @param winCon The sum needed to determine a winning row or column,
    *               equal to the size of the board
    * @return whether a winning board state was found
    */
   public boolean checkRowsColumns(int winCon) {
      int rowSum, colSum; //ints to store current row and column sums
      for(int i = 0; i < size; i++) {
         rowSum = 0; //reset row sum counter
         colSum = 0; //reset column sum counter
         for (int j = 0; j < size; j++) {
            //update current row and column sums for row i / column i
            rowSum += boardStatus[i][j];
            colSum += boardStatus[j][i];
            //check for winning row or column and return true if found
            if (rowSum == winCon || colSum == winCon) {
               return true;
            }
         }
      }
      return false;
   }

   /**
    * This method checks the two diagonals, labeled left top and right top,
    * for the win condition and returns a boolean as to whether a winning
    * board state was found.
    * @param winCon The sum needed to determine a winning row or column,
    *               equal to the size of the board
    * @return whether a winning board state was found
    */
   public boolean checkDiags(int winCon) {
      int LTdiagSum = 0, RTdiagSum = 0; //ints to store the diagonal sums
      for(int i = 0; i < size; i++) {
         //update Left Top Diagonal Sum
         LTdiagSum+= boardStatus[i][i];
         //update Right Top Diagonal Sum
         RTdiagSum+= boardStatus[i][size - 1 - i];
      }
      //check for winning diagonals and return true if found
      return LTdiagSum == winCon || RTdiagSum == winCon;
   }

   /**
    * This method checks for a winner by calling check diags and
    * checkRowsColumns. It defines the win condition as either the positive
    * or negative integer value of size as the X's and O's are stored as
    * positive or negative 1 on the board.
    * @param c The char value of the Symbol to determine which winner to look
    *         for.
    * @return whether a winner was found
    */
   public boolean checkWinner(char c) {
      int winCon; //int to store win condition based on symbol
      boolean winFlag; //flag for whether a winner is found

      //determine the win condition
      if (c == Symbol.X_SYMBOL.asChar()) {
         winCon = size;
      } else {
         winCon = -size;
      }
      //check for the win condition and update flag
      winFlag = checkDiags(winCon) || checkRowsColumns(winCon);

      //increment win counters
      if(winFlag && c == Symbol.X_SYMBOL.asChar())
         this.xWins++;
      if(winFlag && c == Symbol.O_SYMBOL.asChar())
         this.oWins++;
      //return whether a winner was found
      return winFlag;
   }

   /**
    * Accessor for turnCount
    * @return the number of turns
    */
   public int getTurnCount() {
      return this.turnCount;
   }

   /**
    * Mutator for size. Also resets boardStatus and turnCount.
    * @param size the size of board to be generated
    */
   public void setSize(int size) {
      this.size = size;
      this.boardStatus = new int[size][size];
      this.turnCount = 1;
   }
}

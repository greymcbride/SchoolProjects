/**
 * Grey McBride
 * CPSC 5002, Seattle University
 * This is free and unencumbered software released into the public domain.
 */
package gmcbride_p1x;

import java.util.Scanner;

/**
 * This program plays a game of TicTacToe with the user and demonstrates the
 * use of the TicTacToeX object.
 * @author Grey McBride
 * @version 1.3
 */
public class P1X {
   /**
    * This main method calls openingMessage to print an opener, and
    * closingMessage to print a closer. It creates a Scanner object and a
    * TicTacToeX object to use in the game. It then calls sizePrompt,
    * playGame, and repeatPrompt in a while loop to play the game as much as
    * the user wants.
    * @param args A string array containing the command line arguments
    */
   public static void main(String[] args) {
      int size; //the size of the board
      boolean repeat; //flag for repeating the game
      //print opener
      openingMessage();
      Scanner userIn = new Scanner(System.in); //Scanner for user input
      //create TicTacToeX game object
      TicTacToeX game = new TicTacToeX();
      //plays the game
      do {
         //get new board size
         size = sizePrompt(userIn);
         //play the game
         playGame(game,userIn,size);
         //prompt for repeat and update flag
         repeat = repeatPrompt(userIn);
      } while (repeat); //repeat while user desires
      //print closer
      closingMessage();
   }

   /**
    * Prints the opening message
    */
   public static void openingMessage() {
      System.out.print("\nWelcome to TicTacToe!\n\n");
   }

   /**
    * Prints the closing message
    */
   public static void closingMessage() {
      System.out.println("\nThanks for playing!");
   }

   /**
    * This method prompts the user for a board size and verifies that it is
    * within the bounds specified as well as odd numbered, to ensure clear
    * diagonals.
    * @param userIn Scanner for user input
    * @return the size desired
    */
   public static int sizePrompt(Scanner userIn) {
      final int MAX_SIZE = 25; //constant for maximum board size
      final int MIN_SIZE = 3; //constant for minimum board size
      int size; //int to store size from user input
      boolean invalidBoard; //flag for whether the size is valid
      do {
         //Prompt user for size
         System.out.print("\nWhat size game board would you like? ");
         size = userIn.nextInt();
         //define/redefine flag based on input
         invalidBoard = size < MIN_SIZE || size > MAX_SIZE || size % 2 == 0;
         if(invalidBoard)
            //explain issue
            System.out.print("\nInvalid, try again. \nOdd numbers only, " +
                  "between 3 and 25.");
      } while(invalidBoard); //continue until a valid size is achieved
      return size;
   }

   /**
    * This method prompts the user for whether they would like to repeat the
    * game.
    * @param userIn Scanner for user input
    * @return whether to repeat
    */
   public static boolean repeatPrompt(Scanner userIn) {
      userIn.nextLine(); //clear new line tag
      //Prompt
      System.out.print("\nWould you like to play again? (y/n) ");
      String reply = userIn.nextLine();
      //check for yes
      return (reply.charAt(0) != 'y'
            || reply.charAt(0) != 'Y');
   }

   /**
    * This method plays the game with the user by resetting the board to a
    * new one of the specified size, then calling the methods placeMarker,
    * checkWinner, and displayStats from the TicTacToeX class.
    * @param game The TicTacToeX object to play with
    * @param userIn Scanner for user input
    * @param size the desired size of board
    */
   public static void playGame(TicTacToeX game, Scanner userIn, int size) {
      char currentPlayerSymbol; //char to store current player's symbol
      boolean hasWinner; //flag for whether a winner has been found
      game.setSize(size); //create game board
      do {
         game.displayBoard(); //display current board status
         //X plays first and on odd turns
         if (game.getTurnCount() % 2 == 1) {
            currentPlayerSymbol = Symbol.X_SYMBOL.asChar();
         //O plays on even turns
         } else currentPlayerSymbol = Symbol.O_SYMBOL.asChar();
         //Prompt user and place their marker
         game.placeMarker(userIn, currentPlayerSymbol);
         //check for winner and update flag
         hasWinner = game.checkWinner(currentPlayerSymbol);
      //continue while no winner is found and game board is not full
      } while (!hasWinner && game.getTurnCount() <= size * size);
      //display stats
      game.displayStats(!hasWinner, currentPlayerSymbol);
   }
}

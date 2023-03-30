package gmcbride_p3x;

import java.util.Scanner;

/**
 * The SillyCardGame class contains a main method that utilizes the GameModel
 * object in order to play the silly card game as many times as the user
 * wants, with between 2 and 6 players.
 * @author Grey McBride
 * @version 1.0
 */
public class SillyCardGame {
   public static void main(String[] args) {
      Scanner keyboard = new Scanner(System.in); //scanner for user input
      boolean repeatFlag, gameEnd; //flags for repeat and the game ending
      String outputString; //to store output from GameModel for printing
      //Print opening text message
      printOpener();
      //plays another game while the user wants to repeat
      do {
         GameModel game = new GameModel(); //make new GameModel
         //set up the GameModel and get number of players
         game.setupGame(getNumPlayers(keyboard));
         //plays another turn until the game ends
         do {
            //get output for next player's turn
            outputString = game.nextPlayerTurn();
            //print output
            System.out.println(outputString);
            //check for win / stalemate conditional text
            gameEnd =
                  outputString.contains("You have won") ||
                  outputString.contains("Tough situation.");
         } while (!gameEnd); //until game ends
         //prompt for repeat and update flag
         repeatFlag = repeatPrompt(keyboard);
         keyboard.nextLine(); //clear new line tag
      } while(repeatFlag); //until user wants to stop
      //Prints closing text message
      printCloser();
   }

   /**
    * This method uses a scanner to prompt the user for the number of
    * players, verifies that the input is acceptable, and then returns it as
    * an int
    * @param keyboard Scanner for user input
    * @return The number of players
    */
   public static int getNumPlayers(Scanner keyboard) {
      final int MAX_PLAYERS = 6; //maximum number of players
      final int MIN_PLAYERS = 2; //minimum number of players
      int numPlayers = 0; //number of players
      String str = "\nHow many players? (2-6) "; //prompt
      //prompt until acceptable input is received
      do {
         System.out.print(str);
         if(keyboard.hasNextInt()) //verify an int was entered
            numPlayers = keyboard.nextInt();
         else //clear the next token if no int
            keyboard.next();
      } while(!(numPlayers >= MIN_PLAYERS && numPlayers <= MAX_PLAYERS));
      return numPlayers;
   }

   /**
    * This method prompts the user to repeat the game using a scanner and
    * returns a boolean.
    * @param keyboard Scanner for user input
    * @return whether to play again
    */
   public static boolean repeatPrompt(Scanner keyboard) {
      System.out.print("Would you like to play again? (n to quit) ");
      return !(keyboard.hasNext() && keyboard.next().charAt(0) == 'n');
   }

   /**
    * This method prints the opening text message
    */
   public static void printOpener() {
      System.out.println("""
         
         Welcome to the Silly Card Game! (Presented by Silly Little Games Inc.)
         In this game you can play a very silly and fun card game against up
         to 5 other players! The best part is you don't even have to play your
         turns! We do that for you! Good Luck!""");
   }

   /**
    * This method prints the closing text message
    */
   public static void printCloser() {
      System.out.println("""
            
            Thanks for playing the game!!
            Hope it was everything you ever dreamed of! Good bye!""");
   }
}

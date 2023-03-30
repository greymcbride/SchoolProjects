package gmcbride_p3x;

import java.util.ArrayList;
import java.util.Random;

/**
 *The Game Model class defines a game model object that contains all the
 * necessary stacks and queues to play the silly card game. It also has
 * constants that are used to perform all logic and calculations required
 * over the course of the game, which it returns to the SillyCardGame class
 * through various methods.
 * @author Grey McBride
 * @version 1.0
 */
public class GameModel {
   static final int NUMBER_OF_CARDS = 52; //Total number of cards constant
   static final int NUMBER_OF_UNIQUE_CARD_VALUES = 13; //Unique values constant
   static final int INITIAL_HAND_SIZE = 7; //Constant for starting hand size
   static Stack<Integer> dealStack; //Stack to store the cards to be drawn
   static Stack<Integer> discardStack; //Stack to store discarded/played cards
   static Queue<Integer>[] playerQueues; //Queue array to store player's queues
   static ArrayList<Integer> cards = new ArrayList<>(NUMBER_OF_CARDS); //cards
   int currentPlayer = 0; //The player's index whose turn it is

   /**
    * This method shuffles the cards. It takes in the ArrayList of cards and
    * randomizes their order
    * @param cards The ArrayList of cards to be shuffled
    */
   private void shuffleDeck(ArrayList<Integer> cards) {
      Random rand = new Random();
      for (int i = cards.size(); i > 1; i--) {
         int j = rand.nextInt(i);
         int temp = cards.get(i - 1);
         cards.set(i - 1, cards.get(j));
         cards.set(j, temp);
      }
   }

   /**
    * Constructor
    */
   public GameModel() {
      dealStack = new Stack<>();
      discardStack= new Stack<>();
   }

   /**
    * This method (re-)initializes the GameModel whenever it is called. It
    * empties all stacks and queues and shuffles the cards, then populates
    * the deal stack and deals the starting hands using other methods.
    * @param playerCount The number of players in this game
    */
   public void setupGame(int playerCount) {
      //Reset player count and queues
      playerQueues = new Queue[playerCount];
      for(int i = 0; i < playerCount; i++) {
         playerQueues[i] = new Queue<>();
      }
      //reset deal and discard stacks
      dealStack = new Stack<>();
      discardStack= new Stack<>();
      //reset and populate cards
      cards = new ArrayList<>(NUMBER_OF_CARDS);
      for(int i = 1; i <= NUMBER_OF_CARDS; i++) {
         cards.add(i);
      }
      //Shuffle the deck
      shuffleDeck(cards);
      //Populate dealStack
      populateDealStack();
      //Deal initial hands
      dealInitialHands();
      //first discard
      discardStack.push(dealStack.pop());
   }

   /**
    * This method plays one turn for the next player and returns the proper
    * output string to the SillyCardGame class to be printed to the console.
    * It calls a few other methods to determine whether actions are valid,
    * what to add to the string, and when the turn is a winning turn.
    * @return The output String to be printed in SillyCardGame
    */
   public String nextPlayerTurn() {
      boolean gameDead = false; //Flag for whether a stalemate is reached
      //StringBuilder for the string to return
      StringBuilder formattedPlayerTurn = new StringBuilder();
      //Add initial text
      formattedPlayerTurn.append("Player " + (currentPlayer + 1) + "'s turn, " +
            "cards:\n");
      //Display player's queue of cards
      formattedPlayerTurn.append(playerQueues[currentPlayer]);
      //get top of discardStack and append
      int topCard = discardStack.peek();
      formattedPlayerTurn.append("\nDiscard pile card: " + topCard);
      //get player's next card and append
      int playerCard = playerQueues[currentPlayer].dequeue();
      formattedPlayerTurn.append("\nYour current card: " + playerCard);
      //play player's card
      discardStack.push(playerCard);
      //Determine if player has won
      if(playerQueues[currentPlayer].empty()) {
         formattedPlayerTurn.append("\nYou have won the game!\n");
         formattedPlayerTurn.append("\nThe game has finished.\n");

      //evaluate turn - see documentation for individual methods
      } else {
         formattedPlayerTurn.append("\nYour card is ");

         if (playerCard == topCard) {
            formattedPlayerTurn.append("EQUAL, pick up 1 card.\n");
            gameDead = drawCard(playerCard);

         } else if (playerCard < topCard) {
            formattedPlayerTurn.append("LOWER, pick up 2 cards.\n");
            gameDead = drawCard(playerCard);
            gameDead = drawCard(playerCard);

         } else {
            formattedPlayerTurn.append("HIGHER, turn is over.\n");
         }
         //Text to append if stalemate is reached
         if(gameDead) {
            formattedPlayerTurn.append("Tough situation. No winner");
         }
      }
      //update currentPlayer
      if(currentPlayer < playerQueues.length - 1) {
         currentPlayer++;
      } else {
         currentPlayer = 0;
      }
      return formattedPlayerTurn.toString();
   }

   /**
    * This method determines whether the player can draw from the deal stack,
    * and if not it will flip the discard stack into the new deal stack,
    * retaining the most recent addition
    * @param playerCard The most recent discard addition
    * @return whether the player is able to draw a card
    */
   public boolean canDraw(int playerCard) {
      //return false if a stalemate is reached
      if (dealStack.empty()) {
         if(discardStack.empty()) {
            return false;
         }
         //flip discard onto deal
         dealStack = discardStack.flip();
         //reset discard
         discardStack = new Stack<>();
         //push most recent discard
         discardStack.push(playerCard);
      }
      return true;
   }

   /**
    * This method populates the deal stack with the actual values of the
    * cards (Four copies of 1 - 13 rather than 0 -51).
    */
   public void populateDealStack() {
      for (Integer card : cards) {
         //it just makes sense okay
         dealStack.push((card % NUMBER_OF_UNIQUE_CARD_VALUES) + 1);
      }
   }

   /**
    * This method deals the initial hands to each player in round-robin
    * fashion from the deal stack using for loops.
    */
   public void dealInitialHands() {
      for(int i = 0; i < INITIAL_HAND_SIZE; i++) {
         for (Queue<Integer> playerQueue : playerQueues) {
            playerQueue.enqueue(dealStack.pop());
         }
      }
   }

   /**
    * This method calls canDraw to determine if the player can draw, then
    * performs the draw operation if it is possible. If it is impossible, it
    * returns true in order to update the stalemate flag in nextPlayerTurn.
    * @param playerCard The most recent card played, passed to canDraw
    * @return whether the draw was possible to perform
    */
   public boolean drawCard(int playerCard) {
      if (canDraw(playerCard)) {
         playerQueues[currentPlayer].enqueue(dealStack.pop());
         return false;
      }
      return true;
   }
}
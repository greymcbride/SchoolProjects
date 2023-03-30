package gmcbride_p3x;

import java.util.EmptyStackException;

/**
 * This is a class for generic stacks that can be of a specified data type
 * @param <T> The data type for this stack
 * @author Grey McBride
 * @version 1.0
 */
public class Stack<T> {
   //top of stack
   private Node top;
   /**
    * Nodes to store values in stack
    */
   private class Node {
      public T value; //value of node
      public Node next; //ref to successor

      /**
       * Constructor
       *
       * @param value The value of the node
       * @param next  The successor node
       */
      Node(T value, Node next) {
         this.value = value;
         this.next = next;
      }
   }

   public Stack() {
      top = null;
   }

   /**
    * Returns true if stack is empty
    * @return whether it is empty
    */
   public boolean empty() { return top == null;}

   /**
    * Push a value onto the top of the stack
    * @param v Value to be held in new node
    */
   public void push(T v) { top = new Node(v, top);}

   /**
    * Pop the top node off of the list and return it
    * @return Double value of the node popped off
    */
   public T pop() {
      //Empty exception
      if(empty()) {
         throw new IllegalArgumentException("can't pop - empty");
      } else {
         T retValue = top.value;
         top = top.next;
         return retValue;
      }
   }

   /**
    * Peek at the top node of the stack and return its value without altering
    * the stack
    * @return The double value of the top node of the stack
    */
   public T peek() throws EmptyStackException {
      //Empty exception
      if(empty()) {
         throw new IllegalArgumentException("empty stack");
      } else {
         return top.value;
      }
   }

   /**
    * Returns whether this stack is equivalent to another.
    * This isn't used in this project, but I consider it to be a core method
    * for this stack class, so I am leaving it in.
    * @param otherStack The stack to compare to this one
    * @return whether they are equal
    */
   public boolean equals(Stack<T> otherStack) {
      boolean equalFlag = true;
      Node tempThis = top, tempOther = otherStack.top;
      while(tempThis != null && equalFlag) {
         if(tempThis.value != tempOther.value)
            equalFlag = false;
         tempThis = tempThis.next;
         tempOther = tempOther.next;
      }
      return equalFlag;
   }

   /**
    * This method makes a deep copy of the stack by making two copies.
    * This isn't used in this project, but I consider it to be a core method
    * for this stack class, so I am leaving it in.
    * @return the deep copy of the stack
    */
   public Stack<T> copy() {
      Stack<T> reverseStack = this.flip();
      return reverseStack.flip();
   }

   /**
    * This method makes a deep flip of the stack by making one copy
    * @return the deep copy of the stack
    */
   public Stack<T> flip() {
      Stack<T> reverseStack = new Stack<>();
      Node copier = top;
      T value;
      do {
         value = copier.value;
         reverseStack.push(value);
         copier = copier.next;
      } while(copier != null);
      return reverseStack;
   }


   /**
    * This method returns the values within the stack as a string
    * @return the stack as a string
    */
   public String toString() {
      StringBuilder sBuilder = new StringBuilder();
      Node traveler = top;
      while(traveler != null) {
         sBuilder.append(traveler.value + " ");
         traveler = traveler.next;
      }
      return sBuilder.toString();
   }
}

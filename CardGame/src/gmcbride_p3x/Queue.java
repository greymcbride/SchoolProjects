package gmcbride_p3x;


import java.util.EmptyStackException;

/**
 * This class creates generic queues that can be of multiple data types
 * @param <T> the data type to be stored in this queue
 * @author Grey McBride
 * @version 1.0
 */
public class Queue<T> {
   public class Node {
      public T value; //the render command for this node
      public Node next; //ref to successor
      public Node prev; //ref to predecessor

      /**
       * Constructor for nodes
       * @param value The value to be stored
       * @param p The predecessor node
       * @param n The successor node
       */
      public Node(T value, Node p, Node n) {
         this.value = value;
         next = n;
         prev = p;
      }
   }
   public Node head = null; //ref to head of list
   public Node tail = null; //ref to tail of list

   /**
    * This method creates a deep copy of the RenderQueue
    * @return The copy queue
    */
   public Queue<T> copy() {
      Queue<T> copyQ = new Queue<>(); //create new object
      Node copier = head; //node to traverse the queue
      T value; //to store the value of the node temporarily
      do {
         value = copier.value; //grab next value
         copyQ.enqueue(value); //add it to the new queue
         copier = copier.next; //progress through queue
      } while(copier != null); //until the end is reached
      return copyQ;
   }

   /**
    * This method returns whether the queue is empty
    * @return boolean whether it is empty
    */
   public boolean empty() {
      return head == null;
   }
   /**
    * Returns the item at front(head) of queue.
    * This isn't used in this project, but I consider it a core method for the
    * queue class, so I am leaving it in.
    * @return item at head of queue (the first thing in)
    * @throws IllegalArgumentException user tries to peek at an empty queue
    */
   public T peek() {
      if (head == null)  //when user tries to peek at an empty queue
         throw new IllegalArgumentException("empty");
      else {
         return head.value;
      }
   }

   /**
    * This method removes and returns the item at the head of the queue
    * @return item at the head
    * @throws EmptyStackException When the queue is empty
    */
   public T dequeue() {
      if (empty())
         throw new IllegalArgumentException("empty queue");
      else {
         T value = head.value; //store value
         if(head == tail) { //if it is the last item in the queue
            head = null;
            tail = null;
         } else {
            head = head.next; //move head
            head.prev = null; //remove old head
         }
         return value;
      }
   }
   /**
    * The enqueue method adds a value to the queue
    * @param nextInput The RenderCommand to be added to the queue
    */
   public void enqueue(T nextInput) {
      Node newTail = new Node(nextInput, null, null); //create new node
      //update tail
      if(head == null) { //if first node
         head = newTail; //update head
      } else {
         tail.next = newTail; //add new node to tail
         newTail.prev = tail; //update prev reference
      }
      tail = newTail; //update tail
   }

   /**
    * The append method appends a RenderQueue object onto the current
    * RenderQueue object. It uses a deep copy to not alter the queue it is
    * passed
    * @param queue The queue to be appended to this queue
    */
   public void append(Queue<T> queue) {
      Queue<T> tempQ = queue.copy(); //make a deep copy
      while(tempQ.head != null) { //until you reach the end
         this.enqueue(tempQ.head.value); //add the head from the copy
         tempQ.head = tempQ.head.next; //move the head
      }
   }

   /**
    * The toString method essentially does the opposite of the fromString
    * method. It creates a String object from the values in the queue in the
    * same traditional L-system format that fromString reads in. It does this
    * using a switch statement. It defaults to F as the fromString method
    * does as well.
    * @return The string representation of the commands in the queue
    */
   public String toString() {
      StringBuilder sBuilder = new StringBuilder(); //create string builder
      sBuilder.append("| ");
      Node copier = this.head; //node to traverse the queue
      while(copier != null) { //until the end is reached
         sBuilder.append(copier.value); //append char to string
         sBuilder.append(" | ");
         copier = copier.next; //update traversal node
      }
      return sBuilder.toString();
   }

   /**
    * This method checks whether two Generic Queues are equivalent.
    * This isn't used in this project, but I consider it a core method for the
    * queue class, so I am leaving it in.
    * @param otherQ The second queue to compare to this one
    * @return whether they are equal
    */
   public boolean equals(Queue<T> otherQ) {
      if (otherQ == null || otherQ.empty()) {
         return false;
      }
      Node p = head;
      Node p2 = otherQ.head;

      while(p != null && p2 != null) {
         if(p.value != p2.value) {
            return false;
         }
         p = p.next;
         p2 = p2.next;
      }
      return (p == null && p2 == null);
   }
}

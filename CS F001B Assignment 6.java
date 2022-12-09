
// Assignment 6 - Queues Using Inheritance 
// by Jennifer Tran and Lei Woods
import java.lang.Exception;
import java.text.*;
import java.util.*;

public class Foothill
{
   public static void main(String[] args)
   {
      CardQueue cardQ = new CardQueue();

      // saw this on the intergeration test
      cardQ.addCard(new Card('A', Card.Suit.spades));
      cardQ.addCard(new Card('2', Card.Suit.hearts));
      cardQ.addCard(new Card('3', Card.Suit.hearts));
      cardQ.addCard(new Card('4', Card.Suit.clubs));

      // remove from queue
      try
      {
         for (int k = 0; k < 6; k++)
         {
            System.out.print(cardQ.removeCard() + "   ");
         }
         System.out.println();
      } catch (QueueEmptyException ex)
      {
      }
      /* what it prints out:
       * A of spades   2 of hearts   3 of hearts   4 of clubs   
       */
   }
}

// the base class Node
class Node
{
   // variable
   protected Node next;

   // the constructor
   public Node()
   {
      next = null;
   }

   // return string
   public String toString()
   {
      return "(generic node)";
   }
}

// the Queue
class Queue
{
   // variables for the beginning and end.
   private Node head;
   private Node tail;

   // constructors
   public Queue()
   {
      head = null;
      tail = null;
   }

   // addNode
   public boolean add(Node newNode)
   {
      if (newNode == null)
         return false;
      if (head == null)
      {
         head = newNode;
         tail = newNode;
      } else
      {
         tail.next = newNode;
         tail = newNode;
      }
      return true;
   }

   // removeNode
   public Node remove() throws QueueEmptyException
   {
      Node temporary;

      temporary = head;
      if (head != null)
      {
         head = head.next;
         temporary.next = null;
      } else
      {
         throw new QueueEmptyException();
      }
      return temporary;
   }

   // toString
   public String toString()
   {
      Node stringNode;
      String retVal = "";
      // Display all nodes in queue from oldest to newest
      for (stringNode = head; stringNode != null; stringNode = stringNode.next)
      {
         retVal = retVal + stringNode + "   ";
      }
      return retVal;
   }
}

// the ExceptionQueueEmpty
class QueueEmptyException extends Exception
{
   QueueEmptyException()
   {
      super("Queue Empty");
   }
}

// the CardNode
class CardNode extends Node
{
   // the variables
   private Card card;

   // constructors
   public CardNode(char value, Card.Suit suit)
   {
      super();
      card = new Card(value, suit);
   }

   // accessors
   public Card getCard()
   {
      return card;
   }

   // toString
   public String toString()
   {
      return card.toString();
   }
}

// the Card Queue
class CardQueue extends Queue
{

   // constructor
   CardQueue()
   {
      super();
   }

   // addCard
   public void addCard(char value, Card.Suit suit)
   {
      CardNode NodeofCard = new CardNode(value, suit);
      super.add(NodeofCard);
   }

   // addCard when you get a card
   public void addCard(Card card)
   {
      if (card != null)
      {
         this.addCard(card.getValue(), card.getSuit());
      }
   }

   // removeCard
   public Card removeCard() throws QueueEmptyException
   {
      CardNode NodeofCard = (CardNode) remove();
      return NodeofCard.getCard();
   }
}

// the Card
class Card
{
   public enum State
   {
      removed, alive
   }

   public enum Suit
   {
      clubs, diamonds, hearts, spades
   }

   public static char[] rankValue =
   { '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A', 'X' };
   public static Suit[] rankSuit =
   { Suit.clubs, Suit.diamonds, Suit.hearts, Suit.spades };

   // private data
   private char value;
   private Suit suit;
   private boolean errorFlag;
   private State state;
   private static final Suit DefaultSuit = Suit.spades;
   private static final char DefaultCard = 'A';
   private static int numOrder = 13;

   // Constructors

   // Card() constructs when given value and suit
   public Card(char value, Suit suit)
   {
      set(value, suit);
   }

   // When given only value
   public Card(char value)
   {
      this(value, DefaultSuit);
   }

   // default constructor
   public Card()
   {
      this(DefaultCard, DefaultSuit);
   }

   // copy constructor
   public Card(Card card)
   {
      set(card.value, card.suit);
   }

   // Private method to see if returns are true or false
   private static boolean isValid(char value, Suit suit)
   {
      char upVal = Character.toUpperCase(value);
      return (upVal == 'A' || upVal == 'K' || upVal == 'Q' || upVal == 'J' || upVal == 'T'
            || (upVal >= '2' && upVal <= '9'));
   }

   // mutator
   public boolean set(char value, Suit suit)
   {
      this.errorFlag = !this.isValid(value, suit);
      char upVal = Character.toUpperCase(value);
      this.suit = suit;
      this.value = upVal;
      return !this.errorFlag;
   }

   public void setState(State state)
   {
      this.state = state;
   }

   // accessors
   public Suit getSuit()
   {
      return suit;
   }

   public boolean isErrorFlag()
   {
      return errorFlag;
   }

   public char getValue()
   {
      return value;
   }

   public State getState()
   {
      return state;
   }

   // checks to see if this card is equal to their card
   public boolean equals(Card card)
   {
      if (this.errorFlag != card.isErrorFlag())
      {
         return false;
      }
      if (this.suit != card.getSuit())
      {
         return false;
      }
      if (this.value != card.getValue())
      {
         return false;
      }
      return true;
   }

   // compares card
   public int compareCard(Card othercard)
   {
      if (this.value == othercard.value)
      {
         return (getRankofSuit(this.suit) - getRankofSuit(othercard.suit));
      }
      return (getRankofValue(this.value) - getRankofValue(othercard.value));
   }

   // looks at rank of card
   public static void setOrderofRank(char[] valueOrder, Suit[] suitOrder, int numOrder)
   {
      int rank;
      if (numOrder < 0 || numOrder > 13)
      {
         return;
      }
      Card.numOrder = numOrder;
      for (rank = 0; rank < 4; rank++)
         Card.rankValue[rank] = valueOrder[rank];
      for (rank = 0; rank < 4; rank++)
         Card.rankSuit[rank] = suitOrder[rank];
   }

   // gets suit rank
   public static int getRankofSuit(Suit s)
   {
      for (int i = 0; i < 4; i++)
         if (rankSuit[i] == s)
         {
            return i;
         }
      return 0;
   }

   // gets value rank
   public static int getRankofValue(char v)
   {
      for (int i = 0; i < numOrder; i++)
         if (rankValue[i] == v)
         {
            return i;
         }
      return 0;
   }

   // looks into array
   public static void sortArray(Card[] array, int sizeArray)
   {
      for (int i = 0; i < sizeArray; i++)
         if (!floatToTop(array, sizeArray - 1 - i))
         {
            return;
         }
   }

   private static boolean floatToTop(Card[] array, int head)
   {
      boolean see = false;
      Card temp;
      for (int i = 0; i < head; i++)
         if (array[i].compareCard(array[i + 1]) > 0)
         {
            temp = array[i];
            array[i] = array[i + 1];
            array[i + 1] = temp;
            see = true;
         }
      return see;
   }

   // stringizer
   public String toString()
   {
      if (this.errorFlag == true)
      {
         return "** illegal **";
      } else
      {
         String retVal;
         retVal = String.valueOf(value) + " of " + suit;
         return retVal;
      }
   }
}

// the Deck
class Deck
{
   // private values
   private int numPacks;
   private Card[] cards;
   private int topCard;
   private final int MAX_PACKS = 6;
   private static int NUM_CARDS_PER_PACK = 52;
   private int MAX_CARDS_PER_DECK = MAX_PACKS * NUM_CARDS_PER_PACK;
   private static Card[] masterPack;

   // constructor
   public Deck(int numPacks)
   {
      this.numPacks = numPacks;
      allocateMasterPack();
      init(numPacks);
   }

   // default deck
   public Deck()
   {
      this(1);
   }

   // helper
   private static void allocateMasterPack()
   {
      masterPack = new Card[NUM_CARDS_PER_PACK];
      String values = "23456789TJQKA";
      Card.Suit[] suitArray =
      { Card.Suit.clubs, Card.Suit.diamonds, Card.Suit.hearts, Card.Suit.spades };
      for (int suitIndex = 0; suitIndex < 4; suitIndex++)
      {
         for (int valueIndex = 0; valueIndex < values.length(); valueIndex++)
         {
            masterPack[valueIndex + suitIndex * values.length()] = new Card(values.charAt(valueIndex),
                  suitArray[suitIndex]);
         }
      }
   }

   // initializes the deck
   public boolean init(int numPacks)
   {
      // checking to see if numPacks is a legal number
      if (numPacks <= 0 || numPacks > MAX_PACKS)
      {
         return false;
      }
      cards = new Card[NUM_CARDS_PER_PACK * numPacks];
      for (int i = 0; i < cards.length; i++)
      {
         cards[i] = masterPack[i % NUM_CARDS_PER_PACK];
      }
      topCard = numPacks * NUM_CARDS_PER_PACK;
      return true;
   }

   // method to shuffle deck
   public void shuffle()
   {
      Random randomShuffle = new Random();
      for (int i = 0; i < cards.length; i++)
      {
         Card storedCard = cards[i];
         int RanIndex = randomShuffle.nextInt(cards.length);
         cards[i] = cards[RanIndex];
         cards[RanIndex] = storedCard;
      }
   }

   // method that gives a deals a card
   public Card dealCard()
   {
      if (topCard == 0)
      {
         Card Faker = new Card('M', Card.Suit.spades);
         return Faker;
      }
      topCard--;
      Card Copy = new Card(cards[topCard]);
      return Copy;
   }

   public int getNumCards()
   {
      return topCard;
   }

   // looks at card k and see if its an error or not.
   public Card inspectCard(int k)
   {
      if (k < 0 || k >= topCard)
      {
         Card Error = new Card('N', Card.Suit.clubs);
         return Error;
      }
      return cards[k];
   }

   // stringizer
   public String toString()
   {
      String str = "";
      for (int i = 0; i < topCard; i++)
      {
         str += cards[i].toString();
         if (i != topCard - 1)
         {
            str += ", ";
         }
      }
      return str;
   }
}

// the Hand
class Hand
{
   // MAX_CARDS is an integer that tells us what is the max amount of cards we can
   // have
   private static int MAX_CARDS = 30;
   // myCards is an array that holds the value of our cards
   public Card[] myCards;
   // numCards is an integer that changes as our number of cards increase or
   // decrease
   public int numCards;

   // constructor
   public Hand()
   {
      this.resetHand();
   }

   // resetHand() replaces the array with a new array
   public void resetHand()
   {
      numCards = 0;
      myCards = new Card[MAX_CARDS];
   }

   // takeCard() takes in a card a puts in your array
   public boolean takeCard(Card card)
   {
      if (numCards >= MAX_CARDS)
      {
         return false;
      }
      if (card.isErrorFlag())
      {
         return true;
      }
      myCards[numCards] = new Card(card);
      numCards++;
      return true;
   }

   // plays the last card in your hand/array
   public Card playCard()
   {
      if (numCards == 0)
      {
         Card End = new Card('X', Card.Suit.hearts);
         return End;
      }
      numCards--;
      return myCards[numCards];
   }

   // takes the values in your array and converts then in a string
   public String toString()
   {
      String handString = "";
      for (int i = 0; i < numCards; i++)
      {
         handString += myCards[i].toString();
         if (i != numCards - 1)
         {
            handString += ", ";
         }
      }
      return handString;
   }

   // accessor for NumCards()
   public int getNumCards()
   {
      return numCards;
   }

   // method that looks into myCards at index k
   public Card inspectCard(int k)
   {
      if (k < 0 || k >= numCards)
      {
         Card Error = new Card('M', Card.Suit.clubs);
         return Error;
      }
      return myCards[k];
   }
}

//Main Class Phase 1
class FoothillPhase1
{
   static final String SEPARATOR = "\n";
   
   public static void main(String[] args)
   {
      
      Card card1, card2, card3;
      
      card1 = new Card();
      card2 = new Card('x');
      card3 = new Card('T', Card.Suit.diamonds);
      
      String cards = card1.toString() + SEPARATOR + card2.toString() 
      + SEPARATOR + card3.toString();
      
      System.out.println(cards + SEPARATOR);
      
      //New Cards
      card1.set('M', Card.Suit.hearts);
      card2.set('K', Card.Suit.clubs);
      
      System.out.println(cards);
      
   }

}

// Card Class
class Card
{ // card suit enums
   static final Suit DEFAULT_SUIT = Suit.spades;
   static final char DEFAULT_CARD = 'A';
   
   public enum Suit
   {
      clubs, diamonds, hearts, spades
   }

   // private data
   private char value;
   private Suit suit;

   // error flag
   private boolean errorFlag;

   // 4 overloaded constructors
   public Card(char value, Suit suit)
   {
      set(value, suit);
   }
   
   public Card(char value)
   {
      this(value, DEFAULT_SUIT);
   }
   public Card()
   {
      this(DEFAULT_CARD, DEFAULT_SUIT);
   }

   // copy constructor
   public Card(Card card)
   {
      this.suit = card.suit;
      this.value = card.value;
   }

   // mutator
   public boolean set(char value, Suit suit)
   {

      char upVal;

      // convert to uppercase
      upVal = Character.toUpperCase(value);

      if (!isValid(upVal, suit))
      {
         errorFlag = true;
         this.value = upVal;
         this.suit = suit;
         return false;
      }

      else
      {
         errorFlag = false;
         this.value = upVal;
         this.suit = suit;
         return true;

      }
   }

   // accessors
   public char getValue()
   {
      return value;
   }

   public Suit getSuit()
   {
      return suit;
   }
   
   public boolean isErrorFlag()
   {
      return errorFlag;
   }

   public boolean equals(Card card)
   {
      if (this.value != card.getValue())
         return false;
      if (this.suit != card.getSuit())
         return false;
      if (this.errorFlag != card.isErrorFlag())
         return false;
      return true;
   }
   
   //Stringizer
   public String toString()
   {
      String cardVal;
      if (errorFlag)
         return "[ Invalid ]";
      else
         cardVal = String.valueOf(value) + " of " + suit;
      return cardVal;
   }
   
//Creating isValid helper
   private static boolean isValid(char value, Suit suit)
   {
      char upVal;

      // upVal needs to be initialized again. It does not
      // carry over from public boolean set(char value, Suit suit)
      upVal = Character.toUpperCase(value);

      // validity parameters (learned from Suits as enums)
      if (upVal == 'A' || upVal == 'K' || upVal == 'Q' || upVal == 'J'
            || upVal == 'T' || (upVal >= '2' && upVal <= '9'))
         return true;
      else
         return false;
   }

}

/* ---------------------Phase 1 Card Class Runs---------------------
 * A of spades
Invalid
T of diamonds

A of spades
Invalid
T of diamonds
------------------------------------------------------------------- */


// Assignment 1 Phase 2


public class Foothill
{
   static final String SEPARATOR = "\n";

   public static void main(String[] args)
   {
      Card card1, card2, card3;

      card1 = new Card('6', Card.Suit.hearts);
      card2 = new Card('A', Card.Suit.clubs);
      card3 = new Card('T', Card.Suit.diamonds);

      Hand hand = new Hand();

      // full hand tests
      while (true)
      {
         if (!hand.takeCard(card1))
         {
            System.out.println("Hand Full");
            break;
         }
         if (!hand.takeCard(card2))
         {
            System.out.println("Hand Full");
            break;
         }
         if (!hand.takeCard(card3))
         {
            System.out.println("Hand full");
            break;
         }
      }

      // print hand
      System.out
            .println("After deal" + SEPARATOR + hand.toString() + SEPARATOR);

      // System print for inspect card
      System.out.println("Testing inspectCard()");
      System.out.println(hand.inspectCard(7).toString());
      System.out.println(hand.inspectCard(55).toString());

      while (hand.getNumCards() > 0)
         System.out.println("Playing " + hand.playCard().toString());

      System.out.println(SEPARATOR + "After playing all cards" + SEPARATOR
            + hand.toString());
   }
}


class Hand
{
   public static final int MAX_CARDS = 35;

   // Private Data
   private Card[] myCards;
   private int numCards;

   // Hand Constructor
   public Hand()
   {
      myCards = new Card[MAX_CARDS];
      resetHand();
   }

   // Mutators for Hand
   public void resetHand()
   {
      numCards = 0;
   }

   public boolean takeCard(Card originalCard)
   {
      if (MAX_CARDS <= numCards)
         return false;

      if (myCards[numCards] == null)
         myCards[numCards] = new Card();

      myCards[numCards++].set(originalCard.getValue(), originalCard.getSuit());
      return true;
   }

   public Card playCard()
   {
      Card errorCard = new Card('x');

      if (numCards == 0)
         return errorCard;
      else
         return myCards[--numCards];
   }

   // string to print out hand
   public String toString()
   {
      int cardNum;
      String cardVal = "Hand = (";

      for (cardNum = 0; cardNum < numCards; cardNum++)
      {
         cardVal += myCards[cardNum].toString();
         if (cardNum < numCards - 1)
            cardVal += ", ";
      }

      cardVal += " )";
      return cardVal;
   }

   // accessor for numcards
   int getNumCards()
   {
      return numCards;
   }

   Card inspectCard(int k)
   {
      Card errorCard = new Card('x');

      if (k < 0 || k >= numCards)
         return errorCard;
      else
         return myCards[k];
   }
}



/*
 * -------------Phase 2 runs --------------- Hand full After deal Hand = (6 of
 * hearts, A of clubs, T of diamonds, 6 of hearts, A of clubs, T of diamonds, 6
 * of hearts, A of clubs, T of diamonds, 6 of hearts, A of clubs, T of diamonds,
 * 6 of hearts, A of clubs, T of diamonds, 6 of hearts, A of clubs, T of
 * diamonds, 6 of hearts, A of clubs, T of diamonds, 6 of hearts, A of clubs, T
 * of diamonds, 6 of hearts, A of clubs, T of diamonds, 6 of hearts, A of clubs,
 * T of diamonds, 6 of hearts, A of clubs, T of diamonds, 6 of hearts, A of
 * clubs )
 * 
Testing inspectCard()
A of clubs
Invalid
Playing A of clubs
Playing 6 of hearts
Playing T of diamonds
Playing A of clubs
Playing 6 of hearts
Playing T of diamonds
Playing A of clubs
Playing 6 of hearts
Playing T of diamonds
Playing A of clubs
Playing 6 of hearts
Playing T of diamonds
Playing A of clubs
Playing 6 of hearts
Playing T of diamonds
Playing A of clubs
Playing 6 of hearts
Playing T of diamonds
Playing A of clubs
Playing 6 of hearts
Playing T of diamonds
Playing A of clubs
Playing 6 of hearts
Playing T of diamonds
Playing A of clubs
Playing 6 of hearts
Playing T of diamonds
Playing A of clubs
Playing 6 of hearts
Playing T of diamonds
Playing A of clubs
Playing 6 of hearts
Playing T of diamonds
Playing A of clubs
Playing 6 of hearts

After playing all cards
Hand = ( )
 * 
 * -----------------------------
 */


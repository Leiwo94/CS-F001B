import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * 
 * @author Micheal Loceff
 * @author Baba Kofi Weusijana
 * @author Jennifer Tran
 * @author Lei Woods
 */
public class Foothill
{
   // static variables 
   static int NUM_CARDS_PER_HAND = 7;
   static int NUM_PLAYERS = 2;
   static JLabel[] computerLabels = new JLabel[NUM_CARDS_PER_HAND];
   static GUICard[] humanGUICards = new GUICard[NUM_CARDS_PER_HAND];
   static GUICard[] playedCards = new GUICard[NUM_PLAYERS];
   static JLabel[] playLabelText = new JLabel[NUM_PLAYERS];

   //main class
   public static void main(String[] args)
   {
      int k;
     
      // establish main frame in which program will run
      CardTable myCardTable = new CardTable("CS 1B CardTable", NUM_CARDS_PER_HAND, NUM_PLAYERS);
      myCardTable.setName("CS 1B CardTable");
      myCardTable.setSize(1024, 768);
      myCardTable.setLocationRelativeTo(null);
      myCardTable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      // show everything to the user
      myCardTable.setVisible(true);
      
      // ActionListener inner class for humanButtons
      class HumanGUICardActionListener implements ActionListener
      {
         // event handler for JButton mouse click or space key
         public void actionPerformed(ActionEvent e)
         {
            GUICard button = (GUICard) e.getSource();
            System.out.println(button);
            myCardTable.pnlHumanHand.remove(button);
            myCardTable.pnlHumanHand.repaint();
            myCardTable.pnlHumanHand.doLayout();
            button.setEnabled(false);
            myCardTable.pnlHumanArea.removeAll();
            myCardTable.pnlHumanArea.add(button);
            myCardTable.pnlHumanArea.doLayout();
         }
      }
      
      // prepare the human player's array of GUICards (bottom region)
      for (k = 0; k < myCardTable.getNumCardsPerHand(); ++k)
      {
         humanGUICards[k] = new GUICard(generateRandomCard());
         System.out.println(humanGUICards[k]);
         humanGUICards[k].addActionListener(new HumanGUICardActionListener());
      }
      
      // prepare the computer label array (all card backs, top region)
      for (k = 0; k < NUM_CARDS_PER_HAND; ++k)
      {
         computerLabels[k] = new JLabel(GUICard.getBackCardIcon());
      }

      // prepare the played-card GUICard array with jokers
      for (k = 0; k < NUM_PLAYERS; ++k)
      {
         playedCards[k] = new GUICard('X', Card.Suit.clubs);
         playedCards[k].setEnabled(false);
      }

      // ADD LABELS TO PANELS -----------------------------------------
      for (k = 0; k < NUM_CARDS_PER_HAND; ++k)
      {
         myCardTable.pnlComputerHand.add(computerLabels[k]);
      }
      for (k = 0; k < NUM_CARDS_PER_HAND; ++k)
      {
         myCardTable.pnlHumanHand.add(humanGUICards[k]);
      }

      // and two random cards in the play region (simulating a computer/hum ply)
      for (k = 0; k < NUM_PLAYERS; ++k)
      {
         myCardTable.pnlComputerArea.add(playedCards[k]);
         ++k;
         myCardTable.pnlHumanArea.add(playedCards[k]);
      }

      // show everything to the user
      myCardTable.setVisible(true);
   }

   // gets a random int for suit and value and uses that to return a random card 
   static Card generateRandomCard()
   {
      Card.Suit randomSuit = GUICard.turnIntIntoSuit((int) (Math.random() * 4));
      char randomValue = GUICard.turnIntIntoCardValueChar((int) (Math.random() * 14));
      return new Card(randomValue, randomSuit);
      
      
   }

}

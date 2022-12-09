//Assignment 7 - Lei Woods and Brianna Rogers
//------------------------------------------------------
public class Foothill {
	// ------- main --------------
	public static void main(String[] args) throws Exception {
		FHsdTree<String> sceneTree = new FHsdTree<String>();
		FHsdTreeNode<String> tn;

		System.out.println("Starting tree empty? " + sceneTree.empty() + "\n");
		// create a scene in a room
		tn = sceneTree.addChild(null, "room");

		// add three objects to the scene tree
		sceneTree.addChild(tn, "Lily the canine");
		sceneTree.addChild(tn, "Miguel the human");
		sceneTree.addChild(tn, "table");
		// add some parts to Miguel
		tn = sceneTree.find("Miguel the human");

		// Miguel's left arm
		tn = sceneTree.addChild(tn, "torso");
		tn = sceneTree.addChild(tn, "left arm");
		tn = sceneTree.addChild(tn, "left hand");
		sceneTree.addChild(tn, "thumb");
		sceneTree.addChild(tn, "index finger");
		sceneTree.addChild(tn, "middle finger");
		sceneTree.addChild(tn, "ring finger");
		sceneTree.addChild(tn, "pinky");

		// Miguel's right arm
		tn = sceneTree.find("Miguel the human");
		tn = sceneTree.find(tn, "torso", 0);
		tn = sceneTree.addChild(tn, "right arm");
		tn = sceneTree.addChild(tn, "right hand");
		sceneTree.addChild(tn, "thumb");
		sceneTree.addChild(tn, "index finger");
		sceneTree.addChild(tn, "middle finger");
		sceneTree.addChild(tn, "ring finger");
		sceneTree.addChild(tn, "pinky");

		// add some parts to Lily
		tn = sceneTree.find("Lily the canine");
		tn = sceneTree.addChild(tn, "torso");
		sceneTree.addChild(tn, "right front paw");
		sceneTree.addChild(tn, "left front paw");
		sceneTree.addChild(tn, "right rear paw");
		sceneTree.addChild(tn, "left rear paw");
		sceneTree.addChild(tn, "spare mutant paw");
		sceneTree.addChild(tn, "wagging tail");

		// add some parts to table
		tn = sceneTree.find("table");
		sceneTree.addChild(tn, "north east leg");
		sceneTree.addChild(tn, "north west leg");
		sceneTree.addChild(tn, "south east leg");
		sceneTree.addChild(tn, "south west leg");

		System.out.println("\n------------ Loaded Tree ----------------- \n");
		sceneTree.display();

		System.out.println("\nRemoving a few things... ");
		sceneTree.remove("spare mutant paw");
		sceneTree.remove("Miguel the human");
		sceneTree.remove("an imagined higgs boson");

		System.out.println("\n------------ Virtual (soft) Tree --------------- \n");
		sceneTree.display();

		System.out.println("\n----------- Physical (hard) Display ------------- \n");
		sceneTree.displayPhysical();

		System.out.println("\n------- Testing Sizes (compare with above) -------- \n");
		System.out.println("virtual (soft) size: " + sceneTree.size());
		System.out.println("physical (hard) size: " + sceneTree.sizePhysical());

		System.out.println("\n------------ Collecting Garbage ---------------- \n");
		System.out.println("found soft-deleted nodes? " + sceneTree.collectGarbage());
		System.out.println("immediate collect again? " + sceneTree.collectGarbage());

		System.out.println("\n--------- Hard Display after garb col ------------ \n");

		sceneTree.displayPhysical();
		System.out.println("\nvirtual (soft) size: " + sceneTree.size());
		System.out.println("physical (hard) size: " + sceneTree.sizePhysical());

		System.out.println("Semi-deleted tree empty? " + sceneTree.empty() + "\n");
		sceneTree.remove("room");

		System.out.println("virtual (soft) size: " + sceneTree.size());
		System.out.println("physical (hard) size: " + sceneTree.sizePhysical());

		System.out.println("Completely-deleted tree empty? " + sceneTree.empty() + "\n");
		sceneTree.displayPhysical();
		sceneTree.collectGarbage();
		sceneTree.display();
		sceneTree.displayPhysical();

	}
}

/* ------------------ RUN ------------------
Starting tree empty? true


------------ Loaded Tree ----------------- 

room
 table
  south west leg
  south east leg
  north west leg
  north east leg
 Miguel the human
  torso
   right arm
    right hand
     pinky
     ring finger
     middle finger
     index finger
     thumb
   left arm
    left hand
     pinky
     ring finger
     middle finger
     index finger
     thumb
 Lily the canine
  torso
   wagging tail
   spare mutant paw
   left rear paw
   right rear paw
   left front paw
   right front paw

Removing a few things... 

------------ Virtual (soft) Tree --------------- 

room
 table
  south west leg
  south east leg
  north west leg
  north east leg
 Lily the canine
  torso
   wagging tail
   left rear paw
   right rear paw
   left front paw
   right front paw

----------- Physical (hard) Display ------------- 

room
 table
  south west leg
  south east leg
  north west leg
  north east leg
 Miguel the human (D)
  torso
   right arm
    right hand
     pinky
     ring finger
     middle finger
     index finger
     thumb
   left arm
    left hand
     pinky
     ring finger
     middle finger
     index finger
     thumb
 Lily the canine
  torso
   wagging tail
   spare mutant paw (D)
   left rear paw
   right rear paw
   left front paw
   right front paw

------- Testing Sizes (compare with above) -------- 

virtual (soft) size: 13
physical (hard) size: 30

------------ Collecting Garbage ---------------- 

found soft-deleted nodes? true
immediate collect again? false

--------- Hard Display after garb col ------------ 

room
 table
  south west leg
  south east leg
  north west leg
  north east leg
 Lily the canine
  torso
   wagging tail
   left rear paw
   right rear paw
   left front paw
   right front paw

virtual (soft) size: 13
physical (hard) size: 13
Semi-deleted tree empty? false

virtual (soft) size: 0
physical (hard) size: 13
Completely-deleted tree empty? true

room (D)
 table
  south west leg
  south east leg
  north west leg
  north east leg
 Lily the canine
  torso
   wagging tail
   left rear paw
   right rear paw
   left front paw
   right front paw
   
 -------------------------------------*/


// FHsdTree File
// package statement only for use in CS 1C.
// leave commented out for CS 1B:
// package cs_1c;

class FHsdTree<E> implements Cloneable {
	private int mSize;

	FHsdTreeNode<E> mRoot;

	public FHsdTree() {
		clear();
	}

	public boolean empty() {
		return (size(mRoot, 0) == 0);
	}

	public int sizePhysical() {
		return mSize;
	}

	// New Size methods that start initializing size and soft size.
	// Soft size is passed down along the final size method to start
	// counting the first parent, the child and children of that child
	// and the siblings.

	public int size() {
		return size(mRoot, 0);
	}

	public int size(FHsdTreeNode<E> mRoot) {
		return size(mRoot, 0);
	}

	public int size(FHsdTreeNode<E> root, int softSize) {

		if (root == null) {
			return softSize;
		}

		if (!root.deleted) {
			++softSize;
			softSize = size(root.firstChild, softSize);
		}

		softSize = size(root.sib, softSize);
		return softSize;
	}

	public void clear() {
		mSize = 0;
		mRoot = null;
	}

	public FHsdTreeNode<E> find(E x) {
		return find(mRoot, x, 0);
	}

	public boolean remove(E x) {
		return remove(mRoot, x);
	}

	public void display() {
		display(mRoot, 0);
	}

	public <F extends Traverser<? super E>> void traverse(F func) {
		traverse(func, mRoot, 0);
	}

	public FHsdTreeNode<E> addChild(FHsdTreeNode<E> treeNode, E x) {
		// empty tree? - create a root node if user passes in null
		if (mSize == 0) {
			if (treeNode != null)
				return null; // error something's fishy. treeNode can't right

			mRoot = new FHsdTreeNode<E>(x, null, null, null, false);
			mRoot.myRoot = mRoot;
			mSize = 1;
			return mRoot;
		}
		if (treeNode == null)
			return null; // error inserting into non_null tree with a null parent
		if (treeNode.myRoot != mRoot)
			return null; // silent error, node does not belong to this tree
		if (treeNode.deleted == true)
			return null; // Checking to see if tree node has been deleted and
							// returning null to reject
							// add.

		// push this node into the head of the sibling list; adjust prev pointers
		FHsdTreeNode<E> newNode = new FHsdTreeNode<E>(x, treeNode.firstChild, null, treeNode, mRoot, false); // sb,
		// chld,
		// prv,
		// rt
		treeNode.firstChild = newNode;
		if (newNode.sib != null)
			newNode.sib.prev = newNode;
		++mSize;
		return newNode;
	}

	public FHsdTreeNode<E> find(FHsdTreeNode<E> root, E x, int level) {
		FHsdTreeNode<E> retval;

		if (mSize == 0 || root == null || root.deleted == true)
			return null;

		if (root.data.equals(x))
			return root;

		// otherwise, recurse. don't process sibs if this was the original call
		if (level > 0 && (retval = find(root.sib, x, level)) != null)
			return retval;
		return find(root.firstChild, x, ++level);
	}

	public boolean remove(FHsdTreeNode<E> root, E x) {
		FHsdTreeNode<E> tn = null;

		if (mSize == 0 || root == null || root.deleted == true)
			return false;

		if ((tn = find(root, x, 0)) != null) {
			// removeNode(tn);
			tn.deleted = true;
			// mSize--;
			return true;
		}
		return false;
	}

	private void removeNode(FHsdTreeNode<E> nodeToDelete) {
		if (nodeToDelete == null || mRoot == null)
			return;
		if (nodeToDelete.myRoot != mRoot)
			return; // silent error, node does not belong to this tree
		--mSize;
		// remove all the children of this node
		while (nodeToDelete.firstChild != null) {
			removeNode(nodeToDelete.firstChild);
		}

		if (nodeToDelete.prev == null)
			mRoot = null; // last node in tree

		else if (nodeToDelete.prev.sib == nodeToDelete) {
			nodeToDelete.prev.sib = nodeToDelete.sib; // adjust left sibling
		}

		else {
			nodeToDelete.prev.firstChild = nodeToDelete.sib; // adjust parent
		}
		// adjust the successor sib's prev pointer
		if (nodeToDelete.sib != null) {
			nodeToDelete.sib.prev = nodeToDelete.prev;
		}
	}

	public boolean collectGarbage() {
		return collectGarbage(mRoot);
	}

	public boolean collectGarbage(FHsdTreeNode<E> treeNode) {

		int initSize = sizePhysical();

		for (FHsdTreeNode<E> i = treeNode; i != null; i = i.sib) {
			if (i.deleted) {

				removeNode(i);

			}

			collectGarbage(i.firstChild);
		}

		return initSize != sizePhysical();
	}

	public Object clone() throws CloneNotSupportedException {
		FHsdTree<E> newObject = (FHsdTree<E>) super.clone();
		newObject.clear(); // can't point to other's data

		newObject.mRoot = cloneSubtree(mRoot);
		newObject.mSize = mSize;
		newObject.setMyRoots(newObject.mRoot);

		return newObject;
	}

	private FHsdTreeNode<E> cloneSubtree(FHsdTreeNode<E> root) {
		FHsdTreeNode<E> newNode;
		if (root == null)
			return null;

		// does not set myRoot which must be done by caller
		newNode = new FHsdTreeNode<E>(root.data, cloneSubtree(root.sib), cloneSubtree(root.firstChild), null, false);

		// the prev pointer is set by parent recursive call ... this is the code:
		if (newNode.sib != null)
			newNode.sib.prev = newNode;
		if (newNode.firstChild != null)
			newNode.firstChild.prev = newNode;
		return newNode;
	}

	// recursively sets all myRoots to mRoot
	private void setMyRoots(FHsdTreeNode<E> treeNode) {
		if (treeNode == null)
			return;

		treeNode.myRoot = mRoot;
		setMyRoots(treeNode.sib);
		setMyRoots(treeNode.firstChild);
	}

	// define this as a static member so recursive display() does not need
	// a local version
	final static String blankString = "                                    ";

	// let be public so client can call on subtree

	public void display(FHsdTreeNode<E> treeNode, int level) {
		String indent;

		// stop runaway indentation/recursion
		if (level > (int) blankString.length() - 1) {
			System.out.println(blankString + " ... ");
			return;
		}

		if (treeNode == null)
			return;

		indent = blankString.substring(0, level);

		// pre-order processing done here ("visit")
		if (!treeNode.deleted)
			System.out.println(indent + treeNode.data);
		// recursive step done here

		if (!treeNode.deleted)
			display(treeNode.firstChild, level + 1);
		display(treeNode.sib, level);
	}

	public void displayPhysical() {
		displayPhysical(mRoot, 0);
	}

	public void displayPhysical(FHsdTreeNode<E> treeNode, int level) {
		String indent;

		// stop runaway indentation/recursion
		if (level > (int) blankString.length() - 1) {
			System.out.println(blankString + " ... ");
			return;
		}

		if (treeNode == null)
			return;

		indent = blankString.substring(0, level);

		// pre-order processing done here ("visit")
		System.out.print(indent + treeNode.data);

		if (treeNode.deleted == true)
			System.out.print(" (D)");
		System.out.println();

		// recursive step done here

		displayPhysical(treeNode.firstChild, level + 1);
		if (level > 0)
			displayPhysical(treeNode.sib, level);
	}

	// often helper of typical public version, but also callable by on subtree
	public <F extends Traverser<? super E>> void traverse(F func, FHsdTreeNode<E> treeNode, int level) {
		if (treeNode == null)
			return;

		if (!treeNode.deleted) {
			func.visit(treeNode.data);

			// recursive step done here
			traverse(func, treeNode.firstChild, level + 1);
		}

		if (level > 0)
			traverse(func, treeNode.sib, level);
	}
}

// FHsdTreeNode File
class FHsdTreeNode<E> {
	// use protected access so the tree, in the same package,
	// or derived classes can access members
	protected FHsdTreeNode<E> firstChild, sib, prev;
	protected E data;
	protected FHsdTreeNode<E> myRoot; // needed to test for certain error
	protected boolean deleted;

	public FHsdTreeNode(E d, FHsdTreeNode<E> sb, FHsdTreeNode<E> chld, FHsdTreeNode<E> prv, boolean isDeleted) {
		firstChild = chld;
		sib = sb;
		prev = prv;
		data = d;
		myRoot = null;
		deleted = isDeleted;
	}

	public FHsdTreeNode() {
		this(null, null, null, null, false);
	}

	public E getData() {
		return data;
	}

	// for use only by FHtree (default access)
	protected FHsdTreeNode(E d, FHsdTreeNode<E> sb, FHsdTreeNode<E> chld, FHsdTreeNode<E> prv, FHsdTreeNode<E> root,
			boolean isDeleted) {
		this(d, sb, chld, prv, isDeleted);
		myRoot = root;
	}
}

// Traverser File
interface Traverser<E> {
	public void visit(E x);
}

// Card Class
class Card {
	public enum State {
		removed, alive
	}

	public enum Suit {
		clubs, diamonds, hearts, spades
	}

	public static char[] rankValue = { '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A', 'X' };
	public static Suit[] rankSuit = { Suit.clubs, Suit.diamonds, Suit.hearts, Suit.spades };

	// private data
	private char value;
	private Suit suit;
	private boolean errorFlag;
	private State state;
	private static final Suit DEFAULT_SUIT = Suit.spades;
	private static final char DEFAULT_CARD = 'A';
	private static int numOrder = 13;

	// Constructors

	// Card() constructs when given value and suit
	public Card(char value, Suit suit) {
		set(value, suit);
	}

	// When given only value
	public Card(char value) {
		this(value, DEFAULT_SUIT);
	}

	// default constructor
	public Card() {
		this(DEFAULT_CARD, DEFAULT_SUIT);
	}

	// copy constructor
	public Card(Card card) {
		set(card.value, card.suit);
	}

	// Private method to see if returns are true or false
	private static boolean isValid(char value, Suit suit) {
		char upVal = Character.toUpperCase(value);
		return (upVal == 'A' || upVal == 'K' || upVal == 'Q' || upVal == 'J' || upVal == 'T'
				|| (upVal >= '2' && upVal <= '9'));
	}

	// mutator
	public boolean set(char value, Suit suit) {
		this.errorFlag = !this.isValid(value, suit);
		char upVal = Character.toUpperCase(value);
		this.suit = suit;
		this.value = upVal;
		return !this.errorFlag;
	}

	public void setState(State state) {
		this.state = state;
	}

	// accessors
	public Suit getSuit() {
		return suit;
	}

	public boolean isErrorFlag() {
		return errorFlag;
	}

	public char getValue() {
		return value;
	}

	public State getState() {
		return state;
	}

	// checks to see if this card is equal to their card
	public boolean equals(Card card) {
		if (this.errorFlag != card.isErrorFlag()) {
			return false;
		}
		if (this.suit != card.getSuit()) {
			return false;
		}
		if (this.value != card.getValue()) {
			return false;
		}
		return true;
	}

	// not finished
	public int compareCard(Card othercard) {
		if (this.value == othercard.value) {
			return (getRankofSuit(this.suit) - getRankofSuit(othercard.suit));
		}
		return (getRankofValue(this.value) - getRankofValue(othercard.value));
	}

	public static void setOrderofRank(char[] valueOrder, Suit[] suitOrder, int numOrder) {
		int rank;
		if (numOrder < 0 || numOrder > 13) {
			return;
		}
		Card.numOrder = numOrder;
		for (rank = 0; rank < 4; rank++)
			Card.rankValue[rank] = valueOrder[rank];
		for (rank = 0; rank < 4; rank++)
			Card.rankSuit[rank] = suitOrder[rank];
	}

	public static int getRankofSuit(Suit s) {
		for (int i = 0; i < 4; i++)
			if (rankSuit[i] == s) {
				return i;
			}
		return 0;
	}

	public static int getRankofValue(char v) {
		for (int i = 0; i < numOrder; i++)
			if (rankValue[i] == v) {
				return i;
			}
		return 0;
	}

	public static void sortArray(Card[] array, int sizeArray) {
		for (int i = 0; i < sizeArray; i++)
			if (!floatToTop(array, sizeArray - 1 - i)) {
				return;
			}
	}

	private static boolean floatToTop(Card[] array, int head) {
		boolean see = false;
		Card temp;
		for (int i = 0; i < head; i++)
			if (array[i].compareCard(array[i + 1]) > 0) {
				temp = array[i];
				array[i] = array[i + 1];
				array[i + 1] = temp;
				see = true;
			}
		return see;
	}

	// stringizer
	public String toString() {
		if (this.errorFlag == true) {
			return "** illegal **";
		} else {
			String retVal;
			retVal = String.valueOf(value) + " of " + suit;
			return retVal;
		}
	}
}
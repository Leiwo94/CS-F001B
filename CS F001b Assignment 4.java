/*
 * Lei Woods and Jennifer Tran
 * Assignment 3
 */
import java.util.Random;

public class Foothill
{
   public final static String SEPARATOR = "\n";

   public static void main(String[] args)
   {
      InternetUser internetUser1, internetUser2, internetUser3;
      internetUser1 = new InternetUser();
      internetUser2 = new InternetUser("Amy Goode", "32.66.95.84");
      internetUser3 = new InternetUser("bad", "1.2.3");

      System.out
            .println("Base Class Testing ***********************" + SEPARATOR);
      System.out.println(internetUser1.toString() + SEPARATOR
            + internetUser2.toString() + SEPARATOR + internetUser3.toString());

      // testing accessors
      System.out.println(
            "Testing getName Accessor ***********************" + SEPARATOR);
      System.out.println(internetUser2.getName() + SEPARATOR + SEPARATOR);

      System.out.println("Final Print ***********************" + SEPARATOR);
      // testing mutators
      internetUser3.setName("F");
      internetUser2.setIp("66.45.20");
      internetUser1.setName("Tony Stark");

      // Sample Print
      System.out.println(internetUser1.toString() + SEPARATOR
            + internetUser2.toString() + SEPARATOR + internetUser3.toString());

      // Communicator class test
      Communicator rae, pthyro, ydrielle, happy, boom, shinrael, elvis;
      rae = new Communicator(); // Default
      pthyro = new Communicator("Pthyro Druid", "72.54.93.147"); // Partial
      ydrielle = new Communicator("Ydrielle Priest", "23.65",
            EncryptionSupport.getSmallRandomPrime(),
            EncryptionSupport.getSmallRandomPrime()); // Full
      happy = new Communicator("Happy Fish", "102.36.99.01", 105, 66); // Error
                                                                       // 1
      boom = new Communicator("Boom Kenken", "80.08.80.08", 577, 1009); // Full
      shinrael = new Communicator("Shinrael Ghostcat", "11.35.407.79",
            EncryptionSupport.getMedSizedRandomPrime(),
            EncryptionSupport.getSmallRandomPrime()); // Full
      elvis = new Communicator("E", "56.3",
            EncryptionSupport.getSmallRandomPrime(),
            EncryptionSupport.getSmallRandomPrime()); // Error 2

      System.out.println("Derived Class Constructor Testing ***************");
      System.out.println(rae.toString() + SEPARATOR + pthyro.toString()
            + SEPARATOR + ydrielle.toString() + SEPARATOR + happy.toString()
            + SEPARATOR + boom.toString() + SEPARATOR + shinrael.toString()
            + SEPARATOR + elvis.toString() + SEPARATOR);

      // Testing Mutator
      rae.setPrimes(EncryptionSupport.getMedSizedRandomPrime(),
            EncryptionSupport.getMedSizedRandomPrime());
      pthyro.setPrimes(EncryptionSupport.getSmallRandomPrime(),
            EncryptionSupport.getSmallRandomPrime());

      System.out.println("Derived Class Mutator Test **********");
      System.out.println(rae + SEPARATOR + pthyro);

      // Accessor Test
      System.out.println("Derived Test Accessor Test **********");
      System.out.println(shinrael.getPrivateKey() + SEPARATOR
            + shinrael.getPublicKey() + SEPARATOR + boom.getPrivateKey()
            + SEPARATOR + boom.getPublicKey());

   }
}

/**
 * EncryptionSupport contains only static methods that clients can use wherever
 * all method names should be fairly descriptive other than inverseMonN(), which
 * you can take as a black-box (see description of assignment)
 * 
 * @author Michael Loceff
 * @author Baba Kofi Weusijana
 */
class EncryptionSupport
{
   public static boolean isPrime(long x)
   {
      long k, loopLim;

      if (x < 2)
         return false;
      if (x < 4)
         return true;
      if (x % 2 == 0 || x % 3 == 0)
         return false;

      // now use the fact the all primes of form 6k +/- 1
      loopLim = (long) Math.sqrt(x);
      for (k = 5; k <= loopLim; k += 6)
      {
         if (x % k == 0 || x % (k + 2) == 0)
            return false;
      }
      return true;
   }

   public static long inverseModN(long a, long n)
   {
      // uses extended euclidean algorithm giving as + nt = gcd(n, a),
      // with gcd(n, a) = 1, and s, t discovered. s = 1/a, and t ignored

      long s, t, r, sPrev, tPrev, rPrev, temp, q, inverse;

      // special key encryption conditions; we will pick some prime e >= 3 for a
      if (a < 3 || a >= n || !isPrime(a))
         return 0; // error

      // we are now guaranteed 3 <= a < n and gcd(a, n) = 1;

      // initialize working variables
      s = 0;
      t = 1;
      r = n;
      sPrev = 1;
      tPrev = 0;
      rPrev = a;

      while (r != 0)
      {
         q = rPrev / r;

         temp = r;
         r = rPrev - q * r;
         rPrev = temp;

         temp = s;
         s = sPrev - q * s;
         sPrev = temp;

         temp = t;
         t = tPrev - q * t;
         tPrev = temp;
      }

      inverse = sPrev % n;
      if (inverse < 0)
         inverse += n;
      return inverse;
   }

   public static long getSmallRandomPrime()
   {
      int index;
      Random randObject = new Random(System.currentTimeMillis());
      long lowPrimes[] =
      { 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97,
            101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163,
            167, 173, 179, 181, 191, 193, 197, 199, 211, 223, 227, 229, 233,
            239, 241, 251, 257, 263, 269, 271, 277, 281, 283, 293, 307, 311,
            313, 317, 331, 337, 347, 349, 353, 359, 367, 373, 379, 383, 389,
            397, 401, 409, 419, 421, 431, 433, 439, 443, 449, 457, 461, 463,
            467, 479, 487, 491, 499, 503, 509, 521, 523, 541 };
      long arraySize = lowPrimes.length;

      // pick prime in the above array bet 0 and arraySize - 1
      index = (int) (randObject.nextDouble() * arraySize);

      return lowPrimes[index];
   }

   public static long getMedSizedRandomPrime()
   {
      int index;
      Random randObject = new Random(System.currentTimeMillis());
      long lowPrimes[] =
      { 541, 547, 557, 563, 569, 571, 577, 587, 593, 599, 601, 607, 613, 617,
            619, 631, 641, 643, 647, 653, 659, 661, 673, 677, 683, 691, 701,
            709, 719, 727, 733, 739, 743, 751, 757, 761, 769, 773, 787, 797,
            809, 811, 821, 823, 827, 829, 839, 853, 857, 859, 863, 877, 881,
            883, 887, 907, 911, 919, 929, 937, 941, 947, 953, 967, 971, 977,
            983, 991, 997, 1009, 1013, 1019, 1021, 1031, 1033, 1039, 1049, 1051,
            1061, 1063, 1069, 1087, 1091, 1093, 1097, 1103, 1109, 1117, 1123,
            1129, 1151, 1153, 1163, 1171, 1181, 1187, 1193, 1201, 1213, 1217,
            1223 };
      long arraySize = lowPrimes.length;

      // pick prime in the above array bet 0 and arraySize - 1
      index = (int) (randObject.nextDouble() * arraySize);

      return lowPrimes[index];
   }
}

/**
 * IntPair allows public, no filtering; classes that use it will protect it
 * 
 * @author Michael Loceff
 */
class IntPair
{
   public long firstInt;
   public long secondInt;

   // constructors
   IntPair()
   {
      firstInt = secondInt = 0;
   }

   IntPair(long frst, long scnd)
   {
      firstInt = frst;
      secondInt = scnd;
   }

   public String toString()
   {
      return "(" + firstInt + ", " + secondInt + ")";
   }
}

class InternetUser
{
   // public static class constants
   public final static int MIN_NAME_LENGTH = 2;
   public final static int MAX_NAME_LENGTH = 50;
   public final static int MIN_IP_LENGTH = 7;
   public final static int MAX_IP_LENGTH = 15;
   public final static String DEFAULT_IP = "0.0.0.0";
   public final static String DEFAULT_NAME = "undefined";
   public final static String SEPARATOR = "\n";
   public final static String COMM = ",";

   // private member date
   private String name;
   private String ip;

   // error flag
   boolean errorFlag;

   // constructors
   public InternetUser()
   {
      this(DEFAULT_NAME, DEFAULT_IP);
   }

   public InternetUser(String name, String ip)
   {
      setName(name);
      setIp(ip);
   }

   // Mutators
   public boolean setIp(String userIp)
   {
      if (isValidIp(userIp))
      {
         this.ip = userIp;
         return true;
      } else
      {
         this.ip = DEFAULT_IP;
         return false;
      }
   }

   public boolean setName(String userName)
   {
      if (isValidName(userName))
      {
         this.name = userName;
         return true;
      } else
      {
         this.name = DEFAULT_NAME;
         return false;
      }
   }

   // Accessors
   public String getIp()
   {
      return ip;
   }

   public String getName()
   {
      return name;
   }

   // Validation helpers
   private static boolean isValidIp(String userIp)
   {
      return (userIp.length() >= MIN_IP_LENGTH
            && userIp.length() <= MAX_IP_LENGTH);
   }

   private static boolean isValidName(String userName)
   {
      return (userName.length() >= MIN_NAME_LENGTH
            && userName.length() <= MAX_NAME_LENGTH);
   }

   public String toString()
   {
      return "Name: " + name + SEPARATOR + "IP Address: " + ip + SEPARATOR
            + SEPARATOR;
   }
}

// derived class
class Communicator extends InternetUser
{
   public final static int ERROR_FLAG_NUM = 0;
   public final static String COMM = ",";
   private final static double MAX_PQ = Math.pow(Long.MAX_VALUE, 0.5);
   private IntPair publicKey;
   private IntPair privateKey;
   private long firstPrime;
   private long secondPrime;
   private long n, phi, e, d;

   // Constructors
   Communicator()
   {
      super();
      publicKey = new IntPair();
      privateKey = new IntPair();
   }

   Communicator(long firstPrime, long secondPrime)
   {
      super();
      if (!setPrimes(firstPrime, secondPrime))
      {
         this.firstPrime = ERROR_FLAG_NUM;
         this.secondPrime = ERROR_FLAG_NUM;
      }
   }

   Communicator(String name, String ip)
   {
      super(name, ip);
      publicKey = new IntPair();
      privateKey = new IntPair();
   }

   Communicator(String name, String ip, long firstPrime, long secondPrime)
   {
      super(name, ip);
      if (!setPrimes(firstPrime, secondPrime))
      {
         this.firstPrime = ERROR_FLAG_NUM;
         this.secondPrime = ERROR_FLAG_NUM;
      }
   }

   /*
    * check test to make sure that the mutator is checking both primes. If not,
    * check test to see if we want to return one false and pass the other, or
    * return both false until both pass.
    */

   // Mutator
   public boolean setPrimes(long firstPrime, long secondPrime)
   {
      if (firstPrime < MAX_PQ && secondPrime < MAX_PQ)
      {
         this.firstPrime = firstPrime;
         this.secondPrime = secondPrime;
         return computeBothEncrKeys();
      }
      return false;
   }

   // Looks at p and q to see if its prime
   // looks at e until it is prime or until it is over 10million times
   private boolean computeBothEncrKeys()
   {
      if (EncryptionSupport.isPrime(firstPrime)
            && EncryptionSupport.isPrime(secondPrime))
      {
         n = firstPrime * secondPrime;
         phi = ((firstPrime - 1) * (secondPrime - 1));
         e = EncryptionSupport.getSmallRandomPrime();
         int i = 0;
         while (e >= phi || e <= 0 || phi % e == 0) // runs until it looks e is
                                                    // less than phi
         {
            e = EncryptionSupport.getSmallRandomPrime();
            i++;
            if (i >= 10000000)
            {
               return false;
            }
         }
         d = EncryptionSupport.inverseModN(e, n);
         publicKey = new IntPair(e, n);
         privateKey = new IntPair(d, n);
         return true;
      } else
         return false;
   }

   // stringers all the private data objects
   public String toString()
   {
      return SEPARATOR + "----------------" + SEPARATOR + super.toString()
            + "(p, q) n, phi, e, d:" + "(" + firstPrime + ", " + secondPrime
            + ")   " + "" + n + ", " + phi + ", " + e + ", " + d + SEPARATOR
            + "public key " + publicKey + SEPARATOR + "private key "
            + privateKey + SEPARATOR + SEPARATOR;
   }

   // accessors
   public IntPair getPublicKey()
   {
      return publicKey;
   }

   public IntPair getPrivateKey()
   {
      return privateKey;
   }

}

/*
 * Sample Run
Base Class Testing ***********************

Name: undefined
IP Address: 0.0.0.0


Name: Amy Goode
IP Address: 32.66.95.84


Name: bad
IP Address: 0.0.0.0


Testing getName Accessor ***********************

Amy Goode


Final Print ***********************

Name: Tony Stark
IP Address: 0.0.0.0


Name: Amy Goode
IP Address: 66.45.20


Name: undefined
IP Address: 0.0.0.0


Derived Class Constructor Testing ***************

----------------
Name: undefined
IP Address: 0.0.0.0

(p, q) n, phi, e, d:(0, 0)   0, 0, 0, 0
public key (0, 0)
private key (0, 0)



----------------
Name: Pthyro Druid
IP Address: 72.54.93.147

(p, q) n, phi, e, d:(0, 0)   0, 0, 0, 0
public key (0, 0)
private key (0, 0)



----------------
Name: Ydrielle Priest
IP Address: 0.0.0.0

(p, q) n, phi, e, d:(73, 73)   5329, 5184, 73, 1
public key (73, 5329)
private key (1, 5329)



----------------
Name: Happy Fish
IP Address: 102.36.99.01

(p, q) n, phi, e, d:(0, 0)   0, 0, 0, 0
public key null
private key null



----------------
Name: Boom Kenken
IP Address: 80.08.80.08

(p, q) n, phi, e, d:(577, 1009)   582193, 580608, 73, 31901
public key (73, 582193)
private key (31901, 582193)



----------------
Name: Shinrael Ghostcat
IP Address: 11.35.407.79

(p, q) n, phi, e, d:(619, 73)   45187, 44496, 73, 1
public key (73, 45187)
private key (1, 45187)



----------------
Name: undefined
IP Address: 0.0.0.0

(p, q) n, phi, e, d:(73, 73)   5329, 5184, 73, 1
public key (73, 5329)
private key (1, 5329)



Derived Class Mutator Test **********

----------------
Name: undefined
IP Address: 0.0.0.0

(p, q) n, phi, e, d:(619, 619)   383161, 381924, 73, 167961
public key (73, 383161)
private key (167961, 383161)



----------------
Name: Pthyro Druid
IP Address: 72.54.93.147

(p, q) n, phi, e, d:(73, 73)   5329, 5184, 73, 1
public key (73, 5329)
private key (1, 5329)


Derived Test Accessor Test **********
(1, 45187)
(73, 45187)
(31901, 582193)
(73, 582193)

*/
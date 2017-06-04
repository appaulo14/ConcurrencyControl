import java.util.ArrayList;

/**
 * This is the main class where everything starts
 * 
 * @author Pawl
 */
public class ConcurrencyControl {

    private final static int THREAD_COUNT = 6;

    /**
     * The number of class rosters.
     */
    public final static int ROSTER_COUNT = 5;

    /**
     * The number of students in each class. All class rosters have every
     * student
     */
    public final static int TOTAL_STUDENTS = 100;

    /**
     * The number of transactions done by a single thread during the course of
     * its life.
     */
    public final static int PER_THREAD_TRANSACTION_LIMIT = 10;

    /**
     * The indexes for the transaction mix for the threads to run.
     */
    public final static int[] transactionList = { 0, 4, 2, 3, 3 }; //Modify this to change which transactions run

    /**
     * A list containing all of the class rosters.
     */
    public static ArrayList<ClassRoster> rosterList = new ArrayList<ClassRoster>();

    /**
     * The entry point of the program.
     * 
     * @param args
     */
    public static void main(String[] args) {
        initializeClassRosters();
        initializeThreads();
    }

    private static void initializeClassRosters() {
        for (int i = 0; i < ROSTER_COUNT; i++) {
            ClassRoster myClassRoster = new ClassRoster();
            for (int ii = 1; ii <= TOTAL_STUDENTS; ii++) {
                myClassRoster.insert(ii);
            }
            rosterList.add(myClassRoster);
        }
    }

    private static void initializeThreads() {
        // Initialize the transaction object
        TransactionObject myTransactionObject = new TransactionObject();
        System.out.println("Number of Threads: " + THREAD_COUNT);
        for (int i = 0; i < THREAD_COUNT; i++) {
            new TransactionThread(myTransactionObject,
                    PER_THREAD_TRANSACTION_LIMIT, transactionList).start();
            System.out.println("Thread " + i + " launched");
        }
    }

    int x = 1;
}

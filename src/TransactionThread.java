import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 * A thread that does transactions using {@TransactionObject
 * 
* }.
 * 
 * @author Pawl
 */
public class TransactionThread extends Thread {
    private final TransactionObject myTransactionObject;
    private final Random randomGenerator = new Random(new Date().getTime());
    int studentCount = ConcurrencyControl.TOTAL_STUDENTS;
    int rosterCount = ConcurrencyControl.ROSTER_COUNT;
    int TRANSACTION_LIMIT;
    int[] transactionList;

    TransactionThread(TransactionObject inTransactionObject,
            int inTransactionLimit, int[] inTransactionList) {
        this.myTransactionObject = inTransactionObject;
        this.TRANSACTION_LIMIT = inTransactionLimit;
        this.transactionList = inTransactionList;
    }

    @Override
    public void run() {
        int x = 0;
        long startTime = new Date().getTime();
        while (true) {
            for (int transactionNumber : transactionList) {
                x++;
                if (x > TRANSACTION_LIMIT) {
                    break;
                }
                switch (transactionNumber) {
                case 0: // Insert
                    doInsert();
                    break;
                case 1: // Delete
                    doDelete();
                    break;
                case 2: // Enrolled Courses
                    doEnrolledCourses();
                    break;
                case 3: // Total Enrollment
                    doTotalEnrollment();
                    break;
                case 4: // Transfer
                    doTransfer();
                    break;
                }
            }
            if (x > TRANSACTION_LIMIT) {
                x--;
                break;
            }
        }
        long endTime = new Date().getTime();
        long transactionTime = endTime - startTime;
        System.out.println(this.getName() + " did " + x + " transactions in "
                + transactionTime + " milliseconds");
    }

    private void doTransfer() {
        ArrayList<Integer> rosterPair = getRandomPairOfRostersIds();
        int rosterId1 = rosterPair.get(0);
        int rosterId2 = rosterPair.get(1);
        int studentId = getRandomStudentId();
        myTransactionObject.transfer(rosterId1, rosterId2, studentId);
    }

    private void doTotalEnrollment() {
        myTransactionObject.total_enrollment();
    }

    private void doEnrolledCourses() {
        myTransactionObject.enrolled_courses(getRandomStudentId());
    }

    private void doInsert() {
        int studentId = getRandomStudentId();
        int classRosterId = getRandomRosterId();
        myTransactionObject.insert(classRosterId, studentId);
    }

    private void doDelete() {
        int studentId = getRandomStudentId();
        int classRosterId = getRandomRosterId();
        myTransactionObject.delete(classRosterId, studentId);
    }

    private int getRandomStudentId() {
        return randomGenerator.nextInt(studentCount) + 1;
    }

    private int getRandomRosterId() {
        return randomGenerator.nextInt(rosterCount);
    }

    private ArrayList<Integer> getRandomPairOfRostersIds() {
        ArrayList<Integer> rosterPair = new ArrayList<Integer>();
        int roster1 = getRandomRosterId();
        int roster2 = getRandomRosterId();
        while (roster1 == roster2) {
            roster2 = getRandomRosterId();
        }
        rosterPair.add(roster1);
        rosterPair.add(roster2);
        return rosterPair;

    }
}

import java.util.ArrayList;

/**
 * @author Pawl This class contains the list of students in a class and the
 *         methods to manipulate that list of students.
 */
public class ClassRoster {
    private final ArrayList<Integer> studentList;
    private static final long sleepTime = 200;

    ClassRoster() {
        studentList = new ArrayList<Integer>();
    }

    private void sleep() {
        try {
            Thread.sleep(sleepTime);
        }
        catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Insert the student with the specified id into the roster.
     * 
     * @param id
     *            The id of the student to insert.
     */
    public void insert(int id) {
        sleep();
        if (id > 0) {
            studentList.add(id);
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Delete the student with the specified id from the roster
     * 
     * @param id
     *            The id of the student to delete.
     * @return whether the deletion was successful or not.
     */
    public boolean delete(int id) {
        sleep();
        int idIndex = studentList.indexOf(id);
        if (idIndex != -1) {
            studentList.remove(idIndex);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Check to see if a student is in the class roster.
     * 
     * @param id
     *            The id of the student to find.
     * @return whether the student was found.
     */
    public boolean find(int id) {
        sleep();
        int idIndex = studentList.indexOf(id);
        if (idIndex == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * @return the number of the students in the class roster
     */
    public int count() {
        sleep();
        return studentList.size();
    }

}

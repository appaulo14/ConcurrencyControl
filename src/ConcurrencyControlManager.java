import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * This class controls access to the to the shared resource, in this case the
 * class rosters.
 * 
 * @author Pawl
 * 
 */
public class ConcurrencyControlManager {
    private final AtomicInteger readersCount = new AtomicInteger(0);
    private final AtomicInteger writersCount = new AtomicInteger(0);

    private final Lock lock = new ReentrantLock();
    private final Condition writeUnlock = lock.newCondition();
    private final Condition readerUnlock = lock.newCondition();

    /**
     * Request a read lock in order to read from the class roster.
     */
    public void requestReadLock() {
        lock.lock();
        assert ((readersCount.get() == 0 || writersCount.get() == 0) && writersCount
                .get() <= 1) : "rc=" + readersCount.get() + " nw="
                + writersCount.get();
        while (writersCount.get() > 0) {
            try {
                writeUnlock.await();
            }
            catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        readersCount.incrementAndGet();
        assert ((readersCount.get() == 0 || writersCount.get() == 0) && writersCount
                .get() <= 1) : "rc=" + readersCount.get() + " nw="
                + writersCount.get();
        lock.unlock();
    }

    /**
     * Release the read lock to signify that the thread is done reading.
     */
    public void releaseReadLock() {
        lock.lock();
        assert ((readersCount.get() == 0 || writersCount.get() == 0) && writersCount
                .get() <= 1) : "rc=" + readersCount.get() + " nw="
                + writersCount.get();
        readersCount.decrementAndGet();
        if (readersCount.get() == 0) {
            readerUnlock.signalAll();
        }
        assert ((readersCount.get() == 0 || writersCount.get() == 0) && writersCount
                .get() <= 1) : "rc=" + readersCount.get() + " nw="
                + writersCount.get();
        lock.unlock();
    }

    /**
     * Request a write lock in order to write to the class roster(s).
     */
    public void requestWriteLock() {
        try {
            lock.lock();
            while (writersCount.get() > 0 || readersCount.get() > 0) {
                assert ((readersCount.get() == 0 || writersCount.get() == 0) && writersCount
                        .get() <= 1) : "rc=" + readersCount.get() + " nw="
                        + writersCount.get();
                if (writersCount.get() > 0) {
                    writeUnlock.await();
                }
                if (readersCount.get() > 0) {
                    readerUnlock.await();
                }
                assert ((readersCount.get() == 0 || writersCount.get() == 0) && writersCount
                        .get() <= 1) : "rc=" + readersCount.get() + " nw="
                        + writersCount.get();
            }
            writersCount.incrementAndGet();
            lock.unlock();
        }
        catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Release the write lock to signify that the thread is done writing
     */
    public void releaseWriteLock() {
        lock.lock();
        assert ((readersCount.get() == 0 || writersCount.get() == 0) && writersCount
                .get() <= 1) : "rc=" + readersCount.get() + " nw="
                + writersCount.get();
        writersCount.decrementAndGet();
        writeUnlock.signalAll();
        assert ((readersCount.get() == 0 || writersCount.get() == 0) && writersCount
                .get() <= 1) : "rc=" + readersCount.get() + " nw="
                + writersCount.get();
        lock.unlock();
    }
}

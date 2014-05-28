import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Created by bamboo on 28.05.14.
 */
public class PingPongThreadCondition extends PingPongThread {

    private final Lock lock;

    private final Condition firstCondition;
    private final Condition secondCondition;

    private volatile int turnCountDown;

    public static volatile long turnOwner;


    protected volatile long otherThreadId;

    protected PingPongThreadCondition(String name,
                                      Lock lock,
                                      Condition firstCondition,
                                      Condition secondCondition,
                                      boolean owner) {
        super(name);
        this.lock = lock;
        this.firstCondition = firstCondition;
        this.secondCondition = secondCondition;
        if (owner) {
            turnOwner = this.getId();
        }

        turnCountDown = PingPongGame.numOfTurns;
    }

    @Override
    public void acquire() {

        lock.lock();
        try {

            while (turnOwner != getId()) {
                firstCondition.awaitUninterruptibly();
            }

        } finally {
            lock.unlock();
        }

    }

    @Override
    public void release() {

        lock.lock();
        try {

            turnCountDown--;

            if (turnCountDown == 0) {
                PingPongThreadCondition.turnOwner = otherThreadId;
                turnCountDown = PingPongGame.numOfTurns;
                secondCondition.signal();
            }

        } finally {
            lock.unlock();
        }


    }

    public void setOtherThreadId(long otherThreadId) {
        this.otherThreadId = otherThreadId;
    }
}

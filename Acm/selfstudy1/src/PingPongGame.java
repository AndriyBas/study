import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by bamboo on 28.05.14.
 */
public class PingPongGame {

    public static final int numOfTurns = 2;

    public static final int numOfThrows = 20;

    private static final PingPongGame instance = new PingPongGame();

    private PingPongGame() {

    }

    public static PingPongGame getInstance() {
        return instance;
    }

    public void startGame() {

        Lock lock = new ReentrantLock();
        Condition pingCondition = lock.newCondition();
        Condition pongCondition = lock.newCondition();

        PingPongThreadCondition t1 = new PingPongThreadCondition(
                "ping",
                lock,
                pingCondition,
                pongCondition,
                true
        );

        PingPongThreadCondition t2 = new PingPongThreadCondition(
                "pong",
                lock,
                pongCondition,
                pingCondition,
                false
        );

        t1.setOtherThreadId(t2.getId());

        t2.setOtherThreadId(t1.getId());

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

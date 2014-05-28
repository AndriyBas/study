/**
 * Created by bamboo on 28.05.14.
 */
public abstract class PingPongThread extends Thread {

    protected String name;

    protected PingPongThread(String name) {
        this.name = name;
    }

    public abstract void acquire();

    public abstract void release();

    @Override
    public void run() {

        for (int i = 0; i < PingPongGame.numOfThrows; i++) {

            acquire();

            //TODO do action
            System.out.println(name + "(" + i + ")");

            release();
        }

    }
}

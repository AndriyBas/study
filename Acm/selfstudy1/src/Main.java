import java.util.Arrays;

/**
 * Created by bamboo on 28.05.14.
 */
public class Main {

    public static void main(String[] args) {

//        PingPongGame.getInstance().startGame();

        int[] a = new int[2];
        a[0] = 0;
        a[1] = 1;

        a = Arrays.copyOf(a, 4);

        System.arraycopy(a, 0, a, 0, 4);
        for(int i = 0; i < 4; i++) {
            System.out.println(a[i]);
        }

        System.out.println(a.length);
        Arrays.asList(a).stream().forEach(x -> System.out.print(x + " "));
    }
}

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by bamboo on 28.05.14.
 */
public class MainSelf2 {

    public static void main(String[] args) {

        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("Runnable r1");
            }
        };

        Runnable r2 = () -> System.out.println("Runnable r2");

        r1.run();
        r2.run();


        // FUCK MY BALLS, MOTHERFUCKER, THIS IS WONDERFUL
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }

        Collections.sort(list, (a, b) -> Integer.compare(b, a));

        list.stream().forEach(x -> System.out.print(x + " "));
    }
}
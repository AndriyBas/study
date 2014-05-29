import java.util.*;
import java.util.stream.Collectors;

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
        for (int i = 0; i < 100; i++) {
            list.add(i);
        }

        Collections.sort(list, (a, b) -> Integer.compare(b, a));

        list.parallelStream().forEach(x -> System.out.print(x + " "));
        System.out.println("\nWTF");

        Integer i = new Integer(10);

        Integer j = Integer.valueOf(10);
        String.valueOf(new char[]{'A'});

        List<int[]> a = new ArrayList<>();
        a.add(new int[]{1, 2, 3});
        System.out.println(a.get(0));

        ArrayList<Integer> l3 = new ArrayList<>();

        l3.toString();

        List l1 = Arrays.asList(1, 2, 3, 4);
        List l2 = Arrays.asList(new int[]{1, 2, 3, 4});
        System.out.println(l1);
        System.out.println(l2);

        Collections.synchronizedList(l1);


        m1(1);

        System.out.println(Arrays.toString(new int[]{1, 2, 3, 4, 5, 65}));

        Math.abs(1);

        Integer min = Integer.MIN_VALUE + 1;
        System.out.println(Math.abs(min));
        System.out.println(Integer.MAX_VALUE);
        float ff = 10.0f;

//        boxTest();
//        boxTest2();

        testContacts();

    }

    public static void boxTest() {
        System.out.println("\n================\nIn box test()");
        long s1 = 0L;
        long t1 = System.currentTimeMillis();
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            s1 += i;
        }
        long time1 = System.currentTimeMillis() - t1;
        System.out.println("time 1 : " + time1);


    }

    public static void boxTest2() {
        Long s2 = 0L;
        long t2 = System.currentTimeMillis();
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            s2 += i + 1 - 1;
        }
        long time2 = System.currentTimeMillis() - t2;
        System.out.println("time 2 : " + time2);

    }


    public static void m1(int... a) {
        System.out.println("\nin m1");
        System.out.println(a);

    }

    public static void testContacts() {
        Contacts c = new Contacts();
        Random r = new Random();
        for (int i = 0; i < 100; i++) {
            c.add(new Person(String.format("name - %01d", i % 10), r.nextInt(100)));
        }

        System.out.println(c.filter(p -> p.getName().equals("name - 00")));
        List<Person> list = c.filter(p -> MyPredicate.ololoNonNull(p).test(p));
        System.out.println(list);

        // FUCK, OMG WHAT Java 8 does
        Collections.sort(list,
                Comparator.comparing(Person::getName).reversed()
                        .thenComparing(Person::getAge));
        System.out.println(list);


        System.out.println(list.stream().filter(x -> x.getAge() > 88)
                .collect(Collectors.toList()));

        int sum = list.stream()
                .filter(x -> x.getAge() > 50)
                .mapToInt(p -> p.getAge())
                .sum();
        System.out.println("Sum : " + sum);

        double av = list.stream()
                .filter(p -> p.getAge() < 50)
                .mapToInt(p -> p.getAge())
                .average().getAsDouble();
        System.out.println("Average : \t\t\t" + av);

        double av2 = list.parallelStream()
                .filter(p -> p.getAge() < 50)
                .mapToDouble(p -> p.getAge())
                .average().getAsDouble();
        System.out.println("Average parallel : \t" + av2);

        int count = 0;
        for (Person p : list) {
            count += p.testOlolo(x -> x.getAge() > 50) ? 1 : 0;
        }
        System.out.println("Count : " + count);
    }

}
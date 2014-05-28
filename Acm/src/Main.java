import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.*;


class A implements Cloneable {

    private A() throws IllegalAccessException {
//        throw new IllegalAccessException("opa");
        System.out.println("A : in constructor()");
    }


}

class B implements Cloneable {
    Integer b;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return " b : " + String.valueOf(b);
    }
}

class C implements Cloneable {

    B bb;
    Integer c;

    @Override
    public String toString() {
        return " c : " + String.valueOf(c) + "\n" + bb.toString();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

class D implements Cloneable {
    C c;
    String s;
    final int xxx = 10;
    ArrayList<Integer> arr;
    int[] intarr;

    @Override
    public Object clone() throws CloneNotSupportedException {
        D ddd = (D) super.clone();


        ddd.c = (C) c.clone();
        ddd.intarr = intarr.clone();
        return ddd;
    }

    @Override
    public String toString() {
        return "s : " + s + ", intarr[0] : " + intarr[0] + "\n" + c.toString();
    }
}

public class Main {
    class Input {
        BufferedReader bf;
        StringTokenizer st;

        Input(InputStream stream) throws IOException {
            bf = new BufferedReader(new InputStreamReader(stream));
            st = new StringTokenizer("");
        }

        int nextInt() throws IOException {
            if (!st.hasMoreTokens())
                st = new StringTokenizer(bf.readLine());

            return Integer.parseInt(st.nextToken());
        }

        long nextLong() throws IOException {
            if (!st.hasMoreTokens())
                st = new StringTokenizer(bf.readLine());
            return Long.parseLong(st.nextToken());
        }

        double nextDouble() throws IOException {
            if (!st.hasMoreTokens())
                st = new StringTokenizer(bf.readLine());
            return Double.parseDouble(st.nextToken());
        }

        void readLine() throws IOException {
            st = new StringTokenizer(bf.readLine());
        }

        String nextString() throws IOException {
            if (!st.hasMoreTokens())
                st = new StringTokenizer(bf.readLine());
            return st.nextToken();
        }

        String nextLine() throws IOException {
            return bf.readLine();
        }

        void close() throws IOException {
            bf.close();
        }
    }

    Input in;

    public class Problem {

        class Point implements Comparable<Point> {
            int x, y, p;

            Point(int a, int b, int k) {
                x = a;
                y = b;
                p = k;
            }

            @Override
            public int compareTo(Point o) {
                return Integer.compare(this.x, o.x);
            }
        }


        void hack(Class c) {
            try {
                Constructor<?> constructor = c.getDeclaredConstructors()[0];
                constructor.setAccessible(true);
                constructor.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        @SuppressWarnings("deprecation")
        public void solve() throws IOException {


            B b = new B();
            b.b = new Integer(100000);
            System.out.println(b);

            C c = new C();
            c.c = new Integer(1400);
            c.bb = new B();
            c.bb.b = new Integer(470);

            D d = new D();
            d.c = c;
            d.s = new String("ololo");
            d.arr = new ArrayList<>();
            d.arr.add(1);
            d.arr.add(10);
            d.arr.add(1000);
            d.intarr = new int[]{-1, 2};

            System.out.println(d);
            try {
//                Object clone = b.clone();
//                System.out.println(clone);

                Object clone2 = d.clone();
                System.out.println(clone2);
                d.arr.set(0, 46);
                d.c.c = -99;
                d.intarr[0] = -2;

                System.out.println(d);
                System.out.println(clone2);

            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }

            BigDecimal d1 = new BigDecimal("1.00");
            BigDecimal d2 = new BigDecimal("1.0");


            System.out.println("-----------------");
            System.out.println(d1.compareTo(d2));

            HashMap<BigDecimal, String> m1 = new HashMap<>();
            m1.put(d1, "oleh");
            m1.put(d2, "yulia");

            TreeMap<BigDecimal, String> m2 = new TreeMap<>();
            m2.put(d1, "oleh");
            m2.put(d2, "yulia");

            System.out.println(m1);
            System.out.println(m2);

            System.out.println(0.0f / 0.0f);



   /*         hack(A.class);

            Object rr = new Object();
            rr.hashCode();

            Integer[] arr = new Integer[]{2, 2};
            Integer[] arr2 = new Integer[]{1, 2};

            List<Integer> arrayList1 = Arrays.asList(arr);
            List<Integer> arrayList2 = Arrays.asList(arr2);
            System.out.println(arr.hashCode());
            System.out.println(arr2.hashCode());

           Object q = arr;



            int d = arr.length;

            System.out.println(arrayList1.hashCode());
            System.out.println(arrayList2.hashCode());

            Integer i = new Integer(1453643562);
            String s = i.toString();
            System.out.println(String.format("%0123d", 3));
*/
//            System.out.println(s);
//            AtomicInteger s;

            try {
                ex1();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            ex2();

            test(ExtendedOperation.class, 1.0, 2.0);

        }

        double sum(double[] a) {
            double res = 0;
            for (double d : a) {
                res += d;
            }
            return res;
        }


        <T extends Enum<T> & Operation> void test(Class<T> opSet, double a, double b) {
            System.out.println("\nIn test\n------------");
            for (Operation op : opSet.getEnumConstants()) {
                System.out.printf("%.2f %s %.2f = %.2f%n", a, op, b, op.apply(a, b));
            }
        }

        public void ex2() {

            int x1 = 0;
            int x2 = 0;
            Random random = new Random();

            for (int i = 0; i < 1000; i++) {

                double[] arr = random.doubles(10).toArray();

                double c = 0;
                for (int j = 0; j < arr.length; j++) {
                    c += arr[j];
                }
                double d = 0;
                for (int j = arr.length - 1; j >= 0; j--) {
                    d += arr[j];
                }

                if (c == d) {
                    x1++;
                } else {
                    x2++;
                }
            }

            System.out.println("equal : " + x1);
            System.out.println("NOT equal : " + x2);
        }


        public void ex1() throws InterruptedException {
            System.out.println("Ex1 : \n------------------");

            BlockingQueueConc<Integer> b = new BlockingQueueConc<>(10);

            Thread t1 = new Thread(new Runnable() {
                Random random = new Random();

                @Override
                public void run() {
                    try {

                        for (; ; ) {
                            b.put(random.nextInt(1000));
                            Thread.sleep(500 + random.nextInt(1000));
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            });

            Thread t2 = new Thread(new Runnable() {
                Random random = new Random();

                @Override
                public void run() {
                    try {

                        for (; ; ) {
                            b.take();
                            Thread.sleep(500 + random.nextInt(1000));
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            });

            t1.start();
            t2.start();

            t1.join();
            t2.join();

        }
    }


    public void solveProblem() throws IOException {

        boolean oj = System.getProperty("ONLINE_JUDGE") != null;
        in = new Input(oj ? System.in : new FileInputStream("//home/bamboo/GitProjects/study/Acm/input.txt"));

        // in = new Input(System.in);
        // in = new Input(new FileInputStream("D:/Codes/Java/input.txt"));
        new Problem().solve();
        in.close();
    }

    public static void main(String[] args) throws IOException {
        new Main().solveProblem();
    }

}

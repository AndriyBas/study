import java.io.*;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.StringTokenizer;


class A {
    private A() {
        System.out.println("in constructor()");
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
                Constructor<?> constructor = A.class.getDeclaredConstructors()[0];
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

            hack(A.class);
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

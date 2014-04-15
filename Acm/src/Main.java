import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

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


        @SuppressWarnings("deprecation")
        public void solve() throws IOException {

            int n = in.nextInt();
            int m = in.nextInt();

            int[] a = new int[n + 1];
            Arrays.fill(a, 0);
            int last = n;

            for (int i = 0; i < m; i++) {
                int b = in.nextInt();
                while (last >= b) {
                    a[last] = b;
                    last--;
                }
            }

            for (int i = 1; i <= n; i++) {
                System.out.print(a[i]);
                System.out.print(" ");
            }

        }


    }


    public void solveProblem() throws IOException {

        boolean oj = System.getProperty("ONLINE_JUDGE") != null;
        in = new Input(oj ? System.in : new FileInputStream("//home/bamboo/GitProjects/studyDir/Acm/input.txt"));

        // in = new Input(System.in);
        // in = new Input(new FileInputStream("D:/Codes/Java/input.txt"));
        new Problem().solve();
        in.close();
    }

    public static void main(String[] args) throws IOException {
        new Main().solveProblem();
    }

}

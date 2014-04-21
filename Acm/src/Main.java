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

            int c = in.nextInt();
            int d = in.nextInt();
            int n = in.nextInt();
            int m = in.nextInt();
            int k = in.nextInt();

            int N = n * m + 1;
            int[] a = new int[N];
            Arrays.fill(a, 0);

            for (int i = 0; i <= n; i++) {
                a[i] = Math.min(c, d * i);
            }

            for (int i = n + 1; i < N; i++) {
                a[i] = Math.min(a[i - 1] + d, a[i - n] + c);
            }

            int s = Math.max(n * m - k, 0);

            int res = a[s];

            System.out.println(res);
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

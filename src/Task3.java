import java.io.BufferedInputStream;
import java.util.Arrays;

public class Task3 {
    public static void main(String[] args) throws Exception {
        FastScanner in = new FastScanner();
        int n = in.nextInt();
        long[] value = new long[n];
        for (int i = 0; i < n; i++) {
            value[i] = in.nextLong();
        }
        int[] head = new int[n];
        Arrays.fill(head, -1);
        int[] to = new int[2 * Math.max(0, n - 1)];
        int[] next = new int[to.length];
        int edgeCount = 0;
        for (int i = 1; i < n; i++) {
            int a = in.nextInt() - 1;
            int b = in.nextInt() - 1;
            to[edgeCount] = b;
            next[edgeCount] = head[a];
            head[a] = edgeCount++;
            to[edgeCount] = a;
            next[edgeCount] = head[b];
            head[b] = edgeCount++;
        }
        int[] parent = new int[n];
        int[] order = new int[n];
        Arrays.fill(parent, -2);
        parent[0] = -1;
        int count = 1;
        order[0] = 0;
        for (int i = 0; i < n; i++) {
            int vertex = order[i];
            for (int edge = head[vertex]; edge != -1; edge = next[edge]) {
                int other = to[edge];
                if (parent[other] == -2) {
                    parent[other] = vertex;
                    order[count++] = other;
                }
            }
        }
        long[] without = new long[n];
        long[] with = new long[n];
        for (int i = n - 1; i >= 0; i--) {
            int vertex = order[i];
            with[vertex] = value[vertex];
            for (int edge = head[vertex]; edge != -1; edge = next[edge]) {
                int child = to[edge];
                if (parent[child] == vertex) {
                    without[vertex] += Math.max(without[child], with[child]);
                    with[vertex] += without[child];
                }
            }
        }
        System.out.println(Math.max(without[0], with[0]));
    }

    private static class FastScanner {
        private final BufferedInputStream input = new BufferedInputStream(System.in);
        private final byte[] buffer = new byte[1 << 16];
        private int length;
        private int position;

        private int read() throws Exception {
            if (position >= length) {
                length = input.read(buffer);
                position = 0;
                if (length <= 0) {
                    return -1;
                }
            }
            return buffer[position++];
        }

        long nextLong() throws Exception {
            int c;
            do {
                c = read();
            } while (c <= 32);
            int sign = 1;
            if (c == '-') {
                sign = -1;
                c = read();
            }
            long result = 0;
            while (c > 32) {
                result = result * 10 + c - '0';
                c = read();
            }
            return result * sign;
        }

        int nextInt() throws Exception {
            return (int) nextLong();
        }
    }
}


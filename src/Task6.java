import java.io.BufferedInputStream;
import java.util.Arrays;

public class Task6 {
    private static final long NEGATIVE_INFINITY = Long.MIN_VALUE / 4;

    public static void main(String[] args) throws Exception {
        FastScanner in = new FastScanner();
        int n = in.nextInt();
        int k = in.nextInt();
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
        order[0] = 0;
        int count = 1;
        for (int index = 0; index < n; index++) {
            int vertex = order[index];
            for (int edge = head[vertex]; edge != -1; edge = next[edge]) {
                int other = to[edge];
                if (parent[other] == -2) {
                    parent[other] = vertex;
                    order[count++] = other;
                }
            }
        }
        long[][] dp = new long[n][k + 1];
        int[] size = new int[n];
        long answer = NEGATIVE_INFINITY;
        for (int index = n - 1; index >= 0; index--) {
            int vertex = order[index];
            Arrays.fill(dp[vertex], NEGATIVE_INFINITY);
            dp[vertex][1] = value[vertex];
            size[vertex] = 1;
            for (int edge = head[vertex]; edge != -1; edge = next[edge]) {
                int child = to[edge];
                if (parent[child] != vertex) {
                    continue;
                }
                int newSize = Math.min(k, size[vertex] + size[child]);
                long[] merged = new long[k + 1];
                Arrays.fill(merged, NEGATIVE_INFINITY);
                for (int taken = 1; taken <= Math.min(k, size[vertex]); taken++) {
                    merged[taken] = dp[vertex][taken];
                    if (dp[vertex][taken] == NEGATIVE_INFINITY) {
                        continue;
                    }
                    for (int fromChild = 1; fromChild <= Math.min(k - taken, size[child]); fromChild++) {
                        if (dp[child][fromChild] != NEGATIVE_INFINITY) {
                            merged[taken + fromChild] = Math.max(merged[taken + fromChild], dp[vertex][taken] + dp[child][fromChild]);
                        }
                    }
                }
                dp[vertex] = merged;
                size[vertex] = newSize;
            }
            answer = Math.max(answer, dp[vertex][k]);
        }
        System.out.println(answer);
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


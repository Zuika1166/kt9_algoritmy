import java.io.BufferedInputStream;

public class Task4 {
    public static void main(String[] args) throws Exception {
        FastScanner in = new FastScanner();
        int n = in.nextInt();
        long[] prefix = new long[n + 1];
        for (int i = 1; i <= n; i++) {
            prefix[i] = prefix[i - 1] + in.nextLong();
        }
        long[][] dp = new long[n][n];
        int[][] best = new int[n][n];
        for (int i = 0; i < n; i++) {
            best[i][i] = i;
        }
        for (int length = 2; length <= n; length++) {
            for (int left = 0; left + length <= n; left++) {
                int right = left + length - 1;
                int from = best[left][right - 1];
                int to = best[left + 1][right];
                if (from < left) {
                    from = left;
                }
                if (to > right - 1) {
                    to = right - 1;
                }
                long cost = Long.MAX_VALUE;
                int split = from;
                long sum = prefix[right + 1] - prefix[left];
                for (int middle = from; middle <= to; middle++) {
                    long candidate = dp[left][middle] + dp[middle + 1][right] + sum;
                    if (candidate < cost) {
                        cost = candidate;
                        split = middle;
                    }
                }
                dp[left][right] = cost;
                best[left][right] = split;
            }
        }
        System.out.println(n == 1 ? 0 : dp[0][n - 1]);
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
            long result = 0;
            while (c > 32) {
                result = result * 10 + c - '0';
                c = read();
            }
            return result;
        }

        int nextInt() throws Exception {
            return (int) nextLong();
        }
    }
}


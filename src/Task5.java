import java.io.BufferedInputStream;
import java.util.Arrays;

public class Task5 {
    private static final long NEGATIVE_INFINITY = Long.MIN_VALUE / 4;

    public static void main(String[] args) throws Exception {
        FastScanner in = new FastScanner();
        int n = in.nextInt();
        int m = in.nextInt();
        int k = in.nextInt();
        long[][] dp = new long[k + 1][m];
        for (long[] row : dp) {
            Arrays.fill(row, NEGATIVE_INFINITY);
        }
        long[] left = new long[k + 1];
        Arrays.fill(left, NEGATIVE_INFINITY);
        for (int i = 0; i < n; i++) {
            Arrays.fill(left, NEGATIVE_INFINITY);
            for (int j = 0; j < m; j++) {
                long value = in.nextLong();
                for (int used = k; used >= 0; used--) {
                    long previous = Math.max(dp[used][j], left[used]);
                    if (i == 0 && j == 0 && used == 0) {
                        previous = 0;
                    }
                    long result = previous == NEGATIVE_INFINITY ? NEGATIVE_INFINITY : previous + value;
                    if (value < 0 && used > 0) {
                        long screened = Math.max(dp[used - 1][j], left[used - 1]);
                        if (i == 0 && j == 0 && used == 1) {
                            screened = 0;
                        }
                        if (screened != NEGATIVE_INFINITY) {
                            result = Math.max(result, screened);
                        }
                    }
                    dp[used][j] = result;
                    left[used] = result;
                }
            }
        }
        long answer = NEGATIVE_INFINITY;
        for (int used = 0; used <= k; used++) {
            answer = Math.max(answer, dp[used][m - 1]);
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


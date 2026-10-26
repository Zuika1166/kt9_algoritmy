import java.io.BufferedInputStream;

public class Task2 {
    private static final int MOD = 1_000_000_007;

    public static void main(String[] args) throws Exception {
        FastScanner in = new FastScanner();
        int n = in.nextInt();
        int m = in.nextInt();
        int[] dp = new int[m];
        for (int i = 0; i < n; i++) {
            String row = in.next();
            for (int j = 0; j < m; j++) {
                if (row.charAt(j) == '#') {
                    dp[j] = 0;
                } else if (i == 0 && j == 0) {
                    dp[j] = 1;
                } else {
                    int left = j == 0 ? 0 : dp[j - 1];
                    int value = dp[j] + left;
                    if (value >= MOD) {
                        value -= MOD;
                    }
                    dp[j] = value;
                }
            }
        }
        System.out.println(dp[m - 1]);
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

        int nextInt() throws Exception {
            int c;
            do {
                c = read();
            } while (c <= 32);
            int sign = 1;
            if (c == '-') {
                sign = -1;
                c = read();
            }
            int result = 0;
            while (c > 32) {
                result = result * 10 + c - '0';
                c = read();
            }
            return result * sign;
        }

        String next() throws Exception {
            int c;
            do {
                c = read();
            } while (c <= 32);
            StringBuilder result = new StringBuilder();
            while (c > 32) {
                result.append((char) c);
                c = read();
            }
            return result.toString();
        }
    }
}


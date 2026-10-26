import java.io.BufferedInputStream;

public class Task1 {
    public static void main(String[] args) throws Exception {
        FastScanner in = new FastScanner();
        String s = in.next();
        if (s == null) {
            return;
        }
        int n = s.length();
        int[] dp = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            dp[i] = i;
        }
        for (int center = 0; center < n; center++) {
            for (int left = center, right = center; left >= 0 && right < n && s.charAt(left) == s.charAt(right); left--, right++) {
                dp[right + 1] = Math.min(dp[right + 1], dp[left] + 1);
            }
            for (int left = center - 1, right = center; left >= 0 && right < n && s.charAt(left) == s.charAt(right); left--, right++) {
                dp[right + 1] = Math.min(dp[right + 1], dp[left] + 1);
            }
        }
        System.out.println(dp[n]);
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

        String next() throws Exception {
            StringBuilder result = new StringBuilder();
            int c;
            do {
                c = read();
            } while (c <= 32 && c != -1);
            if (c == -1) {
                return null;
            }
            while (c > 32) {
                result.append((char) c);
                c = read();
            }
            return result.toString();
        }
    }
}


package exercises10;

import java.util.Arrays;

public class TestPrime {
    public static void main(String[] args) {
        final int N = 10_000_001;

        // 10.3.1:
        int[] a = new int[N];
        Arrays.parallelSetAll(a, i -> isPrime(i) ? 1 : 0);

        // 10.3.2
        Arrays.parallelPrefix(a, (x, y) -> x + y);
        System.out.println("pi(10,000,000) = " + a[10_000_000]);

        // 10.3.3
        for (int k = 1; k <= 10; k++) {
            int i = k * ((N - 1) / 10);
            double ratio = a[i] / (i / Math.log(i));
            System.out.printf("i = %,10d   pi(i) = %,7d   ratio = %.5f%n", i, a[i], ratio);
            // The ratio decreases very slowly towards 1.
        }
    }

  private static boolean isPrime(int n) {
    int k = 2;
    while (k * k <= n && n % k != 0)
        k++;
    return n >= 2 && k * k > n;
  }
}

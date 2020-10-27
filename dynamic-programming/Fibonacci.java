import java.util.*;

public class Fibonacci {

  /**
   * Returns the nth number in the fibonacci series. This is the naive approach.
   *
   * @param n - a nonnegative integer
   * @return the nth number in the fibonacci series.
   */
  public static int fibStandard(int n) {
    if (n == 0 || n == 1) {
      return 1;
    }

    return fibStandard(n - 1) + fibStandard(n - 2);
  }

  /**
   * Returns the nth number in the fibonacci series. This is the memoized
   * approach.
   *
   * @param n - a nonnegative integer
   * @return the nth number in the fibonacci series.
   */
  public static int fibMemo(int n) {
    int[] memo = new int[n + 1];
    return fibMemoHelper(n, memo);
  }

  private static int fibMemoHelper(int n, int[] memo) {
    // define the nth value if we haven't already
    if (memo[n] == 0) {
      if (n == 0 || n == 1) {
        memo[n] = 1;
      } else {
        memo[n] = fibMemoHelper(n - 1, memo) + fibMemoHelper(n - 2, memo);
      }
    }

    return memo[n];
  }

  public static void main(String[] args) {
    int val;
    long start;
    long end;

    // Time each approach for increasing values of n
    for (int n = 20; n < 45; n++) {
      // naive approach
      start = System.currentTimeMillis();
      val = fibStandard(n);
      end = System.currentTimeMillis();
      System.out.println(String.format("Naive Fib(%d) = %d:\t%d ms", n, val, end-start));

      // memo approach
      start = System.currentTimeMillis();
      val = fibMemo(n);
      end = System.currentTimeMillis();
      System.out.println(String.format("Memo Fib(%d) = %d:\t%d ms", n, val, end-start));
    }
  }
}
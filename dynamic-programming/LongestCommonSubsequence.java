/**
 * Memoized algorithm not properly written.
 * Slight optimization, but not most efficient solution.
 */

import java.util.*;

/**
 * Finding the longest common subsequence between two strings. Taken from
 * https://www.geeksforgeeks.org/longest-common-subsequence-dp-4/
 *
 * LCS Problem Statement: Given two sequences, find the length of longest
 * subsequence present in both of them. A subsequence is a sequence that appears
 * in the same relative order, but not necessarily contiguous. For example,
 * “abc”, “abg”, “bdf”, “aeg”, ‘”acefg”, .. etc are subsequences of “abcdefg”.
 */
public class LongestCommonSubsequence {
  /**
   * Naively finds the length of the longest common subsequence for the provided
   * strings.
   *
   * @param a - String a
   * @param b - String b
   * @return Length of the longest common subsequence of strings a and b
   */
  public static int naiveLCS(String a, String b) {
    if (a.length() == 0 || b.length() == 0) {
      return 0;
    }

    if (a.charAt(a.length() - 1) == b.charAt(b.length() - 1)) {
      return 1 + naiveLCS(a.substring(0, a.length() - 1), b.substring(0, b.length() - 1));
    } else {
      int left = naiveLCS(a.substring(0, a.length() - 1), b);
      int right = naiveLCS(a, b.substring(0, b.length() - 1));
      return Integer.max(left, right);
    }
  }

  /**
   * Memoized version of function to find the length of the longest common
   * subsequence for the provided strings.
   *
   * @param a - String a
   * @param b - String b
   * @return Length of the longest common subsequence of strings a and b
   */
  public static int memoLCS(String a, String b, Map<Set<String>, Integer> memo) {
    Set<String> key = new HashSet<String>();
    key.add(a);
    key.add(b);
    Integer cached = memo.get(key);

    // if result already exists, return
    if (cached != null) {
      return cached;
    }

    // calculate the result
    if (a.length() == 0 || b.length() == 0) {
      cached = 0;
    } else {
      if (a.charAt(a.length() - 1) == b.charAt(b.length() - 1)) {
        cached = 1 + naiveLCS(a.substring(0, a.length() - 1), b.substring(0, b.length() - 1));
      } else {
        cached = naiveLCS(a.substring(0, a.length() - 1), b);
        cached = Integer.max(cached, naiveLCS(a, b.substring(0, b.length() - 1)));
      }
    }

    // store the newly computed result and return
    memo.put(key, cached);
    return cached;
  }

  public static void main(String[] args) {
    String[][] stringPairs = { { "ABCDGH", "AEDFHR" }, { "AGGTAB", "GXTXAYB" }, { "JLDKLHFLASD", "HEIRUBKBSWAJ" },
        { "JLDKLHFGSFDLASD", "HEIRUDSFBKBSWAJ" }, { "JLDKLHFGBSASFAAGSFDLASD", "JLDKLHFGBSASFASD" } };
    int val;
    long start;
    long end;
    Map<Set<String>, Integer> memo = new HashMap<Set<String>, Integer>();

    for (String[] pair : stringPairs) {
      start = System.currentTimeMillis();
      val = memoLCS(pair[0], pair[1], memo);
      end = System.currentTimeMillis();
      System.out.println(String.format("Memo LCS = %d:\t%d ms", val, end - start));

      start = System.currentTimeMillis();
      val = naiveLCS(pair[0], pair[1]);
      end = System.currentTimeMillis();
      System.out.println(String.format("Naive LCS = %d:\t%d ms", val, end - start));
    }
  }
}


import java.util.*;

/**
 * Given a string s, return the longest palindromic substring in s.
 */
public class PalindromicSubstring {

  /**
   * Finds longest palindromic substring via naive approach.
   *
   * @param s - input String
   * @return Longest palindromic substring of s
   */
  public static String findSubstring(String s) {
    if (isPalindrome(s)) {
      return s;
    }

    // check for longest substrings, removing one char from the
    // left and from the right
    String left = findSubstring(s.substring(1));
    String right = findSubstring(s.substring(0, s.length() - 1));

    // return the longer of the two substrings
    if (left.length() > right.length()) {
      return left;
    }
    return right;
  }

  /**
   * Finds longest palindromic substring via memoized approach.
   *
   * @param s - input String
   * @return Longest palindromic substring of s
   */
  public static String findSubstringMemo(String s, Map<String, String> map) {
    String sub = map.get(s);

    // solution exists
    if (sub != null) {
      return sub;
    }

    // solution doesn't already exist
    if (isPalindrome(s)) {
      // current string is a palindrome
      sub = s;
    } else {
      // find longest sub palindrome
      String left = findSubstringMemo(s.substring(1), map);
      String right = findSubstringMemo(s.substring(0, s.length() - 1), map);

      if (left.length() > right.length()) {
        sub = left;
      } else {
        sub = right;
      }
    }

    // store solution
    map.put(s, sub);
    return sub;
  }

  /**
   * Checks whether the input string is a palindrome
   *
   * @param s - input string
   * @return True is s is a palindrom, False if it's not
   */
  private static boolean isPalindrome(String s) {
    int i = 0;
    int j = s.length() - 1;
    while (i < j) {
      if (s.charAt(i) != s.charAt(j)) {
        return false;
      }

      i++;
      j--;
    }

    return true;
  }

  public static void main(String[] args) {
    char[] alphabet = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
        't', 'u', 'v', 'w', 'x', 'y', 'z' };
    String solution;
    long start;
    long end;
    for (int i = 20; i < 30; i++) {
      String s = "";
      for (int j = 0; j < i; j++) {
        s += alphabet[(int) (Math.random() * alphabet.length)];
      }

      System.out.println("---");
      System.out.println("String:\t" + s);

      // memo approach
      start = System.currentTimeMillis();
      solution = findSubstringMemo(s, new HashMap<String, String>());
      end = System.currentTimeMillis();
      System.out.println(String.format("Memo Palindrome = %s:\t%d ms", solution, end - start));

      // naive approach
      start = System.currentTimeMillis();
      solution = findSubstring(s);
      end = System.currentTimeMillis();
      System.out.println(String.format("Naive Palindrome = %s:\t%d ms", solution, end - start));
    }
  }
}

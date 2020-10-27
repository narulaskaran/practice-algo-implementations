
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
    String[] inputs = { "babad", "cbbd", "a", "ac", "babcbabcbaccba", "forgeeksskeegfor",
        "bigwordracecarracecaristhecoolboy",
        "civilwartestingwhetherthatnaptionoranynartionsoconceivedandsodedicatedcanlongendureWeareqmetonagreatbattlefiemldoftzhatwarWehavecometodedicpateaportionofthatfieldasafinalrestingplaceforthosewhoheregavetheirlivesthatthatnationmightliveItisaltogetherfangandproperthatweshoulddothisButinalargersensewecannotdedicatewecannotconsecratewecannothallowthisgroundThebravelmenlivinganddeadwhostruggledherehaveconsecrateditfaraboveourpoorponwertoaddordetractTgheworldadswfilllittlenotlenorlongrememberwhatwesayherebutitcanneverforgetwhattheydidhereItisforusthelivingrathertobededicatedheretotheulnfinishedworkwhichtheywhofoughtherehavethusfarsonoblyadvancedItisratherforustobeherededicatedtothegreattdafskremainingbeforeusthatfromthesehonoreddeadwetakeincreaseddevotiontothatcauseforwhichtheygavethelastpfullmeasureofdevotionthatweherehighlyresolvethatthesedeadshallnothavediedinvainthatthisnationunsderGodshallhaveanewbirthoffreedomandthatgovernmentofthepeoplebythepeopleforthepeopleshallnotperishfromtheearth" };
    String solution;
    long start;
    long end;
    for (String s : inputs) {
      // memo approach
      start = System.currentTimeMillis();
      solution = findSubstringMemo(s, new HashMap<String, String>());
      end = System.currentTimeMillis();
      System.out.println(String.format("Memo Palindrome(%s) = %s:\t%d ms", s, solution, end - start));

      // naive approach
      start = System.currentTimeMillis();
      solution = findSubstring(s);
      end = System.currentTimeMillis();
      System.out.println(String.format("Naive Palindrome(%s) = %s:\t%d ms", s, solution, end - start));
    }
  }
}

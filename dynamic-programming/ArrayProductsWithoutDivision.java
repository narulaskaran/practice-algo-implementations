/**
 * Given an array of numbers, produce a new array where each element is the
 * product of all elements in the original array except the current one.
 *
 * The catch is: do this without using division.
 */
public class ArrayProductsWithoutDivision {

  /**
   * Naively generate a new int[] where each element is the product of all other
   * elements. Runs in O(N^2) time using O(N) space.
   *
   * @param arr - input int[]
   * @return new int[] where each element is the product of all other elements of
   *         arr
   */
  public static int[] naive(int[] arr) {
    int[] products = new int[arr.length];

    int product;
    for (int i = 0; i < arr.length; i++) {
      product = 1;
      for (int j = 0; j < arr.length; j++) {
        if (i != j) {
          product *= arr[j];
        }
      }
      products[i] = product;
    }

    return products;
  }

  /**
   * Efficiently generate a new int[] where each element is the product of all
   * other elements. Runs in O(N) time using O(N) space.
   *
   * Calculates prefix and suffix arrays wherein the ith element of each is equal
   * to the running product of all values to either the left or the right of i
   * from arr.
   *
   * @param arr - input int[]
   * @return new int[] where each element is the product of all other elements of
   *         arr
   */
  public static int[] memo(int[] arr) {
    int len = arr.length;
    int[] prefixes = new int[len];
    int[] suffixes = new int[len];
    int[] products = new int[len];

    // find prefixes
    int product = 1;
    for (int i = 0; i < len; i++) {
      prefixes[i] = product;
      product *= arr[i];
    }

    // find suffixes
    product = 1;
    for (int i = len - 1; i >= 0; i--) {
      suffixes[i] = product;
      product *= arr[i];
    }

    // calc product for each index using prefix and suffix
    for (int i = 0; i < len; i++) {
      product = 1;
      product *= prefixes[i];
      product *= suffixes[i];
      products[i] = product;
    }

    return products;
  }

  /**
   * Helper to print out an int array.
   * @param arr - int[] to print out
   * @return - String representation of arr
   */
  public static String printArr(int[] arr) {
    StringBuilder str = new StringBuilder();
    str.append("[");
    for (int i = 0; i < arr.length - 1; i++) {
      str.append(arr[i]);
      str.append(", ");
    }
    str.append(arr[arr.length - 1]);
    str.append("]");

    return str.toString();
  }

  public static void main(String[] args) {
    // random int array where elements range from -50 to 50
    int[] arr = new int[100000];
    for (int i = 0; i < arr.length; i++) {
      arr[i] = (int) (Math.random() * 100) - 50;
    }

    long start, end;
    int[] val;

    System.out.println("Input Array Length:\t" + arr.length);

    // memo
    start = System.currentTimeMillis();
    val = memo(arr);
    end = System.currentTimeMillis();
    System.out.println(String.format("Memo Calculation Time:\t%d ms", end - start));

    // maive
    start = System.currentTimeMillis();
    val = naive(arr);
    end = System.currentTimeMillis();
    System.out.println(String.format("Naive Calculation Time:\t%d ms", end - start));
  }
}

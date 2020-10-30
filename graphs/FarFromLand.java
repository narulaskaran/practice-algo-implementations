import java.util.*;

/**
 * Taken from Leetcode
 * https://leetcode.com/problems/as-far-from-land-as-possible/
 *
 * Given an N x N grid containing only values 0 and 1, where 0 represents water
 * and 1 represents land, find a water cell such that its distance to the
 * nearest land cell is maximized and return the distance.
 *
 * The distance used in this problem is the Manhattan distance: the distance
 * between two cells (x0, y0) and (x1, y1) is |x0 - x1| + |y0 - y1|.
 *
 * If no land or water exists in the grid, return -1.
 */

public class FarFromLand {
  /**
   * Takes in a 2D cartography array. Returns the max possible distance from land
   * across all cells.
   *
   * @param map - 2D array representing the water and island layout to use.
   * @return Integer value representing max possible distance from land.
   */
  public static int maxDistance(int[][] map) {
    int rows = map.length;
    int cols = map[0].length;

    // Land cells have 0 steps from themselves
    // All other cells are initialized as Integer.MAX_VALUE
    // Add land cell coordinates to Queue and move outwards via BFS
    int[][] distances = new int[rows][cols];
    List<List<Integer>> queue = new ArrayList<List<Integer>>();
    Set<List<Integer>> visited = new HashSet<List<Integer>>();
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        if (map[row][col] == 1) {
          distances[row][col] = -1;
          queue.add(arrToList(new int[] { row, col }));
        } else {
          distances[row][col] = Integer.MAX_VALUE;
        }
      }
    }

    // Iterate over all cells in the queue
    int maxDistance = Integer.MIN_VALUE;
    while (!queue.isEmpty()) {
      List<Integer> cell = queue.remove(0);

      // mark cell as visited
      visited.add(cell);

      // reassign cell value to min of current and surrounding cells
      // enqueue unvisited neighbors
      int row = cell.get(0);
      int col = cell.get(1);
      int val = distances[row][col];
      int[][] neighbors = { { row - 1, col }, { row + 1, col }, { row, col - 1 }, { row, col + 1 } };
      for (int[] neighbor : neighbors) {
        if (neighbor[0] >= 0 && neighbor[0] < rows && neighbor[1] >= 0 && neighbor[1] < cols) {
          val = Integer.min(val, distances[neighbor[0]][neighbor[1]]);
          if (!visited.contains(arrToList(neighbor))) {
            queue.add(arrToList(neighbor));
          }
        }
      }
      val++;
      distances[row][col] = val;
      maxDistance = Integer.max(maxDistance, val);
    }

    return maxDistance;
  }

  /**
   * Convert int[]] to List<Integer>
   *
   * @param arr - int[] to convert
   * @return - List<Integer> matching the values of arr
   */
  public static List<Integer> arrToList(int[] arr) {
    List<Integer> list = new ArrayList<Integer>(arr.length);
    for (int i : arr) {
      list.add(i);
    }

    return list;
  }

  /**
   * Run with test cases
   */
  public static void main(String[] args) {
    int[][] arr1 = { { 1, 0, 0 }, { 0, 0, 0 }, { 0, 0, 0 } };
    int[][] arr2 = { { 1, 0, 1 }, { 0, 0, 0 }, { 1, 0, 1 } };

    System.out.println(maxDistance(arr1));
    System.out.println(maxDistance(arr2));
  }
}

import java.util.Set;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private WeightedQuickUnionUF model;
    private int n;
    private int top;
    private int bottom;
    private Set<Integer> opened;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be a positive integer");
        }

        this.n = n;
        this.top = 0;
        this.bottom = n + 1;
        model = new WeightedQuickUnionUF(n + 2);
        opened = new Set<Integer>();

        // connect top row to top
        for (int i = 1; i < Math.sqrt(n); i++) {
            this.model.union(this.top, i);
        }

        // connect bottom row to bottom
        for (int i = n; i > n - Math.sqrt(n); i--) {
            this.model.union(this.bottom, i);
        }
    }

    private int convertToId(int row, int col) {
        if (row < 1 || col < 1 || row > this.n || col > this.n) {
            throw new IllegalArgumentException("Row or Col index is out of bounds");
        }

        return (int) (Math.sqrt(this.n) * (row - 1)) + col;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        int id = convertToId(row, col);
        if (!this.isOpen(row, col)) {
            // union to the top, bottom, left, right if they're open
            if (row > 1 && this.isOpen(row - 1, col)) {
                this.model.union(id, convertToId(row - 1, col));
            }
            if (col > 1 && this.isOpen(row, col - 1)) {
                this.model.union(id, convertToId(row, col - 1));
            }
            if (col < this.n && this.isOpen(row, col + 1)) {
                this.model.union(id, convertToId(row, col + 1));
            }
            if (row < this.n && this.isOpen(row + 1, col)) {
                this.model.union(id, convertToId(row + 1, col));
            }

            // mark as open
            opened.add(id);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return opened.contains(convertToId(row, col));
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return this.model.connected(this.top, convertToId(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.opened.size();
    }

    // does the system percolate?
    public boolean percolates() {
        return this.model.connected(this.top, this.bottom);
    }

    // test client (optional)
    public static void main(String[] args) {

    }
}
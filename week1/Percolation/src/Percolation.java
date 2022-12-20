import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final WeightedQuickUnionUF uf;
    private final boolean[][] grid;
    private int openSitesCount;
    private final int n;

    public Percolation(int n) {
        uf = new WeightedQuickUnionUF(n * n + 2);

        for (var i = 1; i <= n; i++) {
            uf.union(0, i);
            uf.union(n * n + 1, (n - 1) * n + i);
        }

        grid = new boolean[n][n];
        openSitesCount = 0;
        this.n = n;
    }

    private void validateRowColArguments(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException();
        }
    }

    public void open(int row, int col) {
        validateRowColArguments(row, col);

        if (!isOpen(row, col)) {
            grid[row - 1][col - 1] = true;
            openSitesCount++;

            final int[][] neighbors = {
                {-1, 0},
                {0, -1},
                {0, 1},
                {1, 0},
            };

            for (var neighbor : neighbors) {
                var neighborRow = row + neighbor[0];
                var neighborCol = col + neighbor[1];

                try {
                    validateRowColArguments(neighborRow, neighborCol);

                    if (grid[neighborRow - 1][neighborCol - 1]) {
                        uf.union((row - 1) * n + col, (neighborRow - 1) * n + neighborCol);
                    }
                }
                catch (IllegalArgumentException ignored) { }
            }
        }
    }

    public boolean isOpen(int row, int col) {
        validateRowColArguments(row, col);

        return grid[row - 1][col - 1];
    }

    public boolean isFull(int row, int col) {
        validateRowColArguments(row, col);

        return grid[row - 1][col - 1] && uf.find(0) == uf.find((row - 1) * n + col);
    }

    public int numberOfOpenSites() {
        return openSitesCount;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(0) == uf.find(n * n + 1);
    }

    public static void main(String[] args) {
        var p = new Percolation(5);

        var percolates = p.percolates();

        p.open(1,1);

        var isOpen = p.isOpen(1,1);

        p.open(1,2);
        p.open(1,4);
        p.open(2,4);
        p.open(3,4);
        p.open(3,5);
        p.open(5,4);
        p.open(5,5);

        percolates = p.percolates();

        p.open(4,4);

        percolates = p.percolates();

        isOpen = p.isOpen(4,4);
        isOpen = p.isOpen(5,1);

        var isFull = p.isFull(3,2);
        isFull = p.isFull(1,1);
        isFull = p.isFull(4,4);
    }
}

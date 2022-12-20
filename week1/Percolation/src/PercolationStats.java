import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final double mean;

    private final double stddev;

    private final double confidenceLo;

    private final double confidenceHi;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }

        final int sitesCount = n * n;

        var thresholdByTrial = new double[trials];

        for (var i = 0; i < trials; i++) {
            var percolation = new Percolation(n);

            var openedSites = 0;

            while (true) {
                var siteIndex = StdRandom.uniformInt(0, sitesCount);
                var siteRow = siteIndex / n + 1;
                var siteCol = siteIndex % n + 1;

                if (!percolation.isOpen(siteRow, siteCol)) {
                    percolation.open(siteRow, siteCol);
                    openedSites++;

                    if(percolation.percolates()) {
                        break;
                    }
                }
            }

            thresholdByTrial[i] = (double)openedSites / (n * n);
        }

        mean = StdStats.mean(thresholdByTrial);

        stddev = StdStats.stddev(thresholdByTrial);

        confidenceLo = mean - 1.96 * stddev / Math.sqrt(trials);

        confidenceHi = mean + 1.96 * stddev / Math.sqrt(trials);
    }

    public double mean() {
        return mean;
    }

    public double stddev() {
        return stddev;
    }

    public double confidenceLo() {
        return confidenceLo;
    }

    public double confidenceHi() {
        return confidenceHi;
    }

    public static void main(String[] args) {
        var percolationStats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));

        StdOut.printf("mean                    = %.10f\n", percolationStats.mean());
        StdOut.printf("stddev                  = %.10f\n", percolationStats.stddev());
        StdOut.printf("95%% confidence interval = [%.10f, %.10f]\n", percolationStats.confidenceLo(), percolationStats.confidenceHi());
    }
}
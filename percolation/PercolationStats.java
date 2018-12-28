/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private Percolation perc;
    private int n, trials;
    private double[] numOfOpenSites;
    private double mean, stddev;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new java.lang.IllegalArgumentException("n and trials should be larger than 0");

        this.n = n;
        this.trials = trials;
        this.numOfOpenSites = new double[this.trials];
        for (int i = 0; i < this.trials; i++) {
            this.perc = new Percolation(n);
            while (!this.perc.percolates()) {
                int nextOpenIndex = StdRandom.uniform(1, this.n * this.n + 1); // intervals are half open, like [a, b)
                int[] site = this.perc.index2site(nextOpenIndex);
                int row = site[0];
                int col = site[1];
                this.perc.open(row, col);
            }
            this.numOfOpenSites[i] = (double) this.perc.numberOfOpenSites() * 1.0 / (n * n);
        }
    }

    public double mean() {
        this.mean = StdStats.mean(this.numOfOpenSites);
        return this.mean;
    }

    public double stddev() {
        this.stddev = StdStats.stddev(this.numOfOpenSites);
        return this.stddev;
    }

    public double confidenceLo() {
        return this.mean - 1.96 * this.stddev / Math.sqrt(this.trials);
    }

    public double confidenceHi() {
        return this.mean + 1.96 * this.stddev / Math.sqrt(this.trials);
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats percS = new PercolationStats(n, trials);
        double mean = percS.mean();
        double stddev = percS.stddev();
        double confidenceLo = percS.confidenceLo();
        double confidenceHi = percS.confidenceHi();
        System.out.println("mean                    = " + mean);
        System.out.println("stddev                  = " + stddev);
        System.out.println("95% confidence interval = " + "[" + confidenceLo + ", " + confidenceHi + "]");
    }
}

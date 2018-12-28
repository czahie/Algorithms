/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final int n, trials;
    private double mean, stddev;

    public PercolationStats(int numbers, int trials) {
        if (numbers <= 0 || trials <= 0) throw new java.lang.IllegalArgumentException("n and trials should be larger than 0");

        this.n = numbers;
        this.trials = trials;
        double[] percentOfOpenSites = new double[this.trials];
        for (int i = 0; i < this.trials; i++) {
            Percolation perc = new Percolation(n);
            while (!perc.percolates()) {
                int nextOpenIndex = StdRandom.uniform(1, this.n * this.n + 1); // intervals are half open, like [a, b)
                int[] site = this.index2site(nextOpenIndex);
                int row = site[0];
                int col = site[1];
                perc.open(row, col);
            }
            percentOfOpenSites[i] = (double) perc.numberOfOpenSites() / (n * n);
        }

        this.mean = StdStats.mean(percentOfOpenSites);
        this.stddev = StdStats.stddev(percentOfOpenSites);
    }

    /**
     * Given the index i, return the site in the gird
     * @param n the index
     * */
    private int[] index2site(int n) {
        int col = n % this.n;
        int row = n / this.n + 1;
        if (col == 0) {
            col = this.n;
            row -= 1;
        }
        int[] site = new int[]{row, col};
        return site;
    }

    public double mean() {
        return this.mean;
    }

    public double stddev() {
        return this.stddev;
    }

    /**
     * To imporve the performance of confidenceLo() and confidenceHi() by only using 1.96 once */
    private double confidenceDev() {
        return 1.96 * this.stddev / Math.sqrt(this.trials);
    }

    public double confidenceLo() {
        return this.mean - this.confidenceDev();
    }

    public double confidenceHi() {
        return this.mean + this.confidenceDev();
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

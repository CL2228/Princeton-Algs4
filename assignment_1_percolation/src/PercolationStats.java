import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdIn;
import java.lang.Integer;

public class PercolationStats {
    private double rate[];
    private double mean;
    private double stddev;
    private double confidenceLo;
    private double confidenceHi;
    private int T;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials){
        if(n <= 0 || trials <= 0)
            throw new IllegalArgumentException("not right parameters");
        rate = new double[trials];
        T = trials;
        for(int i = 0; i < trials; i++){
            Percolation percolation = new Percolation(n);
            while(!percolation.percolates()){
                int row = StdRandom.uniform(1,n + 1);
                int col = StdRandom.uniform(1,n + 1);
                percolation.open(row, col);
            }
            rate[i] = (double)percolation.numberOfOpenSites()/(double)(n*n);
        }
    }

    // sample mean of percolation threshold
    public double mean(){
        mean = StdStats.mean(rate);
        return StdStats.mean(rate);
    }

    // sample standard deviation of percolation threshold
    public double stddev(){
        stddev = StdStats.stddev(rate);
        return StdStats.stddev(rate);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo(){
        confidenceLo = mean - 1.96 * stddev / Math.sqrt((double) T);
        return confidenceLo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi(){
        confidenceHi = mean + 1.96 * stddev / Math.sqrt((double) T);
        return confidenceHi;
    }

    // test client (see below)
    public static void main(String[] args){
        PercolationStats percolationStats = new PercolationStats(
                Integer.parseInt(args[0]), Integer.parseInt(args[1]));

        System.out.println("mean                    = " + percolationStats.mean());
        System.out.println("stddev                  = " + percolationStats.stddev());
        System.out.println("95% confidence interval = [" + percolationStats.confidenceLo()
                + " , " + percolationStats.confidenceHi() + " ]");
    }
}

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class PercolationStats {
    private double[] openArray;
    private int trials;
    private int dim;
    
    // Perform trials independent experiments on an n x n grid
    public PercolationStats(int n, int trials) {
	dim = n;
	trials = trials;
	
	// If user enters n <= 0, throw exception
	if (n <= 0) throw new IllegalArgumentException("n must be positive");

	// If user enters trials <= 0, throw exception
	if (trials <= 0) throw new IllegalArgumentException("number of trials must be positive");

	// Initialize empty array to store counts of num. open positions for each trial
	openArray = new double[trials];
	double gridSize = (double) n * n;
	
	for (int t = 0; t < trials; t++) {
	    // Run a trial
	    Percolation p = new Percolation(n);

	    while (!p.percolates()) {
		int row = StdRandom.uniform(1, n+1);
		int col = StdRandom.uniform(1, n+1);

		// If position (row, col) is not open, open it
		if (!p.isOpen(row, col)) {
		    p.open(row, col);
		}
	    }

	    // Initialize number of open positions to zero
	    int numOpen = 0;
	    
	    // Count up the number of open positions in grid
	    for (int i = 1; i <= n; i++) {
		for (int j = 1; j <= n; j++) {
		    if (p.isOpen(i, j)) numOpen++;
		}
	    }

	    // Store in array
	    openArray[t] = ((double) numOpen) / gridSize;
	}
    }

    // Sample mean of percolation threshold
    public double mean() {
	return StdStats.mean(openArray);
    }

    // Sample standard deviation of percolation threshold
    public double stddev() {
	return StdStats.stddev(openArray);
    }

    // Low endpoint of 95% confidence interval
    public double confidenceLo() {
	double mu = this.mean();  
	double sigma = this.stddev(); 

	int numTrials = openArray.length;
	
	return (mu - (1.96*sigma / Math.sqrt(numTrials)));
    }

    // High endpoint of 95% confidence interval
    public double confidenceHi() {
	double mu = this.mean();
	double sigma = this.stddev();
	
	int numTrials = openArray.length;
	
	return (mu + (1.96*sigma / Math.sqrt(numTrials)));
    }

    // Test client
    public static void main(String[] args) {
	// int n = Integer.parseInt(args[0]);
	//int trials = Integer.parseInt(args[1]);
	
	int n = StdIn.readInt();
	int trials = StdIn.readInt();

	PercolationStats ps = new PercolationStats(n, trials);

	// Print the mean
	StdOut.printf("%-23s = ", "mean");
	StdOut.printf("%10.8f", ps.mean());
	StdOut.println();
	
	// Print the standard deviation
	StdOut.printf("%-23s = ", "standard deviation");
	StdOut.printf("%10.8f", ps.stddev());
	StdOut.println();

	// Print the confidence interval
	StdOut.printf("%-23s = ", "95% confidence interval");
	StdOut.printf("%10.8f, %10.8f", ps.confidenceLo(), ps.confidenceHi());
	StdOut.println();
    }
}

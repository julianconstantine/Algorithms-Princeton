import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private WeightedQuickUnionUF uf;

    // Create an n x n grid, with all sites blocked
    public Percolation(int n) {
	// Throw an exception if a non-positive integer is passed to constructor
	if (n <= 0) {
	    throw new IllegalArgumentException("n must be a positive integer");
	}
	
	grid = new boolean[n][n];

	// Mark every entry in the grid as closed (false)
	for (int i = 0; i < n; i++) {
	    for (int j = 0; j < n; j++) {
		grid[i][j] = false;
	    }
	}

	uf = new WeightedQuickUnionUF(n * n + 2);
    }

    public void open(int i, int j) {
	// Length of the grid (n)
	int n = grid[0].length;

	if (i < 1 || i > n) {
	    throw new IndexOutOfBoundsException("Index i must be between 1 and n");
	} else if (j < 1 || j > n) {
	    throw new IndexOutOfBoundsException("Index j must be between 1 and n");
	}

	// Mark position (i-1, j-1) as open (true on the grid)
	grid[i-1][j-1] = true;
	
	int site = (i - 1)*n + j - 1 + 1;  // Index of this site in UF
	    
	if (i != 1) {
	    int above = (i-2)*n + j - 1 + 1;  // Index of site above in UF

	    if (this.isOpen(i-1, j)) {
		// Connect this site with the one above it
		uf.union(site, above);
	    }
	} else {
	    // Connect to top pointer (always open)
	    uf.union(site, 0);
	}
	
	
	if (i != n) {
	    int below = i*n + j - 1 + 1;  // Index of site below in UF
	    if (this.isOpen(i+1, j)) {
		// Connect this site with the one below it
		uf.union(site, below);
	    }
	} else {
	    // Connect to bottom pointer (always open)
	    uf.union(site, n * n + 1);
	}

	if (j != 1) {
	    int left = (i - 1)*n + j - 2 + 1;  // Index of left site in UF
	    if (this.isOpen(i, j-1)) uf.union(site, left);
	}

	if (j != n) {
	    int right = (i - 1)*n + j + 1;  // Index of right site in UF
	    if (this.isOpen(i, j+1)) uf.union(site, right);
	}
    }

    public boolean isOpen(int i, int j) {
	int n = grid[0].length;

	if (i < 1 || i > n) {
	    throw new IndexOutOfBoundsException("Index i must be between 1 and n");
	} else if (j < 1 || j > n) {
	    throw new IndexOutOfBoundsException("Index j must be between 1 and n");
	}

	return grid[i-1][j-1];
    }

    public boolean isFull(int i, int j) {
	int n = grid[0].length;

	if (i < 1 || i > n) {
	    throw new IndexOutOfBoundsException("Index i must be between 1 and n");
	} else if (j < 1 || j > n) {
	    throw new IndexOutOfBoundsException("Index j must be between 1 and n");
	}

	int site = (i - 1)*n + j - 1 + 1;

	return grid[i-1][j-1] && uf.connected(site, 0);
    }

    public boolean percolates() {
	// Length of array
	int n =	grid[0].length;
	
	// Check if top and bottom root pointers are connected
	return uf.connected(0, n * n + 1);
    }

    public static void main(String[] args) {
	int n = Integer.parseInt(args[0]);  // Read size of grid from command line
	Percolation p = new Percolation(n);

	while (!p.percolates()) {
	    // Choose random indices i,j to mark as open
	    int i = StdRandom.uniform(1, n+1);
	    int j = StdRandom.uniform(1, n+1);

	    // System.out.print(i + " ");
	    // System.out.println(j);
	    
	    if (!p.isOpen(i, j)) {
		// If position (i, j) is not, mark as open
		p.open(i, j);
	    }
	}

	// System.out.println(counter);

	// Count up the number of vacant positions in the grid
	int numOpen = 0;

	for (int i = 1; i <= n; i++) {
	    for (int j = 1; j <= n; j++) {
		if (p.isOpen(i, j)) numOpen++;
	    }
	}

	System.out.println(numOpen);
    }
}

// Implemention of the Quick-Union algorithm (to oslve the Union-FInd problen)

public class WeightedQuickUnionUF {
    private int[] id;
    private int[] sz;
    
    public WeightedQuickUnionUF(int N) {
	// NOTE: Need to use new keyword with all arrays? Even primitive types?
	id = new int[N];
	
	// Initialize id array to numbers 0, 1, ..., N-1
	// N array accesses
	for (int i = 0; i < N; i++) {
	    id[i] = i;
	    sz[i] = 1;
	}
    }
    
    // Compute the root of an element
    // Depth of i array accesses
    private int root(int i) {
	while (i != id[i]) {
	    i = id[i];
	}

	return i;
    }
    
    // Check if p and q are connected
    // Depth of p and q array accesses
    public boolean connected(int p, int q) {
	return root(p) == root(q);
    }
    
    // Take the union of p and q
    // Depth of p and q array accesses
    public void union(int p, int q) {
	int i = root(p);
	int j = root(q); 

	if (i == j) return;
	if (sz[i] < sz[j]) {
	    // If Ti is smaller than Tj, set the root of i to be the root of j
	    id[i] = j;
	    sz[j] += sz[i];
	} else {
	    // If Tj is smaller than Ti, set the root of j to be the root of i
	    id[j] = i;
	    sz[i] += sz[j];
	}
    }
}

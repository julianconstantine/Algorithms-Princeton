// Quick-find implemention ofof the Union-Find problem

public class QuickFindUF {
    private int[] id;
    
    // Class constructor
    public QuickFindUF(int N) {
	id = new int[N];

	// Initialize id to be the numbers 0 through N
	for (int i = 0; i < N; i++) {
	    id[i] = i;
	}
    }

    // Find/connected operation
    public boolean connected(int p, int q) {
	return id[p] == id[q];
    }

    // Union operation
    public void union(int p, int q) {
	// Get id's of p and q 
	// NOTE: Must do this else will screw up pointers!
	int pid = id[p];
	int qid = id[q];

	for (int i = 0; i < id.length; i++) {
	    // Set all id's equal to id[p] to id[q]
	    if (id[i] == pid) id[i] = qid;
	}
    }
}
/*
  SELECTION SORT ALGORITHM
 */

public class Selection {
    public static void sort(Comparable[] a) {
	int N = a.length;
	
	// Loop through all elements in array
	for (int i = 0; i < n; i++) {
	    int min = i;
	    
	    // On iteration i, loop through all elements to the right of i and search for the smallest  element
	    for (int j = i + 1; j < N; j++) {
		if (less(a[j], a[min])) {
		    min = j;
		}
	    }

	    // Swap a[1] with the smallest element to the right of i
	    exch(a, i, min);
	}
    }

    private static boolean less(Comparable v, Comparable w) {
	return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
	Comparable swap = a[i];
	a[i] = a[j];
	a[j] = swap;
    }
}
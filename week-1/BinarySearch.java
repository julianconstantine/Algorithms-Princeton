// Implementation of binary search algorithm for sorted array
// From Week 1: Order-of-Growth Classifications

public class BinarySearch {
    public static int binarySearch(int[] a, int key) {
	int low = 0;
	int high = a.length - 1;
	
	while (low <= high) {
	    int mid = low + (high - low) / 2;

	    if (key < a[mid]) high = mid - 1;
	    else if (key > a[mid]) low = mid + 1;
	    else return mid;
	}
	
	// Return -1 if number not found
	return -1;
    }

    public static void main(String[] args) {
    
    }
}
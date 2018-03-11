public class MergeBU {
    private static Comparable[] aux;

    private static void merge(Comparable[] a, int low, int mid, int high) {
        assert isSorted(a, low, mid);   // Precondition: a[low:mid] is sorted
        assert isSorted(a, mid+1, high);   // Precondition: a[mid+1:high] is sorted

        // Copy over a[low:high+1] to the auxiliary array
        for (int k = low; k <= high; k++) aux[k] = a[k];

        // Initialize indices i and j to low and mid+1 respectively
        int i = low, j = mid+1;

        // Merge the two sorted subarrays
        for (int k = low; k <= high; k++) {
            if (i > mid) {
                // If i pointer is exhausted, copy from right half and increment j
                a[k] = aux[j++];
            } else if (j > high) {
                // If j pointer is exhausted, copy from left half and increment i
                a[k] = aux[i++];
            } else if (less(aux[j], aux[i])) {
                // If neither pointers are exhausted, copy from whichever half has the smallest current element
                a[k] = aux[j++];
            } else {
                a[k] = aux[i++];
            }
        }

        assert isSorted(a, low, high);
    }

    public static void sort(Comparable[] a) {
        int N = a.length;

        aux = new Comparable[N];

        for (int sz = 1; sz < N; sz *= 2) {
            for (int low = 0; low < N - sz; low += 2*sz) {
                merge(a, low, low + sz - 1, Math.min(low + 2*sz - 1, N-1));
            }
        }
    }
}

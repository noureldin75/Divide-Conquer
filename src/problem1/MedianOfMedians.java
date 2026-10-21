package problem1;

public class MedianOfMedians implements SelectionAlgorithm {

    @Override
    public int select(int[] arr, int k) {
        return select(arr, 0, arr.length - 1, k);
    }

    private int select(int[] arr, int low, int high, int k) {
        if (low == high) {
            return arr[low];
        }
        int pivotIndex = medianOfMedians(arr, low, high);
        pivotIndex = partition(arr, low, high, pivotIndex);
        int rank = pivotIndex - low + 1;

        return recursion(arr, low, high, k, rank, pivotIndex);
    }

    private int recursion(int[] arr, int low, int high, int k, int rank, int pivotIndex) {
        if (k == rank) {
            return arr[pivotIndex];
        }
        else if (k < rank) {
            return select(arr, low, pivotIndex - 1, k);
        }
        else {
            return select(arr, pivotIndex + 1, high, k - rank);
        }
    }

    private int medianOfMedians(int[] arr, int low, int high) {
        int n = high - low + 1;
        if (n <= 5) {
            insertionSort(arr, low, high);
            return low + (n - 1) / 2;
        }

        int numMedians = (n + 4) / 5;
        for (int i = 0; i < numMedians; i++) {
            int subLow = low + i * 5;
            int subHigh = Math.min(subLow + 4, high);
            insertionSort(arr, subLow, subHigh);
            swap(arr, low + i, subLow + (subHigh - subLow) / 2);
        }

        int mid = (numMedians + 1) / 2;
        int medianValue = select(arr, low, low + numMedians - 1, mid);

        for (int i = low; i < low + numMedians; i++) {
            if (arr[i] == medianValue) {
                return i;
            }
        }
        throw new IllegalStateException("median of medians not found");
    }

    private int partition(int[] arr, int low, int high, int pivotIndex) {
        int pivotValue = arr[pivotIndex];
        swap(arr, pivotIndex, high);
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivotValue) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    private void insertionSort(int[] arr, int low, int high) {
        for (int i = low + 1; i <= high; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= low && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    private void swap(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    @Override
    public String name() {
        return "Median of Medians";
    }
}
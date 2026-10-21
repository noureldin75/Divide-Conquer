package problem1;

import java.util.Random;

public class RandomizedSelect implements SelectionAlgorithm {
    private final Random rand = new Random();

    @Override
    public int select(int[] arr, int k) {
        return select(arr, 0, arr.length - 1, k);
    }

    @Override
    public String name() {
        return "Randomized Select";
    }

    private int select(int[] arr, int low, int high, int k) {
        if (low == high) {
            return arr[low];
        }
        int pivotIndex = randomizedPartition(arr, low, high);
        int rank = pivotIndex - low + 1;   // rank of pivot within this range

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


    private int randomizedPartition(int[] arr, int low, int high) {
        int randomIndex = low + rand.nextInt(high - low + 1);
        swap(arr, randomIndex, high);   // move random pivot to the end
        return partition(arr, low, high);
    }

    private int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    private void swap(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
}
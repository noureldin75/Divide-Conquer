package problem1;

import java.util.Arrays;

public class SortSelect implements SelectionAlgorithm {

    @Override
    public int select(int[] arr, int k) {
        Arrays.sort(arr);
        return arr[k - 1];
    }

    @Override
    public String name() {
        return "Sort Select";
    }
}
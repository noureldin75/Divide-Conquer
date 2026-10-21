package problem1;

import java.util.Random;

public class Problem1Test {

    private static int passed = 0;
    private static int failed = 0;

    private static final SelectionAlgorithm[] ALGOS = {
            new RandomizedSelect(),
            new MedianOfMedians(),
            new SortSelect()
    };

    public static void main(String[] args) {
        testSingleElement();
        testSorted();
        testReverseSorted();
        testMinAndMax();
        testNegativeNumbers();
        testRandomArrays();

        System.out.println("Passed: " + passed + ", Failed: " + failed);
    }

    private static void testSingleElement() {
        int[] arr = { 42 };
        check("Single Element", arr, 1, 42);
    }

    private static void testSorted() {
        int[] arr = { 10, 20, 30, 40, 50, 60, 70 };
        check("Sorted Array", arr, 4, 40);
    }

    private static void testReverseSorted() {
        int[] arr = { 90, 80, 70, 60, 50, 40, 30, 20, 10 };
        check("Reverse Sorted", arr, 5, 50);
    }

    private static void testMinAndMax() {
        int[] arr = { 15, 3, 99, 8, 42, 1, 77 };
        check("Min Element", arr, 1, 1);
        check("Max Element", arr, 7, 99);
    }

    private static void testNegativeNumbers() {
        int[] arr = { -50, -10, -30, -20, -40 };
        check("Negative Numbers", arr, 3, -30);
    }

    private static void testRandomArrays() {
        Random rand = new Random(55);
        boolean ok = true;

        for (int t = 0; t < 30; t++) {
            int n = 1 + rand.nextInt(200);
            int k = 1 + rand.nextInt(n);
            int[] base = new int[n];
            for (int i = 0; i < n; i++)
                base[i] = rand.nextInt();

            int expected = new SortSelect().select(base.clone(), k);
            int resRand = new RandomizedSelect().select(base.clone(), k);
            int resMoM = new MedianOfMedians().select(base.clone(), k);

            if (resRand != expected || resMoM != expected) {
                System.out.println("FAIL: Random Test " + t);
                ok = false;
                failed++;
                break;
            }
        }

        if (ok) {
            System.out.println("PASS: Random Arrays Test");
            passed++;
        }
    }

    private static void check(String name, int[] baseArr, int k, int expected) {
        boolean ok = true;
        for (SelectionAlgorithm algo : ALGOS) {
            int actual = algo.select(baseArr.clone(), k);
            if (actual != expected) {
                System.out.println(
                        "FAIL: " + name + " in " + algo.name() + " (Expected " + expected + ", Got " + actual + ")");
                ok = false;
            }
        }
        if (ok) {
            System.out.println("PASS: " + name + " -> " + expected);
            passed++;
        } else {
            failed++;
        }
    }
}
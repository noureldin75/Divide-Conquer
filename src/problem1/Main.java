package problem1;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Random;

public class Main {

    private static final int[] SIZES = {1_000, 10_000, 100_000, 500_000, 1_000_000, 2_500_000, 5_000_000, 10_000_000
    };

    private static final int NUM_RUNS = 10;

    private static final String CSV_FILE_NAME = "benchmark_results.csv";

    public static void main(String[] args) {
        SelectionAlgorithm[] algorithms = {
                new RandomizedSelect(),
                new MedianOfMedians(),
                new SortSelect()
        };

        Random rand = new Random(42);

        warmup(algorithms, rand);

        try (PrintWriter csvWriter = new PrintWriter(new FileWriter(CSV_FILE_NAME))) {

            csvWriter.println("Size (n),Randomized Select (ms),Median of Medians (ms),Sort Select (ms)");

            System.out.printf("%-12s | %-22s | %-22s | %-22s%n",
                    "Size (n)",
                    "Randomized Select (ms)",
                    "Median of Medians (ms)",
                    "Sort Select (ms)");
            System.out.println("-".repeat(87));

            for (int n : SIZES) {
                int k = (n + 1) / 2;
                double[] totalTimesMs = new double[algorithms.length];

                for (int run = 0; run < NUM_RUNS; run++) {
                    int[] baseArray = generateRandomArray(n, rand);
                    int expectedAnswer = -1;

                    for (int i = 0; i < algorithms.length; i++) {
                        int[] arrCopy = baseArray.clone();

                        long startTime = System.nanoTime();
                        int result = algorithms[i].select(arrCopy, k);
                        long endTime = System.nanoTime();

                        totalTimesMs[i] += (endTime - startTime) / 1_000_000.0;

                        if (i == 0) {
                            expectedAnswer = result;
                        } else if (result != expectedAnswer) {
                            throw new IllegalStateException(
                                    String.format("Mismatch at n=%d! %s returned %d, but expected %d",
                                            n, algorithms[i].name(), result, expectedAnswer)
                            );
                        }
                    }
                }

                double avgRand = totalTimesMs[0] / NUM_RUNS;
                double avgMoM  = totalTimesMs[1] / NUM_RUNS;
                double avgSort = totalTimesMs[2] / NUM_RUNS;

                System.out.printf(Locale.US, "%-12d | %-22.3f | %-22.3f | %-22.3f%n",
                        n, avgRand, avgMoM, avgSort);

                csvWriter.printf(Locale.US, "%d,%.3f,%.3f,%.3f%n",
                        n, avgRand, avgMoM, avgSort);
                csvWriter.flush();
            }

            System.out.println("\nBenchmark finished successfully! All outputs verified.");
            System.out.println("Results saved to: " + CSV_FILE_NAME);

        } catch (IOException e) {
            System.err.println("Error writing to CSV file: " + e.getMessage());
        }
    }

    private static int[] generateRandomArray(int n, Random rand) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = rand.nextInt();
        }
        return arr;
    }

    private static void warmup(SelectionAlgorithm[] algorithms, Random rand) {
        int warmupSize = 10_000;
        int k = (warmupSize + 1) / 2;
        for (int r = 0; r < 5; r++) {
            int[] base = generateRandomArray(warmupSize, rand);
            for (SelectionAlgorithm algo : algorithms) {
                algo.select(base.clone(), k);
            }
        }
    }
}
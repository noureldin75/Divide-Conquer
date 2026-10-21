package problem2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner;
        File inputFile = new File("input.txt");

        if (inputFile.exists()) {
            scanner = new Scanner(inputFile);
        } else {
            scanner = new Scanner(System.in);
        }

        MinPrimeter solver = new MinPrimeter();

        while (scanner.hasNextInt()) {
            int n = scanner.nextInt();
            point[] points = new point[n];

            for (int i = 0; i < n; i++) {
                int x = scanner.nextInt();
                int y = scanner.nextInt();
                points[i] = new point(x, y);
            }

            double result = solver.getMinPrimeter(points);
            System.out.printf(Locale.US, "%.3f%n", result);
        }

        scanner.close();
    }
}
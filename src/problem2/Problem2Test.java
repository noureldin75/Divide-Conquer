package problem2;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Problem2Test {

    private static final double EPS = 1e-3;
    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        testSample1();
        testSample2();
        testSample3();
        testSample4();
        testNegativeCoordinates();
        testVerticalPoints();
        testRandomWithBruteForce();
        testLargeInput();

        System.out.println("Passed: " + passed + ", Failed: " + failed);
    }

    private static void testSample1() {
        point[] pts = {
                new point(0, 0),
                new point(1, 0),
                new point(2, 0),
                new point(1, 3)
        };
        check("Sample 1", pts, 4.000);
    }

    private static void testSample2() {
        point[] pts = {
                new point(0, 0),
                new point(3, 0),
                new point(0, 4),
                new point(10, 10),
                new point(11, 10),
                new point(10, 11)
        };
        check("Sample 2", pts, 2.0 + Math.sqrt(2));
    }

    private static void testSample3() {
        point[] pts = {
                new point(0, 0),
                new point(1, 0),
                new point(3, 0)
        };
        check("Sample 3", pts, 6.000);
    }

    private static void testSample4() {
        point[] pts = {
                new point(0, 0),
                new point(6, 0),
                new point(0, 8)
        };
        check("Sample 4", pts, 24.000);
    }

    private static void testNegativeCoordinates() {
        point[] pts = {
                new point(-1000000, -1000000),
                new point(1000000, 1000000),
                new point(-5, -5),
                new point(-2, -5),
                new point(-5, -1)
        };
        check("Negative Coordinates", pts, 12.000);
    }

    private static void testVerticalPoints() {
        point[] pts = {
                new point(5, 0),
                new point(5, 10),
                new point(5, 2),
                new point(5, 3),
                new point(5, 20),
                new point(5, 1)
        };
        check("Vertical Line", pts, 4.000);
    }

    private static void testRandomWithBruteForce() {
        MinPrimeter solver = new MinPrimeter();
        Random rand = new Random(123);
        boolean ok = true;

        for (int t = 0; t < 20; t++) {
            int n = 10 + rand.nextInt(50);
            point[] pts = generatePoints(n, 1000, rand);
            double expected = bruteForce(pts);
            double actual = solver.getMinPrimeter(pts.clone());

            if (Math.abs(expected - actual) > 1e-6) {
                System.out.println("FAIL: Random Test " + t + " (Expected " + expected + ", Got " + actual + ")");
                ok = false;
                failed++;
                break;
            }
        }

        if (ok) {
            System.out.println("PASS: Random Brute Force Comparison");
            passed++;
        }
    }

    private static void testLargeInput() {
        MinPrimeter solver = new MinPrimeter();
        Random rand = new Random(999);
        int n = 100000;
        point[] pts = generatePoints(n, 1000000, rand);

        long start = System.currentTimeMillis();
        double res = solver.getMinPrimeter(pts);
        long time = System.currentTimeMillis() - start;

        if (res > 0 && time < 3000) {
            System.out.println("PASS: Large Input (100k points) in " + time + " ms");
            passed++;
        } else {
            System.out.println("FAIL: Large Input took " + time + " ms");
            failed++;
        }
    }

    private static void check(String name, point[] pts, double expected) {
        MinPrimeter solver = new MinPrimeter();
        double actual = solver.getMinPrimeter(pts);
        if (Math.abs(actual - expected) <= EPS) {
            System.out.println("PASS: " + name + " -> " + String.format("%.3f", actual));
            passed++;
        } else {
            System.out.println("FAIL: " + name + " -> Expected " + expected + ", Got " + actual);
            failed++;
        }
    }

    private static double bruteForce(point[] pts) {
        double min = Double.MAX_VALUE;
        int n = pts.length;
        for (int i = 0; i < n - 2; i++) {
            for (int j = i + 1; j < n - 1; j++) {
                for (int k = j + 1; k < n; k++) {
                    double p = pts[i].distance(pts[j]) + pts[j].distance(pts[k]) + pts[k].distance(pts[i]);
                    if (p < min)
                        min = p;
                }
            }
        }
        return min;
    }

    private static point[] generatePoints(int n, int bound, Random rand) {
        point[] pts = new point[n];
        Set<Long> seen = new HashSet<>();
        int idx = 0;
        while (idx < n) {
            int x = rand.nextInt(2 * bound + 1) - bound;
            int y = rand.nextInt(2 * bound + 1) - bound;
            long key = (((long) x) << 32) ^ (y & 0xffffffffL);
            if (seen.add(key)) {
                pts[idx++] = new point(x, y);
            }
        }
        return pts;
    }
}
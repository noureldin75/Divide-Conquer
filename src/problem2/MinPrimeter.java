package problem2;

import java.util.ArrayList;
import java.util.Arrays;

public class MinPrimeter {

    public double getMinPrimeter(point[] points) {
        int n = points.length;
        if (n < 3) {
            throw new IllegalArgumentException("At least 3 points are required.");
        }

        Arrays.sort(points, (p1, p2) -> {
            if (p1.getX() != p2.getX()) return Integer.compare(p1.getX(), p2.getX());
            return Integer.compare(p1.getY(), p2.getY());
        });

        ArrayList<point> pointsByY = new ArrayList<>(Arrays.asList(points));
        pointsByY.sort((p1, p2) -> Integer.compare(p1.getY(), p2.getY()));

        return divideAndConquer(points, pointsByY, 0, n - 1);
    }

    private double divideAndConquer(point[] pointsByX, ArrayList<point> pointsByY, int left, int right) {
        int n = right - left + 1;
        if (n <= 5) {
            return Min_5_points(pointsByX, left, right);
        }

        int mid = left + (right - left) / 2;
        point midPoint = pointsByX[mid];

        ArrayList<point> leftByY = new ArrayList<>();
        ArrayList<point> rightByY = new ArrayList<>();

        for (point p : pointsByY) {
            if (p.getX() < midPoint.getX() || (p.getX() == midPoint.getX() && p.getY() <= midPoint.getY())) {
                leftByY.add(p);
            } else {
                rightByY.add(p);
            }
        }

        double leftMin = divideAndConquer(pointsByX, leftByY, left, mid);
        double rightMin = divideAndConquer(pointsByX, rightByY, mid + 1, right);
        double minPerimeter = Math.min(leftMin, rightMin);

        // بناء الـ Strip بـ ArrayList
        ArrayList<point> strip = new ArrayList<>();
        for (point p : pointsByY) {
            if (Math.abs(p.getX() - midPoint.getX()) < minPerimeter / 2.0) {
                strip.add(p);
            }
        }

        int count = strip.size();
        for (int i = 0; i < count; i++) {
            for (int j = i + 1; j < count && (strip.get(j).getY() - strip.get(i).getY()) < minPerimeter / 2.0; j++) {
                for (int k = j + 1; k < count && (strip.get(k).getY() - strip.get(i).getY()) < minPerimeter / 2.0; k++) {
                    double perimeter = strip.get(i).distance(strip.get(j))
                            + strip.get(j).distance(strip.get(k))
                            + strip.get(k).distance(strip.get(i));
                    if (perimeter < minPerimeter) {
                        minPerimeter = perimeter;
                    }
                }
            }
        }

        return minPerimeter;
    }

    private double Min_5_points(point[] points, int left, int right) {
        double minPerimeter = Double.MAX_VALUE;
        for (int i = left; i <= right - 2; i++) {
            for (int j = i + 1; j <= right - 1; j++) {
                for (int k = j + 1; k <= right; k++) {
                    double perimeter = points[i].distance(points[j])
                            + points[j].distance(points[k])
                            + points[k].distance(points[i]);
                    if (perimeter < minPerimeter) {
                        minPerimeter = perimeter;
                    }
                }
            }
        }
        return minPerimeter;
    }
}
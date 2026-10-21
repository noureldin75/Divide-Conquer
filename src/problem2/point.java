package problem2;

public class point {
    private int x;
    private int y;

    public point(int x,int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }
    public int getY() {

        return y;
    }

    public double distance(point p) {
        double dx=(double)(this.x - p.getX());
        double dy=(double)(this.y - p.getY());
        return Math.sqrt(dx*dx + dy*dy);
    }
}

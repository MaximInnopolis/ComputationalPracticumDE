import java.util.ArrayList;

public abstract class Solution {
    double x0, y0, X, step;
    int point_number;
    ArrayList<Double> x_list = new ArrayList<>();
    ArrayList<Double> y_list = new ArrayList<>();

    public int getPoint_number() {
        return point_number;
    }

    public ArrayList<Double> getX_list() {
        return x_list;
    }

    public ArrayList<Double> getY_list() {
        return y_list;
    }

    abstract protected void generate();

    public Solution(double x0, double y0, double X, int N) {
        this.x0 = x0;
        this.y0 = y0;
        this.X = X;
        this.step = (X - x0) / N;
        this.point_number = N + 1;
    }
}

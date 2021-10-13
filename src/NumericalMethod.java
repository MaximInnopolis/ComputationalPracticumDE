import java.util.ArrayList;

public abstract class NumericalMethod {
    double x0, y0, X, step;
    int point_number;
    ArrayList<Double> x_list = new ArrayList<Double>();
    ArrayList<Double> y_list = new ArrayList<Double>();
    double Eps = 1e-6;
    public ArrayList<Double> getX_list() {
        return x_list;
    }

    public ArrayList<Double> getY_list() {
        return y_list;
    }

    public int getPoint_number() {
        return point_number;
    }

    abstract protected void generate();

    double myFunction(double x, double y) {
        return x * y - x * Math.pow(y, 3);
    }

    public NumericalMethod(double x0, double y0, double X, int N) throws IllegalArgumentException {
        if((x0 <= 0 && X >= 0) || (x0 >= X) || (N <= 0)) {
            throw new IllegalArgumentException("Arguments are illegal");
        }

        this.x0 = x0;
        this.y0 = y0;
        this.X  = X;
        this.step = (X - x0) / N;
        this.point_number = N + 1;
        generate();

        ExactSolution exactSolution = new ExactSolution(x0, y0, X, N);
    }
}

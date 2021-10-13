import java.util.ArrayList;

public abstract class NumericalMethod {
    double x0, y0, X, step;
    int point_number;
    ArrayList<Double> x_list = new ArrayList<>();
    ArrayList<Double> y_list = new ArrayList<>();
    ArrayList<Double> local_error_list = new ArrayList<>();
    double Eps = 1e-6;

    public int getPoint_number() {
        return point_number;
    }

    public ArrayList<Double> getX_list() {
        return x_list;
    }

    public ArrayList<Double> getY_list() {
        return y_list;
    }

    public ArrayList<Double> getLocal_error_list() {
        return local_error_list;
    }

    abstract protected void generate();

    double myFunction(double x, double y) {
        return x * y - x * Math.pow(y, 3);
    }

    abstract void calculateLocalError(ExactSolution exactSolution);

    public double calculateMaxGlobalError(ExactSolution exactSolution) {
        double max = 0;

        for (int i = 0; i < point_number; ++i) {
            double error = Math.abs(y_list.get(i) - exactSolution.getY_list().get(i));
            max = Math.max(error, max);
        }
        return max;
    }

    public NumericalMethod(double x0, double y0, double X, int N) throws IllegalArgumentException {
        if((x0 >= X) || (x0 <= 0 && X >= 0) || (N <= 0)) {
            throw new IllegalArgumentException("Arguments are illegal");
        }

        this.x0 = x0;
        this.y0 = y0;
        this.X  = X;
        this.step = (X - x0) / N;
        this.point_number = N + 1;
        generate();

        ExactSolution exactSolution = new ExactSolution(x0, y0, X, N);
        calculateLocalError(exactSolution);
        calculateMaxGlobalError(exactSolution);
    }
}

import java.util.ArrayList;

public abstract class NumericalMethod extends Solution{
    ArrayList<Double> local_error_list = new ArrayList<>();

    public ArrayList<Double> getLocal_error_list() {
        return this.local_error_list;
    }

    double myFunction(double x, double y) {
        return x * y - x * Math.pow(y, 3);
    }

    abstract void calculateLocalTruncationError(ExactSolution exactSolution);

    public double calculateGlobalTruncationError(ExactSolution exactSolution) {
//        double max = 0;
//
//        for (int i = 0; i < point_number; ++i) {
//            double error = Math.abs(y_list.get(i) - exactSolution.getY_list().get(i));
//            max = Math.max(error, max);
//        }
//        return max;

    }

    public NumericalMethod(double x0, double y0, double X, int N) {
        super(x0, y0, X, N);
        generate();

        ExactSolution exactSolution = new ExactSolution(x0, y0, X, N);
        calculateLocalTruncationError(exactSolution);
        calculateGlobalTruncationError(exactSolution);
    }
}

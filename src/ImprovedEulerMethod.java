public class ImprovedEulerMethod extends NumericalMethod {
    @Override
    protected void generate() {
        double x = x0;
        x_list.add(x);
        y_list.add(y0);
        x += step;

        for (int i = 1; i < point_number; ++i) {
            x_list.add(x);
            double f = myFunction(x_list.get(i - 1), y_list.get(i - 1));
            y_list.add(y_list.get(i - 1) + step / 2 * (f + myFunction(x_list.get(i), y_list.get(i - 1) + step * f)));
            x += step;
        }
    }

    @Override
    void calculateLocalTruncationError(ExactSolution exactSolution) {
        local_error_list.add(0.0);
        for (int i = 1; i < point_number; ++i) {
            double y_current = exactSolution.getY_list().get(i - 1);
            double f = myFunction(x_list.get(i-1), y_current);
            double y = y_current + step / 2 * (f + myFunction(x_list.get(i), y_current + step * f));

            local_error_list.add(Math.abs(y - exactSolution.getY_list().get(i)));
        }
    }

    public ImprovedEulerMethod(double x0, double y0, double X, int N) {
        super(x0, y0, X, N);
    }
}

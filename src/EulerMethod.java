public class EulerMethod extends NumericalMethod {
    @Override
    protected void generate() {
        double x = x0;
        x_list.add(x);
        y_list.add(y0);
        x += step;

        for (int i = 1; i < point_number; ++i) {
            x_list.add(x);
            y_list.add(y_list.get(i - 1) + step * myFunction(x_list.get(i - 1), y_list.get(i - 1)));
            x += step;
        }
    }

    @Override
    void calculateLocalError(ExactSolution exactSolution) {
        local_error_list.add(0.0);

        for (int i = 1; i < point_number; ++i) {
            double y_current = exactSolution.getY_list().get(i - 1);
            double y = y_current + step * myFunction(x_list.get(i), y_current);

            local_error_list.add(Math.abs(y - exactSolution.getY_list().get(i)));
        }
    }

    public EulerMethod(double x0, double y0, double X, int N) {
        super(x0, y0, X, N);
    }
}

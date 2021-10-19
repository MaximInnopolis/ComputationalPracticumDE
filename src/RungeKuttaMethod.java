public class RungeKuttaMethod extends NumericalMethod {
    @Override
    protected void generate() {
        double x = x0;
        x_list.add(x);
        y_list.add(y0);
        x += step;
        double k1, k2, k3, k4;

        for (int i = 1; i < point_number; ++i) {
            x_list.add(x);
            k1 = myFunction(x_list.get(i - 1), y_list.get(i - 1));
            k2 = myFunction(x_list.get(i - 1) + step / 2, y_list.get(i - 1) + step / 2 * k1);
            k3 = myFunction(x_list.get(i - 1) + step / 2, y_list.get(i - 1) + step / 2 * k2);
            k4 = myFunction(x_list.get(i - 1) + step, y_list.get(i - 1) + step * k3);

            y_list.add(y_list.get(i - 1) + step / 6 * (k1 + 2 * k2 + 2 * k3 + k4));
            x += step;
        }
    }

    @Override
    void calculateLocalTruncationError(ExactSolution exactSolution) {
        double k1, k2, k3, k4;
        local_error_list.add(0.0);

        for (int i = 1; i < point_number; ++i) {
            double y_current = exactSolution.getY_list().get(i - 1);
            k1 = myFunction(x_list.get(i - 1), y_current);
            k2 = myFunction(x_list.get(i - 1) + step / 2, y_current + step / 2 * k1);
            k3 = myFunction(x_list.get(i - 1) + step / 2, y_current + step / 2 * k2);
            k4 = myFunction(x_list.get(i - 1) + step, y_current + step*k3);

            double f = y_list.get(i - 1) + step / 6 * (k1 + 2 * k2 + 2 * k3 + k4);

            local_error_list.add(Math.abs(f - exactSolution.getY_list().get(i)));
        }
    }

    public RungeKuttaMethod(double x0, double y0, double X, int N) {
        super(x0, y0, X, N);
    }
}

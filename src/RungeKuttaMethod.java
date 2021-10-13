public class RungeKuttaMethod extends NumericalMethod {
    @Override
    protected void generate() {
        double x = x0;
        x_list.add(x);
        y_list.add(y0);
        x += step;
        double k1,k2,k3,k4;

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

    public RungeKuttaMethod(double x0, double y0, double X, int N) {
        super(x0, y0, X, N);
    }
}

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

    public ImprovedEulerMethod(double x0, double y0, double X, int N) {
        super(x0, y0, X, N);
    }
}

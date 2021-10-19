public class ExactSolution extends Solution {

    private final double C;

    private double findSolution(double x) {
        return (Math.exp(Math.pow(x, 2) / 2)) / Math.sqrt(this.C + Math.exp(Math.pow(x, 2)));
    }

    @Override
    protected void generate() {
        double x = this.x0;

        for (int i = 0; i < point_number; ++i) {
            x_list.add(x);
            y_list.add(findSolution(x));
            x += this.step;
        }
    }

    public ExactSolution(double x0, double y0, double X, int N) {
        super(x0, y0, X, N);
        this.C = (Math.exp(Math.pow(x0, 2)) / Math.pow(y0, 2)) - Math.exp(Math.pow(x0, 2));
        generate();
    }
}

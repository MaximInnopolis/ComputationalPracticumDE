import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import javafx.scene.control.*;
import javafx.event.EventHandler;
import javafx.scene.layout.FlowPane;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application{

    private static final double x0_default = 0.0;
    private static final double y0_default = 1 / Math.sqrt(2);
    private static final double X_default = 3.0;
    private static final int N_default = 100;

    private static ExactSolution exactSolution = new ExactSolution(x0_default, y0_default, X_default, N_default);
    private static EulerMethod eulerMethod = new EulerMethod(x0_default, y0_default, X_default, N_default);
    private static ImprovedEulerMethod improvedEulerMethod = new ImprovedEulerMethod(x0_default, y0_default, X_default, N_default);
    private static RungeKuttaMethod rungeKuttaMethod = new RungeKuttaMethod(x0_default, y0_default, X_default, N_default);

    @Override
    public void start(Stage stage) {
        stage.setTitle("Differential equations: Computational Practicum");
        TabPane common_tab = new TabPane();
        common_tab.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        HBox tab1data = createFirstTab();
        HBox tab2data = createSecondTab();

        Tab tab1 = new Tab("Solutions and Errors Comparisons", tab1data);
        Tab tab2 = new Tab("Errors Analysis", tab2data);

        common_tab.getTabs().addAll(tab1, tab2);
        Scene scene  = new Scene(common_tab,800,600);
        stage.setScene(scene);
        stage.show();
    }

    private HBox createFirstTab(){
        final NumberAxis x1Axis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Y");
        x1Axis.setLabel("X");
        final LineChart<Number,Number> lineChart = new LineChart<>(x1Axis,yAxis);

        lineChart.getData().add(createSeries("Euler's Method", eulerMethod.getX_list(), eulerMethod.getY_list()));
        lineChart.getData().add(createSeries("Improved Euler's Method", improvedEulerMethod.getX_list(), improvedEulerMethod.getY_list()));
        lineChart.getData().add(createSeries("Runge-Kutta Method", rungeKuttaMethod.getX_list(), rungeKuttaMethod.getY_list()));
        lineChart.getData().add(createSeries("Exact Solution", exactSolution.getX_list(), exactSolution.getY_list()));
        lineChart.setCreateSymbols(false);

        final NumberAxis x2Axis = new NumberAxis();
        final NumberAxis errorAxis = new NumberAxis();
        errorAxis.setLabel("Error");
        x2Axis.setLabel("X");
        final LineChart<Number, Number> errorlineChart = new LineChart<>(x2Axis, errorAxis);

        errorlineChart.getData().add(createSeries("Euler's Method", eulerMethod.getX_list(), eulerMethod.getLocal_error_list()));
        errorlineChart.getData().add(createSeries("Improved Euler's Method", improvedEulerMethod.getX_list(), improvedEulerMethod.getLocal_error_list()));
        errorlineChart.getData().add(createSeries("Runge-Kutta Method", rungeKuttaMethod.getX_list(), rungeKuttaMethod.getLocal_error_list()));
        errorlineChart.setCreateSymbols(true);

        VBox chartBox = new VBox(18, lineChart, errorlineChart);
        VBox.setVgrow(lineChart, Priority.ALWAYS);
        VBox.setVgrow(errorlineChart, Priority.ALWAYS);

        final Label x0_label = new Label("x0:");
        final TextField x0_text_field = new TextField(String.valueOf(x0_default));
        final FlowPane x0_pane = new FlowPane(10, 10, x0_label, x0_text_field);

        final Label y0_label = new Label("y0:");
        final TextField y0_text_field = new TextField(String.valueOf(y0_default));
        final FlowPane y0_pane = new FlowPane(10, 10, y0_label, y0_text_field);

        final Label X_label = new Label("X:");
        final TextField X_text_field = new TextField(String.valueOf(X_default));
        final FlowPane X_pane = new FlowPane(10, 10, X_label, X_text_field);

        final Label N_label = new Label("N:");
        final TextField N_text_field = new TextField(String.valueOf(N_default));
        final FlowPane N_pane = new FlowPane(10, 10, N_label, N_text_field);

        final Label invalid_arguments_label = new Label("Arguments are invalid");
        invalid_arguments_label.setTextFill(Color.RED);
        invalid_arguments_label.setVisible(false);

        Button computeButton = new Button("Compute");

        VBox inputBox = new VBox(18, x0_pane, y0_pane, X_pane, N_pane, computeButton, invalid_arguments_label);
        inputBox.setMaxWidth(150);
        inputBox.setAlignment(Pos.TOP_CENTER);
        VBox.setMargin(x0_pane, new Insets(8, 0, 0, 0));

        final HBox common = new HBox(0, chartBox, inputBox);
        HBox.setHgrow(inputBox, Priority.NEVER);
        HBox.setHgrow(chartBox, Priority.ALWAYS);

        computeButton.setOnAction(actionEvent -> {
            invalid_arguments_label.setVisible(false);
            try {
                double x0 = Double.parseDouble(x0_text_field.getText());
                double y0 = Double.parseDouble(y0_text_field.getText());
                double X = Double.parseDouble(X_text_field.getText());
                int N = Integer.parseInt(N_text_field.getText());

                exactSolution = new ExactSolution(x0, y0, X, N);
                eulerMethod = new EulerMethod(x0, y0, X, N);
                improvedEulerMethod = new ImprovedEulerMethod(x0, y0, X, N);
                rungeKuttaMethod = new RungeKuttaMethod(x0, y0, X, N);
            } catch (IllegalArgumentException e) {
                invalid_arguments_label.setVisible(true);
                return;
            }


            final NumberAxis xAxis = new NumberAxis();
            final NumberAxis yAxis1 = new NumberAxis();
            yAxis1.setLabel("Y");
            xAxis.setLabel("X");
            final LineChart<Number,Number> newGraph = new LineChart<>(xAxis, yAxis1);
            newGraph.setTitle("Comparison of Numerical Methods");

            newGraph.getData().add(createSeries("Euler's Method", eulerMethod.getX_list(), eulerMethod.getY_list()));
            newGraph.getData().add(createSeries("Improved Euler's Method", improvedEulerMethod.getX_list(), improvedEulerMethod.getY_list()));
            newGraph.getData().add(createSeries("Runge-Kutta Method", rungeKuttaMethod.getX_list(), rungeKuttaMethod.getY_list()));
            newGraph.getData().add(createSeries("Exact Solution", exactSolution.getX_list(), exactSolution.getY_list()));
            newGraph.setCreateSymbols(false);


            final NumberAxis errorAxis1 = new NumberAxis();
            final NumberAxis xAxis1 = new NumberAxis();
            errorAxis1.setLabel("Error");
            xAxis1.setLabel("X");
            final LineChart<Number, Number> newErrorGraph = new LineChart<>(xAxis1, errorAxis1);
            newErrorGraph.setTitle("Comparison of LTE");

            newErrorGraph.getData().add(createSeries("Euler's Method", eulerMethod.getX_list(), eulerMethod.getLocal_error_list()));
            newErrorGraph.getData().add(createSeries("Improved Euler's Method", improvedEulerMethod.getX_list(), improvedEulerMethod.getLocal_error_list()));
            newErrorGraph.getData().add(createSeries("Runge-Kutta Metod", rungeKuttaMethod.getX_list(), rungeKuttaMethod.getLocal_error_list()));
            newErrorGraph.setCreateSymbols(true);

            VBox chartBox1 = (VBox) common.getChildren().get(0);
            chartBox1.getChildren().set(0, newGraph);
            chartBox1.getChildren().set(1, newErrorGraph);

            VBox.setVgrow(newGraph, Priority.ALWAYS);
            VBox.setVgrow(newErrorGraph, Priority.ALWAYS);
        });
        common.setAlignment(Pos.CENTER);
        return common;
    }

    private HBox createSecondTab(){
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Error");
        xAxis.setLabel("N");

        final LineChart<Number,Number> errorChart = new LineChart<>(xAxis,yAxis);

        ArrayList<Double> eulerGTE = new ArrayList<>();
        ArrayList<Double> improvedGTE = new ArrayList<>();
        ArrayList<Double> rungeGTE = new ArrayList<>();
        ArrayList<Double> nValues = new ArrayList<>();

        for (int i = 0; i <= N_default; ++i) {
            ExactSolution exactSolution = new ExactSolution(x0_default, y0_default, X_default, i);
            nValues.add((double) i);
            eulerGTE.add(new EulerMethod(x0_default, y0_default, X_default, i).calculateGlobalTruncationError(exactSolution));
            improvedGTE.add(new ImprovedEulerMethod(x0_default, y0_default, X_default, i).calculateGlobalTruncationError(exactSolution));
            rungeGTE.add(new RungeKuttaMethod(x0_default, y0_default, X_default, i).calculateGlobalTruncationError(exactSolution));
        }

        errorChart.getData().add(createSeries("Euler's Method", nValues, eulerGTE));
        errorChart.getData().add(createSeries("Improved Euler's Method", nValues, improvedGTE));
        errorChart.getData().add(createSeries("Runge-Kutta Method", nValues, rungeGTE));

        final Label N_label = new Label("N:");
        final TextField N_text_field = new TextField(String.valueOf(N_default));
        final FlowPane N_pane = new FlowPane(10, 10, N_label, N_text_field);

        final Label invalid_arguments_label = new Label("Arguments are invalid");
        invalid_arguments_label.setTextFill(Color.RED);
        invalid_arguments_label.setVisible(false);

        Button computeButton = new Button("Compute");

        VBox inputBox = new VBox(15, N_pane, computeButton, invalid_arguments_label);
        inputBox.setMaxWidth(180);
        inputBox.setAlignment(Pos.TOP_CENTER);
        VBox.setMargin(N_pane, new Insets(10, 0, 0, 0));

        final HBox common = new HBox(0, errorChart, inputBox);
        HBox.setHgrow(inputBox, Priority.NEVER);
        HBox.setHgrow(errorChart, Priority.ALWAYS);

        computeButton.setOnAction(actionEvent -> {
            invalid_arguments_label.setVisible(false);
            int N;
            try {
                N = Integer.parseInt(N_text_field.getText());
            } catch (IllegalArgumentException e) {
                invalid_arguments_label.setVisible(true);
                return;
            }

            final NumberAxis xAxis1 = new NumberAxis();
            final NumberAxis yAxis1 = new NumberAxis();
            yAxis1.setLabel("Error");
            xAxis1.setLabel("N");

            LineChart<Number,Number> newErrorChart = new LineChart<>(xAxis1, yAxis1);

            ArrayList<Double> eulerGTE1 = new ArrayList<>();
            ArrayList<Double> improvedGTE1 = new ArrayList<>();
            ArrayList<Double> rungeGTE1 = new ArrayList<>();
            ArrayList<Double> nValues1 = new ArrayList<>();

            for (int i = 0; i <= N; ++i) {
                ExactSolution exactSolution = new ExactSolution(x0_default, y0_default, X_default, i);
                nValues1.add((double) i);
                eulerGTE1.add(new EulerMethod(x0_default, y0_default, X_default, i).calculateGlobalTruncationError(exactSolution));
                improvedGTE1.add(new ImprovedEulerMethod(x0_default, y0_default, X_default, i).calculateGlobalTruncationError(exactSolution));
                rungeGTE1.add(new RungeKuttaMethod(x0_default, y0_default, X_default, i).calculateGlobalTruncationError(exactSolution));
            }

            newErrorChart.getData().add(createSeries("Euler's Method", nValues1, eulerGTE1));
            newErrorChart.getData().add(createSeries("Improved Euler's Method", nValues1, improvedGTE1));
            newErrorChart.getData().add(createSeries("Runge-Kutta Method", nValues1, rungeGTE1));


            common.getChildren().set(0, newErrorChart);
            HBox.setHgrow(newErrorChart, Priority.ALWAYS);
        });

        errorChart.setTitle("Global Truncation Error");
        common.setAlignment(Pos.CENTER);
        return common;
    }


    private static XYChart.Series<Number, Number> createSeries(String name, ArrayList<Double> x_data, ArrayList<Double> y_data) {
        XYChart.Series series = new XYChart.Series();
        series.setName(name);

        for (int i = 0; i < x_data.size(); ++i) {
            series.getData().add(new XYChart.Data<>(x_data.get(i), y_data.get(i)));
        }
        return series;
    }

    public static void main(String[] args){
        launch(args);
    }
}
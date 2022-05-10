package InterfazGrafica.vista;

import InterfazGrafica.controlador.Controlador;
import InterfazGrafica.controlador.ImplementacionControlador;
import InterfazGrafica.modelo.ImplementacionModelo;
import InterfazGrafica.modelo.InterrogaModelo;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ImplementacionVista implements InformaVista, InterrogaVista {

    private final Stage stage;
    private Controlador controlador;
    private InterrogaModelo modelo;
    private TextField tfNombre;
    private Label lContador;
    public ImplementacionVista(final Stage stage) {
        this.stage = stage;
    }

    public void setModelo(final InterrogaModelo modelo) {
        this.modelo = modelo;
    }

    public void setControlador(final Controlador controlador) {
        this.controlador = controlador;
    }

    public void creaGUI() {
        // Diseña la gui

        stage.setTitle("Hello World!");
        stage.setMinHeight(400);
        stage.setMinWidth(600);

        VBox root = new VBox();

        // * FIRST LINE
        HBox firstLine = new HBox();
        firstLine.setAlignment(Pos.CENTER_LEFT);

        // + 1 selection
        ChoiceBox selectYAxisChBox = new ChoiceBox();

        // + 2 chart
            NumberAxis xAxis = new NumberAxis();
            xAxis.setLabel("X");

            NumberAxis yAxis = new NumberAxis();
            yAxis.setLabel("Y");

        ScatterChart scatterChart = new ScatterChart(xAxis, yAxis);
        scatterChart.legendVisibleProperty().setValue(false);
        scatterChart.setMinSize(250, 250);
        scatterChart.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

            XYChart.Series series = new XYChart.Series();
            scatterChart.getData().add(series);

        // + 3 vbox
        VBox controlButtons = new VBox();
        controlButtons.setAlignment(Pos.CENTER_LEFT);
        controlButtons.setMinWidth(100);
        // - openFile BTN
        Button openFileBtn = new Button("Open File");
        openFileBtn.setOnAction(actionEvent -> controlador.openFile());
        // - tipoDistancia CHBOX
        ChoiceBox tipoDistanciaChbox = new ChoiceBox();
        tipoDistanciaChbox.setDisable(true);
        // - añadePunto TXT
        TextField anadePuntoTxt = new TextField("New Point");
        // - estimate BTN
        Button estimateBtn = new Button("Estimate");
        estimateBtn.setOnAction(actionEvent -> controlador.openFile());
        // - ADD TO VBOX
        controlButtons.getChildren().addAll(openFileBtn, tipoDistanciaChbox, anadePuntoTxt, estimateBtn);

        // * SECOND LINE
        HBox secondLine = new HBox();
        secondLine.setAlignment(Pos.TOP_CENTER);

        // + 1
        ChoiceBox selectXAxisChBox = new ChoiceBox();


        // * GROW MODIFIERS
        // + vbox for lines
        VBox.setVgrow(firstLine, Priority.ALWAYS);
        // + hbox for components
        HBox.setHgrow(scatterChart, Priority.ALWAYS);
        HBox.setHgrow(openFileBtn, Priority.NEVER);
        HBox.setHgrow(selectYAxisChBox, Priority.NEVER);


        // * ADD EVERYTHING
        // + 1 line
        firstLine.getChildren().addAll(selectYAxisChBox, scatterChart, controlButtons);
        // + 2 line
        secondLine.getChildren().addAll(selectXAxisChBox);
        // + final
        root.getChildren().addAll(firstLine, secondLine);

        stage.setScene(new Scene(root, 400, 400));
        stage.show();
    }
}

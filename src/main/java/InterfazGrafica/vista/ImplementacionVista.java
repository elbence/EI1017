package InterfazGrafica.vista;

import Estructura.RowWithLabels;
import Estructura.TableWithLabels;
import InterfazGrafica.controlador.Controlador;
import InterfazGrafica.modelo.InterrogaModelo;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class ImplementacionVista implements InformaVista, InterrogaVista {

    // * MAIN VARS
    private final Stage stage;
    private Controlador controlador;
    private InterrogaModelo modelo;

    // * OTHER VARS
    private ChoiceBox selectYAxisChBox;
    private ScatterChart scatterChart;
    private NumberAxis yAxis;
    private NumberAxis xAxis;
    private List<String> cabeceras;
    private ChoiceBox tipoDistanciaChbox;
    private TextField estimacionPuntoTxt;
    private ChoiceBox selectXAxisChBox;
    private TextField anadePuntoTxt;

    private int selectedY;
    private int selectedX;

    private String estimadaAnterior;


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
        // will contain 1 entry per type of
        selectYAxisChBox = new ChoiceBox();
        selectYAxisChBox.setMinWidth(80);
        selectYAxisChBox.getSelectionModel().selectedIndexProperty().addListener((item, valorInicial, valorActual) -> {
            selectedY = valorActual.intValue();
            redrawPoints();
        } );

        // + 2 chart
            xAxis = new NumberAxis();
            xAxis.setLabel("X");

            yAxis = new NumberAxis();
            yAxis.setLabel("Y");

        scatterChart = new ScatterChart(xAxis, yAxis);
        scatterChart.legendVisibleProperty().setValue(false);
        scatterChart.setMinSize(250, 250);
        scatterChart.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        // + 3 vbox
        VBox controlButtons = new VBox();
        controlButtons.setAlignment(Pos.CENTER_LEFT);
        controlButtons.setMinWidth(100);

        // - (1) openFile BTN
        Button openFileBtn = new Button("Open File");
        openFileBtn.setOnAction(actionEvent -> controlador.openFile());

        // - (2) tipoDistancia CHBOX
        tipoDistanciaChbox = new ChoiceBox();
        tipoDistanciaChbox.getItems().addAll("EUCLIDEAN", "MANHATTAN");
        tipoDistanciaChbox.setValue("EUCLIDEAN");
        tipoDistanciaChbox.setDisable(true);
        tipoDistanciaChbox.getSelectionModel().selectedIndexProperty().addListener((item, valorInicial, valorActual) -> {
            controlador.actualizaDistancia(valorInicial, valorActual);
        } );

        // - (3) añadePunto TXT
        anadePuntoTxt = new TextField("New Point");

        // - (4) estimacionPunto TXT
        estimacionPuntoTxt = new TextField("None");
        estimacionPuntoTxt.setDisable(true);

        // - (5) estimate BTN
        Button estimateBtn = new Button("Estimate");
        estimateBtn.setOnAction(actionEvent -> controlador.estimateValue());

        // - ADD TO VBOX
        controlButtons.getChildren().addAll(openFileBtn, tipoDistanciaChbox, anadePuntoTxt, estimacionPuntoTxt, estimateBtn);

        // * SECOND LINE
        HBox secondLine = new HBox();
        secondLine.setAlignment(Pos.TOP_CENTER);

        // + 1
        selectXAxisChBox = new ChoiceBox();
        selectXAxisChBox.getSelectionModel().selectedIndexProperty().addListener((item, valorInicial, valorActual) -> {
            selectedX = valorActual.intValue();
            redrawPoints();
        } );


        // * GROW MODIFIERS
        // + vbox for lines
        VBox.setVgrow(firstLine, Priority.ALWAYS);
        // + hbox for components
        HBox.setHgrow(scatterChart, Priority.ALWAYS);

        // * ADD EVERYTHING
        // + 1 line
        firstLine.getChildren().addAll(selectYAxisChBox, scatterChart, controlButtons);
        // + 2 line
        secondLine.getChildren().addAll(selectXAxisChBox);
        // + final
        root.getChildren().addAll(firstLine, secondLine);

        stage.setScene(new Scene(root, 400, 400));
        stage.show();

        // ! COMUNICATION METHODS
    }


    @Override
    public void nuevoDocumento() {
        if (tipoDistanciaChbox.isDisabled()) tipoDistanciaChbox.setDisable(false);
        cabeceras = modelo.getHeaders();
        cabeceras.remove(cabeceras.size()-1);

        selectYAxisChBox.getItems().addAll(cabeceras);
        selectYAxisChBox.setValue(cabeceras.get(0));

        selectXAxisChBox.getItems().addAll(cabeceras);
        selectXAxisChBox.setValue(cabeceras.get(1));

        inicializaScatterChartValues();
    }

    private void inicializaScatterChartValues() {
        selectedY = 0;
        selectedX = 1;
        redrawPoints();
    }

    private void redrawPoints() {

        yAxis.setLabel(cabeceras.get(selectedY));
        xAxis.setLabel(cabeceras.get(selectedX));

        scatterChart.getData().removeAll(scatterChart.getData());
        // dependiente de los tipos de series que hay en el csv
        TableWithLabels table = modelo.getDataTable();
        List<String> tipos = modelo.getTypes();

        List<XYChart.Series> conjuntoSeries = new ArrayList<>();
        for (int i = 0; i < tipos.size(); i++) conjuntoSeries.add(new XYChart.Series());

        for (int i = 0; i < table.size(); i++) {
            RowWithLabels rowAct = table.getRowAt(i);
            conjuntoSeries.get(tipos.indexOf(rowAct.getLabel())).getData().add(new XYChart.Data(rowAct.get(selectedX),rowAct.get(selectedY)));
        }

        for (int i = 0; i < tipos.size(); i++) scatterChart.getData().add(conjuntoSeries.get(i));
    }

    @Override
    public String getNewPoint() {
        return anadePuntoTxt.getText();
    }
}

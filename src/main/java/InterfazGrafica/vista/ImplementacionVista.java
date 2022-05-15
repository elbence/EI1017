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
import javafx.scene.control.*;
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

    // * DESIGN VARS
    private ChoiceBox selectYAxisChBox;
    private ChoiceBox selectXAxisChBox;
    private ScatterChart scatterChart;
    private NumberAxis yAxis;
    private NumberAxis xAxis;
    private ChoiceBox tipoDistanciaChbox;
    private TextField anadePuntoTxt;
    private TextField estimacionPuntoTxt;

    // * DATA VARS
    private List<String> cabeceras;
    private int selectedX;
    private int selectedY;
    private String tipoEstimado;
    private RowWithLabels rowEstimada;


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


        stage.setTitle("");
        stage.setMinHeight(400);
        stage.setMinWidth(600);

        TabPane rootHolder = new TabPane();
        VBox root = new VBox();

        // * FIRST LINE
        HBox firstLine = new HBox();
        firstLine.setAlignment(Pos.CENTER_LEFT);

        // + 1 selection
        selectYAxisChBox = new ChoiceBox();
        selectYAxisChBox.setMinWidth(80);
        selectYAxisChBox.getSelectionModel().selectedIndexProperty().addListener((item, valorInicial, valorActual) -> {
            selectedY = valorActual.intValue();
            redrawPoints();
            System.out.println("(vist) Y Point selection called, index value: " + valorActual);
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
            System.out.println("(vist) Distance type changed from " + valorInicial + " to " + valorActual);
        } );

        // - (3) añadePunto TXT
        anadePuntoTxt = new TextField("New Point");

        // - (4) estimacionPunto TXT
        estimacionPuntoTxt = new TextField("None");
        estimacionPuntoTxt.setDisable(true);

        // - (5) estimate BTN
        Button estimateBtn = new Button("Estimate");
        estimateBtn.setOnAction(actionEvent -> controlador.notificaNuevoValorEstimate());

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
            System.out.println("(vist) X Point selection called, index value: " + valorActual);
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

        Tab knnTab = new Tab("KNN");
        knnTab.setContent(root);
        //knnTab.setClosable(false);
        rootHolder.getTabs().add(knnTab);

        stage.setScene(new Scene(rootHolder, 400, 400));
        stage.show();

        // ! COMUNICATION METHODS
    }


    @Override
    public void nuevoDocumento() {
        if (tipoDistanciaChbox.isDisabled()) tipoDistanciaChbox.setDisable(false);
        cabeceras = modelo.getHeaders();
        cabeceras.remove(cabeceras.size()-1);

        selectYAxisChBox.getItems().removeAll(selectYAxisChBox.getItems());
        selectYAxisChBox.getItems().addAll(cabeceras);
        selectYAxisChBox.setValue(cabeceras.get(0));

        selectXAxisChBox.getItems().removeAll(selectXAxisChBox.getItems());
        selectXAxisChBox.getItems().addAll(cabeceras);
        selectXAxisChBox.setValue(cabeceras.get(1));

        inicializaScatterChartValues();
    }

    @Override
    public void nuevaEstimacion() {
        tipoEstimado = modelo.getTipoEstimado();
        estimacionPuntoTxt.setText(tipoEstimado);
        rowEstimada = modelo.getRowEstimada();
        redrawPoints();
    }

    private void inicializaScatterChartValues() {
        selectedY = 0;
        selectedX = 1;
        redrawPoints();
    }

    private void redrawPoints() {

        System.out.print("(vist) Dibujando puntos");
        if (tipoEstimado != null) System.out.print(", estimado considerado");
        System.out.println();

        if (selectedX >= cabeceras.size() || selectedY >= cabeceras.size() || selectedY < 0 || selectedX < 0) {
            return;
        }
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

        if (tipoEstimado != null) {
            XYChart.Series tmp = new XYChart.Series<>();
            tmp.getData().add(new XYChart.Data(rowEstimada.get(selectedX), rowEstimada.get(selectedY)));
            scatterChart.getData().add(tmp);
        }
    }

    @Override
    public String getNuevoValorEstimate() {
        return anadePuntoTxt.getText();
    }
}

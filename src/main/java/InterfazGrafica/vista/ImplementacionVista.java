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
    private boolean openingDocument; // flow controller, so elements dont go mad while opening || closing


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
            if (!openingDocument) {
                selectedY = valorActual.intValue();
                System.out.println("(vist) Y Point selection called, index value: " + valorActual);
                manageScatterChart();
            }
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
            // should not execute when closing file -> select = -1
            if (!openingDocument) {
                selectedX = valorActual.intValue();
                System.out.println("(vist) X Point selection called, index value: " + valorActual);
                manageScatterChart();
            }
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
        openingDocument = true;
        if (tipoDistanciaChbox.isDisabled()) tipoDistanciaChbox.setDisable(false);
        inicializaValoresPorDefecto();
        inicializaCabeceras();
        inicializaYAxisChBox();
        inicializaXAxisChBox();
        manageScatterChart();
        openingDocument = false;
    }

    private void inicializaValoresPorDefecto() {
        tipoEstimado = null;
        rowEstimada = null;
        estimacionPuntoTxt.setText("None");
    }

    @Override
    public void nuevaEstimacion() {
        tipoEstimado = modelo.getTipoEstimado();
        rowEstimada = modelo.getRowEstimada();
        estimacionPuntoTxt.setText(tipoEstimado);
        manageScatterChart();
    }

    @Override
    public String getNuevoValorEstimate() {
        return anadePuntoTxt.getText();
    }

    private void inicializaCabeceras() {
        cabeceras = modelo.getHeaders();
        if (!cabeceras.isEmpty()) cabeceras.remove(cabeceras.size()-1);
    }

    private void inicializaYAxisChBox() {
        selectYAxisChBox.getItems().removeAll(selectYAxisChBox.getItems());
        selectYAxisChBox.getItems().addAll(cabeceras);
        selectedY = 0;
        if (!cabeceras.isEmpty()) selectYAxisChBox.setValue(cabeceras.get(selectedY));
    }

    private void inicializaXAxisChBox() {
        selectXAxisChBox.getItems().removeAll(selectXAxisChBox.getItems());
        selectXAxisChBox.getItems().addAll(cabeceras);
        if (cabeceras.size() > 1) selectedX = 1;
        else selectedX = 0;
        if (!cabeceras.isEmpty()) selectXAxisChBox.setValue(cabeceras.get(selectedX));
    }

    private void manageScatterChart() {
        System.out.print("(vist) Dibujando puntos");

        setScatterChartNames();
        setScatterChartData();
        if (tipoEstimado != null) setEstimatedPoint();
        scatterChart.autosize(); // if not size may bug when changing data

        System.out.println("\n");
    }

    private void setScatterChartNames() {
        if (!cabeceras.isEmpty()) {
            yAxis.setLabel(cabeceras.get(selectedY));
            xAxis.setLabel(cabeceras.get(selectedX));
            scatterChart.setTitle(cabeceras.get(selectedY) + " vs " + cabeceras.get(selectedX));
        }
    }

    private void setScatterChartData() {
        scatterChart.getData().clear();
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

    private void setEstimatedPoint() {
        System.out.print(", estimado considerado");
        XYChart.Series tmp = new XYChart.Series<>();
        tmp.getData().add(new XYChart.Data(rowEstimada.get(selectedX), rowEstimada.get(selectedY)));
        scatterChart.getData().add(tmp);
    }
}

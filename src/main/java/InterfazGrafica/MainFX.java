package InterfazGrafica;

import InterfazGrafica.controlador.Controlador;
import InterfazGrafica.controlador.ImplementacionControlador;
import InterfazGrafica.modelo.CambioModelo;
import InterfazGrafica.modelo.ImplementacionModelo;
import InterfazGrafica.vista.ImplementacionVista;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainFX extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        ImplementacionControlador controlador = new ImplementacionControlador();
        ImplementacionModelo modelo = new ImplementacionModelo();
        ImplementacionVista vista = new ImplementacionVista(stage);
        modelo.setVista(vista);
        controlador.setVista(vista);
        controlador.setModelo(modelo);
        vista.setModelo(modelo);
        vista.setControlador(controlador);
        vista.creaGUI();

    }
}

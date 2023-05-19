package interfaz_grafica;

import interfaz_grafica.controlador.ImplementacionControlador;
import interfaz_grafica.modelo.ImplementacionModelo;
import interfaz_grafica.vista.ImplementacionVista;
import javafx.application.Application;
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

package main;

import com.google.inject.Guice;
import controllers.InditoController;
import controllers.SzamlaController;
import guice.PersistenceModule;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.ugyfel.UgyfelDao;

public class KonyveloApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/indito.fxml"));
        primaryStage.setTitle("Könyvelő Program");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
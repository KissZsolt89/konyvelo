package main;

import controllers.InditoController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class KonyveloApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/indito.fxml"));
        Parent root = fxmlLoader.load();
        fxmlLoader.<InditoController>getController().initdata();
        primaryStage.setTitle("Könyvelő Program");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import java.io.IOException;

public class InditoController {

    @FXML
    private ChoiceBox ugyfelChoiceBox;

    public void initdata() {
        ObservableList<String> ugyfelLista = FXCollections.observableArrayList("ABC", "BCD","CDE");
        ugyfelChoiceBox.setItems(ugyfelLista);
        this.ugyfelChoiceBox.setValue(ugyfelLista.get(0));
    }

    public void inditasAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/szamla.fxml"));
        Parent root = fxmlLoader.load();
        fxmlLoader.<SzamlaController>getController().initdata(ugyfelChoiceBox.getValue().toString());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void ugyfelkezelesAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/ugyfelek.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}

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
import model.szamla.UgyfelSzamlaDao;
import model.ugyfel.UgyfelDao;

import java.io.IOException;
import java.util.List;

public class InditoController {

    @FXML
    private ChoiceBox ugyfelChoiceBox;

    private UgyfelDao ugyfelDao;
    private UgyfelSzamlaDao ugyfelSzamlaDao;

    @FXML
    public void initialize() {

        ugyfelDao = UgyfelDao.getInstance();
        ugyfelSzamlaDao = UgyfelSzamlaDao.getInstance();

        List<String> ugyfelLista = ugyfelDao.findAllNev();

        if (ugyfelLista.size() > 0) {
            ObservableList<String> observableUgyfelLista = FXCollections.observableArrayList();
            observableUgyfelLista.addAll(ugyfelLista);

            ugyfelChoiceBox.setItems(observableUgyfelLista);
            ugyfelChoiceBox.setValue(observableUgyfelLista.get(0));
        }
    }

    public void inditasAction(ActionEvent actionEvent) throws IOException {
        if (ugyfelChoiceBox.getValue() != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/szamla.fxml"));
            Parent root = fxmlLoader.load();
            fxmlLoader.<SzamlaController>getController().initdata(ugyfelChoiceBox.getValue().toString());
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    public void ugyfelkezelesAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ugyfelek.fxml"));
        Parent root = fxmlLoader.load();
        fxmlLoader.<UgyfelekController>getController().initdata("indito");
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}

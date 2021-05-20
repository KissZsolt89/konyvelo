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
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class SzamlaController {

    @FXML
    private Label ugyfelLabel;

    @FXML
    private ChoiceBox afaChoiceBox;

    public void initdata(String ugyfelnev) {
        this.ugyfelLabel.setText(ugyfelnev);

        ObservableList<String> afaLista =
                FXCollections.observableArrayList("27%", "12%","5%", "AM");
        this.afaChoiceBox.setItems(afaLista);
        this.afaChoiceBox.setValue(afaLista.get(0));
    }

    public void megseAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/indito.fxml"));
        Parent root = fxmlLoader.load();
        fxmlLoader.<InditoController>getController().initdata();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void mentesAction(ActionEvent actionEvent) {
    }
}

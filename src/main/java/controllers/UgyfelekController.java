package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.ugyfel.Ugyfel;
import model.ugyfel.UgyfelDao;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class UgyfelekController {

    @FXML
    private TableView<Ugyfel> ugyfelekTable;

    @FXML
    private TableColumn<Ugyfel, String> nev;

    @FXML
    private TableColumn<Ugyfel, String> adoszam;

    @FXML
    private TableColumn<Ugyfel, String> cim;

    private UgyfelDao ugyfelDao;

    @FXML
    public void initialize() {
        ugyfelDao = UgyfelDao.getInstance();
        initializeTable();
    }

    @FXML
    private void initializeTable() {
        List<Ugyfel> ugyfelLista = ugyfelDao.findAll();

        if (!ugyfelLista.isEmpty()) {
            nev.setCellValueFactory(new PropertyValueFactory<>("nev"));
            adoszam.setCellValueFactory(new PropertyValueFactory<>("adoszam"));
            cim.setCellValueFactory(new PropertyValueFactory<>("cim"));

            ObservableList<Ugyfel> observableUgyfelLista = FXCollections.observableArrayList();
            observableUgyfelLista.addAll(ugyfelLista);

            ugyfelekTable.setItems(observableUgyfelLista);
        }
        else {
            ugyfelekTable.getItems().clear();
        }
    }

    public void megseAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/indito.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void ujAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/ugyfel.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void modositasAction(ActionEvent actionEvent) throws IOException {
        if (!ugyfelekTable.getSelectionModel().isEmpty()) {
            Optional<Ugyfel> optionalUgyfel =
                    ugyfelDao.findByNev(ugyfelekTable.getSelectionModel().getSelectedItem().getNev());

            if (!optionalUgyfel.isEmpty()) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ugyfel.fxml"));
                Parent root = fxmlLoader.load();
                fxmlLoader.<UgyfelController>getController().initdata(optionalUgyfel.get());
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            }
        }
    }

    public void torlesAction(ActionEvent actionEvent) {
        if (!ugyfelekTable.getSelectionModel().isEmpty()) {
            Optional<Ugyfel> optionalUgyfel =
                    ugyfelDao.findByNev(ugyfelekTable.getSelectionModel().getSelectedItem().getNev());

            if (!optionalUgyfel.isEmpty()) {
                Ugyfel torlendoUgyfel = optionalUgyfel.get();
                ugyfelDao.remove(optionalUgyfel.get());
                initializeTable();
            }
        }
    }
}

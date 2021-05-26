package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.szamla.UgyfelSzamla;
import model.szamla.UgyfelSzamlaDao;
import model.ugyfel.Ugyfel;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class SzamlakController {

    private static Ugyfel aktualisUgyfel;

    private UgyfelSzamlaDao ugyfelSzamlaDao;

    @FXML
    private TableView<UgyfelSzamla> szamlakTable;

    @FXML
    private TableColumn<UgyfelSzamla, Boolean> irany;

    @FXML
    private TableColumn<UgyfelSzamla, String> bizonylatszam;

    @FXML
    private TableColumn<UgyfelSzamla, String> partner;

    @FXML
    private TableColumn<UgyfelSzamla, String> kelte;

    @FXML
    private TableColumn<UgyfelSzamla, String> teljesites;

    @FXML
    private TableColumn<UgyfelSzamla, String> esedekesseg;

    @FXML
    private TableColumn<UgyfelSzamla, String> fizetesiMod;

    @FXML
    private TableColumn<UgyfelSzamla, Integer> netto;

    @FXML
    private TableColumn<UgyfelSzamla, Integer> afa;

    @FXML
    private TableColumn<UgyfelSzamla, Integer> brutto;

    @FXML
    private Label ugyfelLabel;

    @FXML
    public void initialize() {
        ugyfelSzamlaDao = UgyfelSzamlaDao.getInstance();
        initializeTable();
    }

    public void initdata(Ugyfel ugyfel) {
        aktualisUgyfel = ugyfel;
        initializeTable();
    }

    @FXML
    private void initializeTable() {
        if (aktualisUgyfel != null) {
            ugyfelLabel.setText(aktualisUgyfel.getNev());

            List<UgyfelSzamla> szamlaLista = ugyfelSzamlaDao.findAllByNev(aktualisUgyfel.getNev());

            if (!szamlaLista.isEmpty()) {
                irany.setCellValueFactory(new PropertyValueFactory<>("irany"));
                bizonylatszam.setCellValueFactory(new PropertyValueFactory<>("bizonylatszam"));
                partner.setCellValueFactory(new PropertyValueFactory<>("partner"));
                kelte.setCellValueFactory(new PropertyValueFactory<>("kelte"));
                teljesites.setCellValueFactory(new PropertyValueFactory<>("teljesites"));
                esedekesseg.setCellValueFactory(new PropertyValueFactory<>("esedekesseg"));
                fizetesiMod.setCellValueFactory(new PropertyValueFactory<>("fizetesiMod"));
                netto.setCellValueFactory(new PropertyValueFactory<>("netto"));
                afa.setCellValueFactory(new PropertyValueFactory<>("afa"));
                brutto.setCellValueFactory(new PropertyValueFactory<>("brutto"));

                ObservableList<UgyfelSzamla> observableUgyfelSzamlaLista = FXCollections.observableArrayList();
                observableUgyfelSzamlaLista.addAll(szamlaLista);

                szamlakTable.setItems(observableUgyfelSzamlaLista);
            } else {
                szamlakTable.getItems().clear();
            }
        }
    }

    public void modositasAction(ActionEvent actionEvent) throws IOException {
        if (!szamlakTable.getSelectionModel().isEmpty()) {
            Optional<UgyfelSzamla> optionalUgyfelSzamla =
                    ugyfelSzamlaDao.findByID(szamlakTable.getSelectionModel().getSelectedItem().getId());

            if (!optionalUgyfelSzamla.isEmpty()) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/szamla.fxml"));
                Parent root = fxmlLoader.load();
                fxmlLoader.<SzamlaController>getController().initdata(optionalUgyfelSzamla.get());
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            }
        }
    }

    public void ujSzamlakAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/szamla.fxml"));
        Parent root = fxmlLoader.load();
        fxmlLoader.<SzamlaController>getController().initdata(aktualisUgyfel);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void torlesAction(ActionEvent actionEvent) {
        if (!szamlakTable.getSelectionModel().isEmpty()) {
            Optional<UgyfelSzamla> optionalUgyfelSzamla =
                    ugyfelSzamlaDao.findByID(szamlakTable.getSelectionModel().getSelectedItem().getId());

            if (!optionalUgyfelSzamla.isEmpty()) {
                ugyfelSzamlaDao.remove(optionalUgyfelSzamla.get());
                initializeTable();
            }
        }
    }

    public void megseAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/indito.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void fokonyvAction(ActionEvent actionEvent) {
    }

    public void afaAnalitikaAction(ActionEvent actionEvent) {
    }

    public void vevoAnalitikaAction(ActionEvent actionEvent) {
    }

    public void szallitoAnalitikaAction(ActionEvent actionEvent) {
    }
}

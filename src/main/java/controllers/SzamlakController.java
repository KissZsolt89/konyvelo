package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import model.szamla.UgyfelSzamla;
import model.szamla.UgyfelSzamlaDao;
import model.ugyfel.Ugyfel;
import model.ugyfel.UgyfelDao;

import java.util.List;

public class SzamlakController {

    @FXML
    private TableColumn<UgyfelSzamla, String> id;

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

    private UgyfelDao ugyfelDao;
    private UgyfelSzamlaDao ugyfelSzamlaDao;

    @FXML
    public void initialize() {
        ugyfelDao = UgyfelDao.getInstance();
        ugyfelSzamlaDao = UgyfelSzamlaDao.getInstance();
        //initializeTable();
    }
    /*
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
*/
    public void modositasAction(ActionEvent actionEvent) {
    }

    public void torlesAction(ActionEvent actionEvent) {
    }

    public void megseAction(ActionEvent actionEvent) {
    }
}

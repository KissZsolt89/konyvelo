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
import model.szamla.UgyfelSzamlaDao;
import model.tetel.Tetel;
import model.ugyfel.Ugyfel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KivonatController {

    private UgyfelSzamlaDao ugyfelSzamlaDao;

    @FXML
    private Label ugyfelLabel;

    @FXML
    private Label egyenlegLabel;

    @FXML
    private TableView<Tetel> kivonatTable;

    @FXML
    private TableColumn<Tetel, String> tetelNev;

    @FXML
    private TableColumn<Tetel, Integer> bevetel;

    @FXML
    private TableColumn<Tetel, Integer> kiadas;

    @FXML
    private TableColumn<Tetel, Integer> egyenleg;


    @FXML
    public void initialize() {
        ugyfelSzamlaDao = UgyfelSzamlaDao.getInstance();

        tetelNev.setCellValueFactory(new PropertyValueFactory<>("tetelNev"));
        bevetel.setCellValueFactory(new PropertyValueFactory<>("bevetel"));
        kiadas.setCellValueFactory(new PropertyValueFactory<>("kiadas"));
        egyenleg.setCellValueFactory(new PropertyValueFactory<>("egyenleg"));
    }

    public void initdata(String text, Ugyfel ugyfel) {

        ugyfelLabel.setText(ugyfel.getNev());

        List<Tetel> lista;

        switch (text) {
            case "ÁFA-analitika" :
                lista = ugyfelSzamlaDao.findAllByNev_afaAnalitika(ugyfel.getNev());
                break;
            case "Vevő-szállító-analitika" :
                lista = ugyfelSzamlaDao.findAllByNev_vevoSzallitoAnalitika(ugyfel.getNev());
                break;
            default :
                lista = ugyfelSzamlaDao.findAllByNev_fokonyviSzam(ugyfel.getNev());
        }

        ObservableList<Tetel> observableTetelLista = FXCollections.observableArrayList();
        observableTetelLista.addAll(lista);

        kivonatTable.setItems(observableTetelLista);

        egyenlegLabel.setText(egyenlegSzamolo(lista));
    }

    public void visszaAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/szamlak.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public String egyenlegSzamolo(List<Tetel> lista) {
        List<Integer> bevetelLista = new ArrayList<>();
        List<Integer> kiadasLista = new ArrayList<>();
        List<Integer> egyenlegLista = new ArrayList<>();

        lista.stream().forEach(s -> {bevetelLista.add(s.getBevetel());
            kiadasLista.add(s.getKiadas());
            egyenlegLista.add(s.getEgyenleg()); });

        return "Összes bevétel: " + listaOsszeado(bevetelLista)
                + "   Összes kiadás: " + listaOsszeado(kiadasLista)
                + "   Egyenleg: " + listaOsszeado(egyenlegLista);
    }

    public int listaOsszeado (List<Integer> lista) {
        return lista.stream().reduce(0, Integer::sum);
    }
}

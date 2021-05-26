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

        switch (text) {
            case "Főkönyvi kivonat" : fokonyviKivonat(ugyfel);
            case "ÁFA-analitika" : afaAnalitika(ugyfel);
            case "Vevő-analitika" : vevoAnalitika(ugyfel);
            case "Szállító-analitika" : szallitoAnalitika(ugyfel);
        }
    }

    private void fokonyviKivonat(Ugyfel ugyfel) {
        ugyfelSzamlaDao.findByNev_fokonyviSzam(ugyfel.getNev());


        List<Tetel> tetelLista = ugyfelSzamlaDao.findByNev_fokonyviSzam(ugyfel.getNev());

        for (int i = 1; i < tetelLista.size(); i++) {

            if (tetelLista.get(i).getTetelNev()
                    .equals(tetelLista.get(i - 1).getTetelNev())) {

                tetelLista.get(i - 1).setBevetel(tetelLista.get(i - 1).getBevetel()
                        + tetelLista.get(i).getBevetel());

                tetelLista.get(i - 1).setKiadas(tetelLista.get(i - 1).getKiadas()
                        + tetelLista.get(i).getKiadas());

                tetelLista.get(i - 1).setEgyenleg(tetelLista.get(i - 1).getBevetel()
                        - tetelLista.get(i - 1).getKiadas());

                tetelLista.remove(i);
            }
        }

        ObservableList<Tetel> observableTetelLista = FXCollections.observableArrayList();
        observableTetelLista.addAll(tetelLista);

        kivonatTable.setItems(observableTetelLista);

        List<Integer> bevetelLista = new ArrayList<>();
        tetelLista.stream().forEach(s -> bevetelLista.add(s.getBevetel()));
        List<Integer> kiadasLista = new ArrayList<>();
        tetelLista.stream().forEach(s -> kiadasLista.add(s.getKiadas()));
        List<Integer> egyenlegLista = new ArrayList<>();
        tetelLista.stream().forEach(s -> egyenlegLista.add(s.getEgyenleg()));

        egyenlegLabel.setText("Összes bevétel: " + listaOsszeado(bevetelLista)
                + "   Összes kiadás: " + listaOsszeado(kiadasLista)
                + "   Egyenleg: " + listaOsszeado(egyenlegLista));
   }


    private void afaAnalitika(Ugyfel ugyfel) {

    }

    private void vevoAnalitika(Ugyfel ugyfel) {

    }

    private void szallitoAnalitika(Ugyfel ugyfel) {

    }

    public void visszaAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/szamlak.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    private int listaOsszeado (List<Integer> lista) {
        return lista.stream().reduce(0, Integer::sum);
    }
}

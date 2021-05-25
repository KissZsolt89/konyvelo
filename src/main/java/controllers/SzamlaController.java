package controllers;

import javafx.beans.binding.StringBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.szamla.UgyfelSzamla;
import model.szamla.UgyfelSzamlaDao;
import model.ugyfel.Ugyfel;
import model.ugyfel.UgyfelDao;

import java.io.IOException;
import java.util.List;

public class SzamlaController {

    private Ugyfel aktualisUgyfel;
    private UgyfelSzamla modositandoUgyfelSzamla;

    private UgyfelDao ugyfelDao;
    private UgyfelSzamlaDao ugyfelSzamlaDao;

    @FXML
    private Label ugyfelLabel;

    @FXML
    private RadioButton bejovoRadioButton;

    @FXML
    private RadioButton kimenoRadioButton;

    @FXML
    private TextField bizonylatszamTextField;

    @FXML
    private DatePicker kelteDatePicker;

    @FXML
    private DatePicker teljesitesDatePicker;

    @FXML
    private DatePicker esedekessegDatePicker;

    @FXML
    private ChoiceBox partnerChoiceBox;

    @FXML
    private ChoiceBox fizetesiModChoiceBox;

    @FXML
    private TextField megjegyzesTextField;

    @FXML
    private ChoiceBox fokonyviSzamChoiceBox;

    @FXML
    private TextField megnevezesTextField;

    @FXML
    private ChoiceBox afaChoiceBox;

    @FXML
    private TextField nettoTextField;

    @FXML
    private TextField afaTextField;

    @FXML
    private TextField bruttoTextField;

    @FXML
    private Label hibasAdatLabel;

    public void initdata(Ugyfel ugyfel) {
        aktualisUgyfel = ugyfel;
        ugyfelLabel.setText(aktualisUgyfel.getNev());
    }

    public void initdata(UgyfelSzamla ugyfelSzamla) {
        modositandoUgyfelSzamla = ugyfelSzamla;
        ugyfelLabel.setText("Módosítás");

        if (modositandoUgyfelSzamla.getBejovo()) {
            bejovoRadioButton.setSelected(true);
            kimenoRadioButton.setSelected(false);
        }
        else {
            bejovoRadioButton.setSelected(false);
            kimenoRadioButton.setSelected(true);
        }
        bizonylatszamTextField.setText(modositandoUgyfelSzamla.getBizonylatszam());
        kelteDatePicker.setValue(modositandoUgyfelSzamla.getKelte());
        teljesitesDatePicker.setValue(modositandoUgyfelSzamla.getTeljesites());
        esedekessegDatePicker.setValue(modositandoUgyfelSzamla.getEsedekesseg());
        partnerChoiceBox.setValue(modositandoUgyfelSzamla.getPartner());
        fizetesiModChoiceBox.setValue(modositandoUgyfelSzamla.getFizetesiMod());
        megjegyzesTextField.setText(modositandoUgyfelSzamla.getMegjegyzes());
        fokonyviSzamChoiceBox.setValue(modositandoUgyfelSzamla.getFokonyviSzam());
        megnevezesTextField.setText(modositandoUgyfelSzamla.getMegnevezes());
        afaChoiceBox.setValue(modositandoUgyfelSzamla.getAfaTipus());
        nettoTextField.setText(Integer.toString(modositandoUgyfelSzamla.getNetto()));
    }

    @FXML
    public void initialize() {
        ugyfelDao = UgyfelDao.getInstance();
        ugyfelSzamlaDao = UgyfelSzamlaDao.getInstance();

        if (aktualisUgyfel != null) {
            ugyfelLabel.setText(aktualisUgyfel.getNev());
        }

        hibasAdatLabel.setText("");

        ObservableList<String> fizetesiModLista =
                FXCollections.observableArrayList("átutalás", "készpénz");
        fizetesiModChoiceBox.setItems(fizetesiModLista);
        fizetesiModChoiceBox.setValue(fizetesiModLista.get(0));

        ObservableList<String> afaLista =
                FXCollections.observableArrayList("27%", "18%", "5%", "AM");
        afaChoiceBox.setItems(afaLista);
        afaChoiceBox.setValue(afaLista.get(0));

        ObservableList<String> fokonyvLista =
                FXCollections.observableArrayList(
                        "1 - izé%",
                        "2 - mizé",
                        "3 - hozé",
                        "4 - mittomén");
        fokonyviSzamChoiceBox.setItems(fokonyvLista);
        fokonyviSzamChoiceBox.setValue(fokonyvLista.get(0));

        List<String> ugyfelLista = ugyfelDao.findAllNev();

        if (ugyfelLista.size() > 0) {
            ObservableList<String> observableUgyfelLista = FXCollections.observableArrayList();
            observableUgyfelLista.addAll(ugyfelLista);

            partnerChoiceBox.setItems(observableUgyfelLista);
            partnerChoiceBox.setValue(observableUgyfelLista.get(0));
        }

        afaTextField.textProperty().bind(
                new StringBinding() {

                    {
                        super.bind(nettoTextField.textProperty(), afaChoiceBox.valueProperty());
                    }

                    @Override
                    protected String computeValue() {
                        try {
                            String s = afaChoiceBox.getValue().toString();
                            s = Integer.toString((int) (Double.parseDouble(nettoTextField.getText())
                                    * (s.equals("AM") ? 0 : Double.parseDouble(s.substring(0, s.length() - 1)) / 100)));
                            return s;
                        } catch (NumberFormatException e) {
                            return "Hibás nettó!";
                        }
                    }
                });

        bruttoTextField.textProperty().bind(
                new StringBinding() {

                    {
                        super.bind(nettoTextField.textProperty(), afaTextField.textProperty());
                    }

                    @Override
                    protected String computeValue() {
                        try {
                            return Integer.toString(Integer.parseInt(nettoTextField.getText())
                                    + Integer.parseInt(afaTextField.getText()));
                        } catch (NumberFormatException e) {
                            return "Hibás nettó!";
                        }
                    }
                });
    }

    public void megseAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/indito.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void mentesAction(ActionEvent actionEvent) throws IOException {

        if (bizonylatszamTextField.getText().isEmpty()
                || kelteDatePicker.getValue() == null
                || teljesitesDatePicker.getValue() == null
                || esedekessegDatePicker.getValue() == null
                || partnerChoiceBox.getValue() == null
                || fizetesiModChoiceBox.getValue() == null
                || fokonyviSzamChoiceBox.getValue() == null
                || megnevezesTextField.getText().isEmpty()
                || afaChoiceBox.getValue() == null
                || bruttoTextField.getText().equals("Hibás nettó!")) {

            hibasAdatLabel.setText("Hiányzó adat!");
        }
        else {
            if (ugyfelLabel.equals("Módosítás")) {
                UgyfelSzamla ugyfelSzamla = UgyfelSzamla.builder()
                        .ugyfel(aktualisUgyfel)
                        .bejovo(bejovoRadioButton.isSelected())
                        .bizonylatszam(bizonylatszamTextField.getText())
                        .kelte(kelteDatePicker.getValue())
                        .teljesites(teljesitesDatePicker.getValue())
                        .esedekesseg(esedekessegDatePicker.getValue())
                        .partner(partnerChoiceBox.getValue().toString())
                        .fizetesiMod(fizetesiModChoiceBox.getValue().toString())
                        .megjegyzes(megjegyzesTextField.getText())
                        .fokonyviSzam(fokonyviSzamChoiceBox.getValue().toString())
                        .megnevezes(megnevezesTextField.getText())
                        .afaTipus(afaChoiceBox.getValue().toString())
                        .netto(Integer.parseInt(nettoTextField.getText()))
                        .afa(Integer.parseInt(afaTextField.getText()))
                        .brutto(Integer.parseInt(bruttoTextField.getText()))
                        .build();

                ugyfelSzamlaDao.persist(ugyfelSzamla);
            }
            else {
                modositandoUgyfelSzamla.setBejovo(bejovoRadioButton.isSelected());
                modositandoUgyfelSzamla.setBizonylatszam(bizonylatszamTextField.getText());
                modositandoUgyfelSzamla.setKelte(kelteDatePicker.getValue());
                modositandoUgyfelSzamla.setTeljesites(teljesitesDatePicker.getValue());
                modositandoUgyfelSzamla.setEsedekesseg(esedekessegDatePicker.getValue());
                modositandoUgyfelSzamla.setPartner(partnerChoiceBox.getValue().toString());
                modositandoUgyfelSzamla.setFizetesiMod(fizetesiModChoiceBox.getValue().toString());
                modositandoUgyfelSzamla.setMegjegyzes(megjegyzesTextField.getText());
                modositandoUgyfelSzamla.setFokonyviSzam(fokonyviSzamChoiceBox.getValue().toString());
                modositandoUgyfelSzamla.setMegnevezes(megnevezesTextField.getText());
                modositandoUgyfelSzamla.setAfaTipus(afaChoiceBox.getValue().toString());
                modositandoUgyfelSzamla.setNetto(Integer.parseInt(nettoTextField.getText()));
                modositandoUgyfelSzamla.setAfa(Integer.parseInt(afaTextField.getText()));
                modositandoUgyfelSzamla.setBrutto(Integer.parseInt(bruttoTextField.getText()));

                ugyfelSzamlaDao.update(modositandoUgyfelSzamla);
            }

            Parent root = FXMLLoader.load(getClass().getResource("/fxml/szamlak.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    public void partnerAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ugyfelek.fxml"));
        Parent root = fxmlLoader.load();
        fxmlLoader.<UgyfelekController>getController().initdata("szamla");
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void bejovoAction(ActionEvent actionEvent) {
        bejovoRadioButton.setSelected(true);
        kimenoRadioButton.setSelected(false);
    }

    public void kimenoAction(ActionEvent actionEvent) {
        kimenoRadioButton.setSelected(true);
        bejovoRadioButton.setSelected(false);
    }
}

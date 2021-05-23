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
import model.szamla.UgyfelSzamlaDao;
import model.ugyfel.UgyfelDao;

import java.io.IOException;
import java.util.List;

public class SzamlaController {

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
    private ChoiceBox afaChoiceBox;

    @FXML
    private TextField nettoTextField;

    @FXML
    private TextField afaTextField;

    @FXML
    private TextField bruttoTextField;

    private UgyfelDao ugyfelDao;
    private UgyfelSzamlaDao ugyfelSzamlaDao;

    public void initdata(String ugyfelnev) {
        this.ugyfelLabel.setText(ugyfelnev);
    }

    @FXML
    public void initialize() {
        ugyfelDao = UgyfelDao.getInstance();
        ugyfelSzamlaDao = UgyfelSzamlaDao.getInstance();

        ObservableList<String> fizetesiModLista =
                FXCollections.observableArrayList("átutalás", "készpénz");
        fizetesiModChoiceBox.setItems(fizetesiModLista);
        fizetesiModChoiceBox.setValue(fizetesiModLista.get(0));

        ObservableList<String> afaLista =
                FXCollections.observableArrayList("27%", "18%", "5%", "AM");
        afaChoiceBox.setItems(afaLista);
        afaChoiceBox.setValue(afaLista.get(0));

        List<String> ugyfelLista = ugyfelDao.findAllNev();

        if (ugyfelLista.size() > 0) {
            ObservableList<String> observableUgyfelLista = FXCollections.observableArrayList();
            observableUgyfelLista.addAll(ugyfelLista);

            partnerChoiceBox.setItems(observableUgyfelLista);
            partnerChoiceBox.setValue(observableUgyfelLista.get(0));
        }

        afaTextField.textProperty().bind(
                new StringBinding() {

                    { super.bind(nettoTextField.textProperty(), afaChoiceBox.valueProperty()); }

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

                    { super.bind(nettoTextField.textProperty(), afaTextField.textProperty()); }

                    @Override
                    protected String computeValue() {
                        try {
                            return Integer.toString(Integer.parseInt(nettoTextField.getText())
                                    + Integer.parseInt(afaTextField.getText()));
                        }
                        catch (NumberFormatException e) {
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

    public void mentesAction(ActionEvent actionEvent) {
    }

    public void szamlakAction(ActionEvent actionEvent) {
    }

    public void fokonyvAction(ActionEvent actionEvent) {
    }

    public void afaAnalitikaAction(ActionEvent actionEvent) {
    }

    public void vevoAnalitikaAction(ActionEvent actionEvent) {
    }

    public void szallitoAnalitikaAction(ActionEvent actionEvent) {
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

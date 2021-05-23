package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.ugyfel.Ugyfel;
import model.ugyfel.UgyfelDao;

import java.io.IOException;

public class UgyfelController {

    @FXML
    private Label ugyfelLabel;

    @FXML
    private TextField nevTextField;

    @FXML
    private TextField adoszamTextField;

    @FXML
    private TextField cimTextField;

    private UgyfelDao ugyfelDao;

    private Ugyfel modositandoUgyfel;

    @FXML
    public void initialize() {
        ugyfelDao = UgyfelDao.getInstance();
        this.ugyfelLabel.setText("Új ügyfél");
    }

    public void initdata(Ugyfel ugyfel) {
        this.modositandoUgyfel = ugyfel;
        this.ugyfelLabel.setText("Módosítás");
    }

    public void megseAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/ugyfelek.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void mentesAction(ActionEvent actionEvent) throws IOException {
        if (nevTextField.getText().isEmpty() ||
                adoszamTextField.getText().isEmpty() ||
                cimTextField.getText().isEmpty()) {
            //errorLabel.setText("* Username is empty!");
        } else {
            if (ugyfelLabel.getText() == "Új ügyfél") {
                Ugyfel ugyfel = new Ugyfel();
                ugyfel.setNev(nevTextField.getText());
                ugyfel.setAdoszam(adoszamTextField.getText());
                ugyfel.setCim(cimTextField.getText());

                ugyfelDao.persist(ugyfel);
            }
            else {
                modositandoUgyfel.setNev(nevTextField.getText());
                modositandoUgyfel.setAdoszam(adoszamTextField.getText());
                modositandoUgyfel.setCim(cimTextField.getText());

                ugyfelDao.update(modositandoUgyfel);
            }

            Parent root = FXMLLoader.load(getClass().getResource("/fxml/ugyfelek.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }
    }
}

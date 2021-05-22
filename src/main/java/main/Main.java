package main;

import com.google.inject.Guice;
import com.google.inject.Injector;
import guice.PersistenceModule;
import javafx.application.Application;
import model.ugyfel.UgyfelDao;

public class Main {

    public static void main(String[] args) {
        Application.launch(KonyveloApplication.class, args);
    }
}
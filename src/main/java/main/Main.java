package main;

import javafx.application.Application;
import model.szamla.UgyfelSzamla;
import model.szamla.UgyfelSzamlaDao;
import model.ugyfel.Ugyfel;
import model.ugyfel.UgyfelDao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

        //randomFeltoltes(500);

        Application.launch(KonyveloApplication.class, args);
    }

    private static void randomFeltoltes(int darabszam) {

        UgyfelDao ugyfelDao = UgyfelDao.getInstance();
        UgyfelSzamlaDao ugyfelSzamlaDao = UgyfelSzamlaDao.getInstance();

        Random r = new Random();

        Ugyfel ugyfel = Ugyfel.builder()
                .nev("Prezi " + r.nextInt(10000) + " Kft.")
                .adoszam((10000000 + r.nextInt(10000000)) + "-8-88")
                .cim("4800 Vásárosnamény, Bocskai utca 88.")
                .build();

        ugyfelDao.persist(ugyfel);

        List<String> fizetesiModLista = new ArrayList<>();
        fizetesiModLista.add("átutalás");
        fizetesiModLista.add("készpénz");

        List<String> afaLista = new ArrayList<>();
        afaLista.add("27%");
        afaLista.add("18%");
        afaLista.add("5%");
        afaLista.add("AM");

        List<String> fokonyvLista = new ArrayList<>();
        fokonyvLista.add("1 – Befektetett eszközök");
        fokonyvLista.add("2 - Készletek");
        fokonyvLista.add("3 - Követelések, pénzügyi eszközök");
        fokonyvLista.add("4 - Források");
        fokonyvLista.add("5 - Költségnemek");
        fokonyvLista.add("6 - Költséghelyek, általános költségek");
        fokonyvLista.add("7 - Tevékenységek költségei");
        fokonyvLista.add("8 - Értékesítés elszámolt önköltsége és ráfordítások");
        fokonyvLista.add("9 - Értékesítés árbevétele és bevételek");

        List<String> partnerLista = new ArrayList<>();
        partnerLista.add("Alma Bt.");
        partnerLista.add("Banán Kft.");
        partnerLista.add("Barack Bt.");
        partnerLista.add("Citrom Kft.");
        partnerLista.add("Cseresznye Bt.");
        partnerLista.add("Dió Kft.");
        partnerLista.add("Dinnye Kft.");
        partnerLista.add("Datolya Bt.");
        partnerLista.add("Eper Kft.");
        partnerLista.add("Egres Bt.");

        List<UgyfelSzamla> ugyfelSzamlaLista = new ArrayList<>();

        for (int i = 0; i < darabszam; i++) {

            String afaT = afaLista.get(r.nextInt(afaLista.size()));
            int nettoOsszeg = (r.nextInt(1000) + 1) * 100;
            int afaOsszeg = (int) ((afaT.equals("AM") ? 0 : (Double.parseDouble(afaT.substring(0, afaT.length() - 1))
                            / 100)) * nettoOsszeg);

            UgyfelSzamla ugyfelSzamla = UgyfelSzamla.builder()
                    .ugyfel(ugyfel)
                    .irany(r.nextInt(2) == 0 ? "bejövő" : "kimenő")
                    .bizonylatszam("BIZ00" + (r.nextInt(999) + 1))
                    .kelte(LocalDate.of(2021, r.nextInt(5) + 1, r.nextInt(28) + 1))
                    .teljesites(LocalDate.of(2021, r.nextInt(5) + 1, r.nextInt(28) + 1))
                    .esedekesseg(LocalDate.of(2021, r.nextInt(5) + 1, r.nextInt(28) + 1))
                    .partner(partnerLista.get(r.nextInt(partnerLista.size())))
                    .fizetesiMod(fizetesiModLista.get(r.nextInt(fizetesiModLista.size())))
                    .megjegyzes("")
                    .fokonyviSzam(fokonyvLista.get(r.nextInt(fokonyvLista.size())))
                    .megnevezes("Tesztáru")
                    .afaTipus(afaT)
                    .netto(nettoOsszeg)
                    .afa(afaOsszeg)
                    .brutto(nettoOsszeg+afaOsszeg)
                    .build();

            ugyfelSzamlaLista.add(ugyfelSzamla);
        }
        ugyfelSzamlaDao.persistAll(ugyfelSzamlaLista);
    }
}
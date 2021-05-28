package controllers;

import model.tetel.Tetel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KivonatControllerTest {

    KivonatController underTest;

    @BeforeEach
    void setUp() {
        underTest = new KivonatController();
    }

    @Test
    void testEgyenlegSzamolo() {
        List<Tetel> lista = new ArrayList<>();
        assertEquals("Összes bevétel: 0   Összes kiadás: 0   Egyenleg: 0",
                underTest.egyenlegSzamolo(lista));

        lista.add(Tetel.builder().bevetel(0).kiadas(0).egyenleg(0).build());
        assertEquals("Összes bevétel: 0   Összes kiadás: 0   Egyenleg: 0",
                underTest.egyenlegSzamolo(lista));

        lista.add(Tetel.builder().bevetel(3000).kiadas(2000).egyenleg(1000).build());
        assertEquals("Összes bevétel: 3000   Összes kiadás: 2000   Egyenleg: 1000",
                underTest.egyenlegSzamolo(lista));

        lista.add(Tetel.builder().bevetel(0).kiadas(5000).egyenleg(-5000).build());
        assertEquals("Összes bevétel: 3000   Összes kiadás: 7000   Egyenleg: -4000",
                underTest.egyenlegSzamolo(lista));
    }

    @Test
    void testListaOsszeado() {
        assertEquals(0, underTest.listaOsszeado(Arrays.asList(new Integer[] {})));
        assertEquals(9, underTest.listaOsszeado(Arrays.asList(new Integer[] {9})));
        assertEquals(36, underTest.listaOsszeado(Arrays.asList(new Integer[] {1, 2, 3, 4, 5, 6, 7, 8})));
        assertEquals(-55, underTest.listaOsszeado(Arrays.asList(new Integer[] {-37, -18})));
        assertEquals(40, underTest.listaOsszeado(Arrays.asList(new Integer[] {-40, 80})));
    }
}
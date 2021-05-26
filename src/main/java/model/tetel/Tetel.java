package model.tetel;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tetel {

    private String tetelNev;

    private int bevetel;

    private int kiadas;

    private int egyenleg;
}

package model.szamla;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Inheritance(strategy= InheritanceType.TABLE_PER_CLASS)
public class Szamla {

    @GeneratedValue
    @Id
    private Long id;

    @Column(nullable=false)
    private String irany;

    @Column(nullable=false)
    private String bizonylatszam;

    @Column(nullable=false)
    private LocalDate kelte;

    @Column(nullable=false)
    private LocalDate teljesites;

    @Column(nullable=false)
    private LocalDate esedekesseg;

    @Column(nullable=false)
    private String partner;

    @Column(nullable=false)
    private String fizetesiMod;

    @Column
    private String megjegyzes;

    @Column(nullable=false)
    private String fokonyviSzam;

    @Column(nullable=false)
    private String megnevezes;

    @Column(nullable=false)
    private String afaTipus;

    @Column(nullable=false)
    private Integer netto;

    @Column(nullable=false)
    private Integer afa;

    @Column(nullable=false)
    private Integer brutto;
}

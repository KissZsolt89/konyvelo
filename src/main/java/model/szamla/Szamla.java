package model.szamla;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Inheritance(strategy= InheritanceType.TABLE_PER_CLASS)
public class Szamla {

    @GeneratedValue
    @Id
    private Integer id;

    @Column(nullable=false)
    private Date kelte;

    @Column(nullable=false)
    private Date teljesites;

    @Column(nullable=false)
    private Date esedekesseg;

    @Column(nullable=false)
    private String partner;

    @Column(nullable=false)
    private String bizonylatszam;

    @Column(nullable=false)
    private String fizetesiMod;

    @Column
    private String megjegyzes;

    @Column(nullable=false)
    private Integer netto;

    @Column(nullable=false)
    private Integer afa;

    @Column(nullable=false)
    private Integer brutto;
}

package model.ugyfel;

import javax.persistence.*;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Ugyfel {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable=false, unique=true)
    private String nev;

    @Column(nullable=false, unique=true)
    private String adoszam;

    @Column(nullable=false)
    private String cim;
}

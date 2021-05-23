package model.szamla;

import lombok.*;
import lombok.experimental.SuperBuilder;

import model.ugyfel.Ugyfel;

import javax.persistence.*;

@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class UgyfelSzamla extends Szamla {

    @ManyToOne
    @JoinColumn(nullable=false)
    private Ugyfel ugyfel;
}

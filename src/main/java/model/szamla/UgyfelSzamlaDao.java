package model.szamla;

import jpa.GenericJpaDao;
import model.tetel.Tetel;

import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UgyfelSzamlaDao extends GenericJpaDao<UgyfelSzamla> {

    private static UgyfelSzamlaDao instance;

    private UgyfelSzamlaDao() {
        super(UgyfelSzamla.class);
    }

    public static UgyfelSzamlaDao getInstance() {
        if (instance == null) {
            instance = new UgyfelSzamlaDao();
            instance.setEntityManager(Persistence.createEntityManagerFactory("konyvelo").createEntityManager());
        }
        return instance;
    }

    public List<UgyfelSzamla> findAllByNev(String nev) {
        return entityManager.createQuery("SELECT u FROM UgyfelSzamla u WHERE u.ugyfel.nev = :nev", UgyfelSzamla.class)
                .setParameter("nev", nev)
                .getResultList();
    }

    public Optional<UgyfelSzamla> findByID(Long id) {
        try {
            return Optional.of(entityManager.createQuery("SELECT u FROM UgyfelSzamla u WHERE u.id = :id", UgyfelSzamla.class)
                    .setParameter("id", id)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public List<UgyfelSzamla> findAllByNev_fokonyviSzam(String nev) {
        return entityManager.createQuery("SELECT u FROM UgyfelSzamla u WHERE u.ugyfel.nev = :nev ORDER BY u.fokonyviSzam", UgyfelSzamla.class)
                .setParameter("nev", nev)
                .getResultList();
    }

    public List<Tetel> findByNev_fokonyviSzam(String nev) {
        List<Tetel> tetelLista = new ArrayList<>();
        entityManager.createQuery(
                "SELECT fokonyviSzam, irany, sum(brutto) FROM UgyfelSzamla u WHERE u.ugyfel.nev = :nev GROUP BY fokonyviSzam, irany ORDER BY fokonyviSzam"
                 ,Object[].class)
                .setParameter("nev", nev)
                .getResultList().stream().forEach(s -> tetelLista.add(new Tetel(s[0].toString(),
                    s[1].toString().equals("bejövő") ? 0 : (int)s[2], s[1].toString().equals("bejövő") ? (int)s[2] : 0, 0)));
        return tetelLista;
    }
}

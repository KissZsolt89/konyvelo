package model.szamla;

import com.google.inject.persist.Transactional;
import jpa.GenericJpaDao;
import model.ugyfel.Ugyfel;

import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
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
            instance.setEntityManager(Persistence.createEntityManagerFactory("konyvelo-mysql").createEntityManager());
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
}

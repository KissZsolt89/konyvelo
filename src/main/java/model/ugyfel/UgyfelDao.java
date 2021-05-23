package model.ugyfel;

import com.google.inject.persist.Transactional;
import jpa.GenericJpaDao;

import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class UgyfelDao extends GenericJpaDao<Ugyfel> {

    private static UgyfelDao instance;

    private UgyfelDao() {
        super(Ugyfel.class);
    }

    public static UgyfelDao getInstance() {
        if (instance == null) {
            instance = new UgyfelDao();
            instance.setEntityManager(Persistence.createEntityManagerFactory("konyvelo-mysql").createEntityManager());
        }
        return instance;
    }

    public Optional<Ugyfel> findByNev(String nev) {
        try {
            return Optional.of(entityManager.createQuery("SELECT u FROM Ugyfel u WHERE u.nev = :nev", Ugyfel.class)
                    .setParameter("nev", nev)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public List<String> findAllNev() {
        TypedQuery<String> typedQuery = entityManager.createQuery("SELECT nev FROM Ugyfel", String.class);
        return typedQuery.getResultList();
    }
}

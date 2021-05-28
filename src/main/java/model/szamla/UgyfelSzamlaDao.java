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
            instance.setEntityManager(Persistence.createEntityManagerFactory("konyvelo")
                    .createEntityManager());
        }
        return instance;
    }

    public void persistAll(List<UgyfelSzamla> lista) {
        entityManager.getTransaction().begin();
        int i =0;
        for (UgyfelSzamla u : lista) {
            entityManager.persist(u);
        }
        entityManager.getTransaction().commit();
    }

    public List<UgyfelSzamla> findAllByNev(String nev) {
        return entityManager.createQuery("SELECT u FROM UgyfelSzamla u WHERE u.ugyfel.nev = :nev",
                UgyfelSzamla.class)
                .setParameter("nev", nev)
                .getResultList();
    }

    public Optional<UgyfelSzamla> findByID(Long id) {
        try {
            return Optional.of(entityManager.createQuery("SELECT u FROM UgyfelSzamla u WHERE u.id = :id",
                    UgyfelSzamla.class)
                    .setParameter("id", id)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public List<Tetel> findAllByNev_fokonyviSzam(String nev) {
        return objectToTetelList(entityManager.createQuery(
                "SELECT fokonyviSzam, irany, sum(brutto) FROM UgyfelSzamla u WHERE u.ugyfel.nev = :nev "
                        + "GROUP BY fokonyviSzam, irany ORDER BY fokonyviSzam"
                , Object[].class)
                .setParameter("nev", nev)
                .getResultList());
    }

    public List<Tetel> findAllByNev_afaAnalitika(String nev) {
        return objectToTetelList(entityManager.createQuery(
                "SELECT afaTipus, irany, sum(brutto) FROM UgyfelSzamla u WHERE u.ugyfel.nev = :nev "
                        + "GROUP BY afaTipus, irany ORDER BY afaTipus DESC"
                , Object[].class)
                .setParameter("nev", nev)
                .getResultList());
    }

    public List<Tetel> findAllByNev_vevoSzallitoAnalitika(String nev) {
        return objectToTetelList(entityManager.createQuery(
                "SELECT partner, irany, sum(brutto) FROM UgyfelSzamla u WHERE u.ugyfel.nev = :nev "
                        + "GROUP BY partner, irany ORDER BY partner"
                , Object[].class)
                .setParameter("nev", nev)
                .getResultList());
    }

    private List<Tetel> objectToTetelList(List<Object[]> objectList) {
        List<Tetel> tLista = new ArrayList<>();

        objectList.stream().forEach(s -> tLista.add(new Tetel(s[0].toString(),
                s[1].toString().equals("bejövő") ? 0 : (int) s[2],
                s[1].toString().equals("bejövő") ? (int) s[2] : 0, 0)));

        for (int i = 1; i < tLista.size(); i++) {

            if (tLista.get(i).getTetelNev().equals(tLista.get(i-1).getTetelNev())) {

                tLista.get(i-1).setBevetel(tLista.get(i-1).getBevetel() + tLista.get(i).getBevetel());
                tLista.get(i-1).setKiadas(tLista.get(i-1).getKiadas() + tLista.get(i).getKiadas());
                tLista.get(i-1).setEgyenleg(tLista.get(i-1).getBevetel() - tLista.get(i-1).getKiadas());

                tLista.remove(i);
            }
        }
        return tLista;
    }
}

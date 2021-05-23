package model.szamla;

import jpa.GenericJpaDao;

import javax.persistence.Persistence;

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
}

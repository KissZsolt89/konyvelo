package jpa;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.google.inject.persist.Transactional;

public abstract class GenericJpaDao<T> {

    protected Class<T> entityClass;
    protected EntityManager entityManager;

    public GenericJpaDao(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void persist(T entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
    }

    public Optional<T> find(Object primaryKey) {
        return Optional.ofNullable(entityManager.find(entityClass, primaryKey));
    }

    public List<T> findAll() {
        TypedQuery<T> typedQuery = entityManager.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass);
        return typedQuery.getResultList();
    }

    public void remove(T entity) {
        entityManager.getTransaction().begin();
        entityManager.remove(entity);
        entityManager.getTransaction().commit();
    }

    public void update(T entity) {
        entityManager.getTransaction().begin();
        entityManager.merge(entity);
        entityManager.getTransaction().commit();
    }
}

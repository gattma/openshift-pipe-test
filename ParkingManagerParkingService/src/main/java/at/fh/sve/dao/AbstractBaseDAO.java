package at.fh.sve.dao;

import org.slf4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class AbstractBaseDAO<E> implements BaseDAO<E> {

    @Inject
    private Logger LOG;

    private final Class<E> clazz;

    @Inject
    protected EntityManager em;

    public AbstractBaseDAO() {
        this.clazz = (Class<E>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public List<E> getAll() {
        String className = clazz.getName().substring(clazz.getName().lastIndexOf('.') + 1);
        TypedQuery<E> q = em.createQuery("select c from " + className + " c", clazz);

        return q.getResultList();
    }

    @Override
    public E findById(final Serializable id) {
        return em.find(clazz, id);
    }

    @Override
    public E create(E entity) {
        em.persist(entity);
        em.flush();
        LOG.debug(String.format("persisted new entity: %s", entity));
        return entity;
    }

    @Override
    public E update(E entity) {
        return em.merge(entity);
    }

    @Override
    public void delete(final Serializable id) {
        E persisted = this.findById(id);
        if (persisted == null)
            throw new EntityNotFoundException(String.format("Entity mit der id = '%s' konnte nicht gefunden werden!", id));

        em.remove(persisted);
    }

}

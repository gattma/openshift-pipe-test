package at.fh.sve.dao;

import java.io.Serializable;
import java.util.List;

public interface BaseDAO<E> {

    List<E> getAll();

    E findById(final Serializable id);

    E create(E entity);

    E update(E entity);

    void delete(final Serializable id);
}

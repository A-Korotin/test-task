package org.ra.atomidtesttask.application;

import org.ra.atomidtesttask.application.exception.NotFoundException;

public interface BaseService<E, ID> {
    E findById(ID id) throws NotFoundException;
    E save(E entity);
    void deleteById(ID id) throws NotFoundException;
    boolean existsById(ID id);
}

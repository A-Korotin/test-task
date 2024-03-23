package org.ra.atomidtesttask.application;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PageableService<E> {
    Page<E> findAll(Pageable pageable);
}

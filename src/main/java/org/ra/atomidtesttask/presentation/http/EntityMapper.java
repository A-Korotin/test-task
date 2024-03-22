package org.ra.atomidtesttask.presentation.http;

public interface EntityMapper<E, W, R> {
    E fromDto(W dto);
    R toDto(E entity);
}

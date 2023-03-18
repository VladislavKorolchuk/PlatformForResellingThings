package ru.work.graduatework.mapper;

import java.util.Collection;
import java.util.List;

public interface MapperScheme<D, E> {

    E toEntity(D dto);

    D toDto(E entity);
    List<D> toDto (Collection<E> entity);
    List<E> toEntity (Collection<D> dto);

}

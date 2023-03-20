package ru.work.graduatework.mapper;

import java.util.Collection;
import java.util.List;

public interface MapperScheme<A, B> {

    B toEntity(A dto);
    A toDto(B entity);
    List<A> toDto (Collection<B> entity);
    List<B> toEntity (Collection<A> dto);

}

package com.epam.internship.converter;


import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public abstract class DTOConverter<T, D> {
    protected final ModelMapper modelMapper;

    public T toEntity(D dto) {
        return modelMapper.map(dto, getTypeEntity());
    }

    public D toDto(T entity) {
        return modelMapper.map(entity, getTypeDTO());
    }

    public List<T> toEntityList(List<D> dtoList) {
        return dtoList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public List<D> toDtoList(List<T> entityList) {
        return entityList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    protected abstract Class<T> getTypeEntity();

    protected abstract Class<D> getTypeDTO();
}

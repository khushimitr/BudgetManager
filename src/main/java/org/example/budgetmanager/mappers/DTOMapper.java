package org.example.budgetmanager.mappers;

import java.util.List;

public interface DTOMapper<E, RQ, RS> {

    public E toEntity(RQ requestDto);

    public RS toResponseDTO(E entity);

    public List<E> toEntityList(List<RQ> requestDtoList);

    public List<RS> toResponseDTOList(List<E> entityList);

}

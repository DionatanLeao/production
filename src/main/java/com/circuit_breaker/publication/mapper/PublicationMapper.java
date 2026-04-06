package com.circuit_breaker.publication.mapper;

import com.circuit_breaker.publication.controller.request.PublicationRequest;
import com.circuit_breaker.publication.domain.Publication;
import com.circuit_breaker.publication.repository.entity.PublicationEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PublicationMapper {
    PublicationEntity toPublicationEntity(Publication publication);

    Publication toPublication(PublicationEntity entity);

    Publication toPublication(PublicationRequest request);
}

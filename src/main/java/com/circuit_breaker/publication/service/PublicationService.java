package com.circuit_breaker.publication.service;

import com.circuit_breaker.publication.client.CommentClient;
import com.circuit_breaker.publication.domain.Publication;
import com.circuit_breaker.publication.mapper.PublicationMapper;
import com.circuit_breaker.publication.repository.PublicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublicationService {
    @Autowired
    private PublicationRepository publicationRepository;

    @Autowired
    private CommentClient commentClient;

    @Autowired
    private PublicationMapper publicationMapper;

    public void insert(Publication publication) {
        publicationRepository.save(publicationMapper.toPublicationEntity(publication));
    }

    public List<Publication> findAll() {
        return publicationRepository.findAll().stream().map(publicationMapper::toPublication).toList();
    }

    public Publication findById(String id) {
        var publication = publicationRepository.findById(id)
                .map(publicationMapper::toPublication)
                .orElseThrow(RuntimeException::new);

        var comments = commentClient.getComments(id);
        publication.setComments(comments);

        return publication;
    }
}

package com.circuit_breaker.publication.service;

import com.circuit_breaker.publication.domain.Publication;
import com.circuit_breaker.publication.mapper.PublicationMapper;
import com.circuit_breaker.publication.repository.PublicationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PublicationService {
    @Autowired
    private PublicationRepository publicationRepository;

    @Autowired
    private CommentService commentService;

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

        var comments = commentService.getComments(id);
        publication.setComments(comments);

        return publication;
    }
}

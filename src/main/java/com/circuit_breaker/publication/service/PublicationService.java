package com.circuit_breaker.publication.service;

import com.circuit_breaker.publication.client.CommentClient;
import com.circuit_breaker.publication.domain.Publication;
import com.circuit_breaker.publication.exceptions.FallbackException;
import com.circuit_breaker.publication.mapper.PublicationMapper;
import com.circuit_breaker.publication.repository.PublicationRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
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
    private CommentClient commentClient;

    @Autowired
    private PublicationMapper publicationMapper;

    public void insert(Publication publication) {
        publicationRepository.save(publicationMapper.toPublicationEntity(publication));
    }

    public List<Publication> findAll() {
        return publicationRepository.findAll().stream().map(publicationMapper::toPublication).toList();
    }

    @CircuitBreaker(name = "comments", fallbackMethod = "findByIdFallback")
    public Publication findById(String id) {
        var publication = publicationRepository.findById(id)
                .map(publicationMapper::toPublication)
                .orElseThrow(RuntimeException::new);

        var comments = commentClient.getComments(id);
        publication.setComments(comments);

        return publication;
    }

    private Publication findByIdFallback(String id, Throwable cause) {
        log.warn("[WARN] fallback with id {}", id);
        throw new FallbackException(cause);
    }
}

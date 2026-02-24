package com.circuit_breaker.publication.controller;

import com.circuit_breaker.publication.controller.request.PublicationRequest;
import com.circuit_breaker.publication.domain.Publication;
import com.circuit_breaker.publication.mapper.PublicationMapper;
import com.circuit_breaker.publication.service.PublicationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/publications")
public class PublicationController {
    @Autowired
    private PublicationService service;

    @Autowired
    private PublicationMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void insert(@RequestBody @Valid PublicationRequest publicationRequest) {
        service.insert(mapper.toPublication(publicationRequest));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Publication> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Publication findById(@PathVariable("id") String id) {
        return service.findById(id);
    }
}

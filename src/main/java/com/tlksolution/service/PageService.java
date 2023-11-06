package com.tlksolution.service;

import com.tlksolution.model.Page;
import com.tlksolution.repository.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PageService {
    @Autowired
    private PageRepository pageRepository;

    public List<Page> findAll() {
        return pageRepository.findAll();
    }

    public Optional<Page> findById(Long id) {
        return pageRepository.findById(id);
    }

    public Page save(Page book) {
        return pageRepository.save(book);
    }

    public void deleteById(Long id) {
        pageRepository.deleteById(id);
    }

}

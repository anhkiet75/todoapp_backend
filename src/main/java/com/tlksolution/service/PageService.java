package com.tlksolution.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.tlksolution.dto.request.PageRequestDto;
import com.tlksolution.model.Page;
import com.tlksolution.repository.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.testcontainers.shaded.com.fasterxml.jackson.core.TreeNode;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PageService {
    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private ObjectMapper objectMapper;
    public List<Page> findAll() {
        List<Page> rootPages = pageRepository.findAllRoots();
        List<Long> rootPageIds = rootPages.stream().map(Page::getId).toList();
        List<Page> results =  new ArrayList<>();
        rootPageIds.forEach( id -> {
            results.add(findById(id).orElseThrow());
        });
        return results;
    }

    public Optional<Page> findById(Long id) {
        return pageRepository.findById(id);
    }

    public Page save(PageRequestDto page) {
        Page model = convertToModel(page);
        return pageRepository.save(model);
    }

    public Page update(Long id, PageRequestDto page) {
        Page model = convertToModel(page);
        Page updatePage =  pageRepository.findById(id).orElseThrow();
        updatePage.setTitle(model.getTitle());
        updatePage.setIcon(model.getIcon());
        updatePage.setContent(model.getContent());
        updatePage.setParentPage(model.getParentPage());
        return pageRepository.save(updatePage);
    }

    public void deleteById(Long id) {
        pageRepository.deleteById(id);
    }

    private Page convertToModel(PageRequestDto dto) {
        Page page = new Page();
        page.setTitle(dto.getTitle());
        page.setIcon(dto.getIcon());
        page.setContent(dto.getContent());
        if (dto.getParentPageId() != null) {
            Page parentPage = pageRepository.findById(dto.getParentPageId()).orElseThrow();
            page.setParentPage(parentPage);
        }
        return page;
    }
}

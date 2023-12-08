package com.tlksolution.controller;

import com.github.fge.jsonpatch.JsonPatch;
import com.tlksolution.dto.request.PageRequestDto;
import com.tlksolution.model.Page;
import com.tlksolution.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/pages")
public class PageController {
    @Autowired
    private PageService pageService;

    @GetMapping(produces= MediaType.APPLICATION_JSON_VALUE)
    public List<Page> findAll() {
        return pageService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Page> findById(@PathVariable Long id) {
        return pageService.findById(id);
    }

    // create a page
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Page create(@RequestBody PageRequestDto page) {
        return pageService.save(page);
    }

    // update a page
    @PatchMapping("/{id}")
    public Page update(@PathVariable Long id, @RequestBody Map<String,String> fields) {
        return pageService.update(id, fields);
    }

    // delete a page
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        pageService.deleteById(id);
    }
}

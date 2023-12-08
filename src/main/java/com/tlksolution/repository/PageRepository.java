package com.tlksolution.repository;

import com.tlksolution.model.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface PageRepository extends JpaRepository<Page, Long> {
    @Query("SELECT page from Page page"
            +" WHERE page.parentPage.id IS NULL"
    )
    public List<Page> findAllRoots();
}
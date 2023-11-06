package com.tlksolution.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity

public class Page {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "parentPage", cascade =  CascadeType.ALL)
    private Set<Page> subPage = new LinkedHashSet<>();;

    @ManyToOne
    private Page parentPage;

    public Page() {
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Page> getSubPage() {
        return subPage;
    }

    @Override
    public String toString() {
        return "Page{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }

    public void addSubPage(Page page) {
        this.subPage.add(page);
    }

    public void setSubPage(Set<Page> subPage) {
        this.subPage = subPage;
    }
//    public Page getParentPage() {
//        return parentPage;
//    }

    public void setParentPage(Page parentPage) {
        this.parentPage = parentPage;
    }

    public Page(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Page page = (Page) o;
        return Objects.equals(id, page.id) && Objects.equals(title, page.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }
}

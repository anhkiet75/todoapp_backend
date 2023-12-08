package com.tlksolution.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Entity

public class Page {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="page_id")
    private Long id;
    @Getter
    private String title;
    private String content;
    private String icon;

    @Getter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_page_id")
    @JsonIgnore
    private Page parentPage;

    @OneToMany(fetch = FetchType.LAZY,  mappedBy = "parentPage", cascade = CascadeType.ALL)
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<Page> childrenPage;

    public Page() {
    }

    public Set<Page> getChildrenPage() {
        return childrenPage;
    }

    public void setChildrenPage(Set<Page> childrenPage) {
        this.childrenPage = childrenPage;
    }

    public void setParentPage(Page parentPage) {
        this.parentPage = parentPage;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Page(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Page page = (Page) o;
        return Objects.equals(id, page.id) && Objects.equals(title, page.title);
    }

    @Override
    public String toString() {
        return "Page{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }
}

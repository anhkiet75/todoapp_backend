package com.tlksolution.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PageRequestDto {
    private Long id;
    private Long parentPageId;
    private String title;
    private String content;
    private String icon;
}

package com.springcloud.api.auth.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Accessors(chain = true)
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
public class ResourceDto implements Serializable {
    private Long id;
    private String icon;
    private String identity;
    private String name;
    private Long parentId;
    private String parentIds;
    private Boolean isShow;
    private String url;
    private Integer weight;
    private String httpMethod;
}

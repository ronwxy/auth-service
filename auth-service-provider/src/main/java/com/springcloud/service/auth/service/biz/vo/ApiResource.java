package com.springcloud.service.auth.service.biz.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.beans.Transient;
import java.io.Serializable;

/**
 * @author liubo
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"url", "httpMethod"})
@ToString
public class ApiResource implements Serializable {
    public static final String MATCH_ALL_HTTP_METHOD = "*";
    public static final String WHITE_LIST_API_MARK_HTTP_METHOD_PREFIX = "!";
    private Long id;
    private String identity;
    private String name;
    private Long parentId;
    private String parentIds;
    private String url;
    private String httpMethod;

    public ApiResource(Long id, String identity, String name, Long parentId, String parentIds, String url, String httpMethod) {
        this.id = id;
        this.identity = identity;
        this.name = name;
        this.parentId = parentId;
        this.parentIds = parentIds;
        this.url = url;
        this.httpMethod = StringUtils.isEmpty(httpMethod) ? MATCH_ALL_HTTP_METHOD : httpMethod;
    }

    /**
     * means permit all,need no authenticate and no spi
     *
     * @return
     */
    @Transient
    @JsonIgnore
    public boolean isWhiteListApi() {
        return httpMethod != null && httpMethod.startsWith(WHITE_LIST_API_MARK_HTTP_METHOD_PREFIX);
    }
}

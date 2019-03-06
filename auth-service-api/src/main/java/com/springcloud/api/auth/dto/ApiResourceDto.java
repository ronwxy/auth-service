package com.springcloud.api.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.util.StringUtils;

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
public class ApiResourceDto implements Serializable {
    public static final String MATCH_ALL_HTTP_METHOD = "*";
    public static final String WHITE_LIST_API_MARK_HTTP_METHOD_PREFIX = "!";
    public static final String SUB_PATH_MATCH_SUFFIX = "/**";
    public static final String PARENT_IDS_PATH_SEPARATOR = "/";

    private Long id;
    private String identity;
    private String name;
    private Long parentId;
    private String parentIds;
    private String url;
    private String httpMethod;

    public ApiResourceDto(Long id, String identity, String name, Long parentId, String parentIds, String url, String httpMethod) {
        this.id = id;
        this.identity = identity;
        this.name = name;
        this.parentId = parentId;
        this.parentIds = parentIds;
        this.url = url;
        this.httpMethod = StringUtils.isEmpty(httpMethod) ? MATCH_ALL_HTTP_METHOD : httpMethod;
    }

    @JsonIgnore
    @Transient
    public static boolean isWhiteListApi(String httpMethod) {
        return httpMethod != null && httpMethod.startsWith(WHITE_LIST_API_MARK_HTTP_METHOD_PREFIX);
    }

    @Transient
    @JsonIgnore
    public static String getRealHttpMethod(String httpMethod) {
        return isWhiteListApi(httpMethod) ? httpMethod.substring(1) : httpMethod;
    }

    /**
     * means permit all,need no authenticate or no spi
     *
     * @return
     */
    @Transient
    @JsonIgnore
    public boolean isWhiteListApi() {
        return isWhiteListApi(this.httpMethod);
    }

    @Transient
    @JsonIgnore
    public String getRealHttpMethod() {
        return getRealHttpMethod(httpMethod);
    }
}

package com.springcloud.service.auth.domain;

import com.springboot.autoconfig.tkmapper.domain.FixedIdBaseDomain;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "sys_resource")
@Accessors(chain = true)
@Getter
@Setter
@ToString
public class Resource extends FixedIdBaseDomain<Long> {

    public static final String API_ROOT_IDENTITY = "controller-root";
    public static final String PARENT_IDS_PATH_SEPARATE = "/";
    public static final String SEPARATE_CHAR = ",";
    public static final String DEFAULT_API_ROLE = "default-controller-role";
    private static final long serialVersionUID = 1L;

    private String icon;
    private String identity;
    private String name;
    private Long parentId;
    private String parentIds;
    private Boolean isShow;
    private String url;
    private Integer weight;
    private String httpMethod;

    @Transient
    public static String generateChildParentIdsPrefix(Resource r) {
        return r.getParentIds() + r.getId() + PARENT_IDS_PATH_SEPARATE;
    }

    @Transient
    public String getChildParentIdsPrefix() {
        return generateChildParentIdsPrefix(this);
    }


}

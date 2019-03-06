package com.springcloud.api.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Set;

/**
 * create user and authorize job/roles to user;
 *
 * @author liubo
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserSaveReqDto implements Serializable {
    private UserSaveDto user;
    /**
     * ids group parameters
     */
    private Set<Long> jobIds;
    private Set<Long> roleIds;
    private Set<Long> groupIds;
    /**
     * names group parameters
     * if names effected,ids do not effected;
     */
    private Set<String> jobNames;
    private Set<String> roleNames;
    private Set<String> groupNames;
    /**
     * if roles is empty,remove all roles authorize to user directly,this is the default behaviour
     */
    private boolean rolesEmpty2remove = true;
    /**
     * default,if jobs is empty,do nothing,this is the default behaviour
     */
    private boolean jobsEmpty2remove = false;
    /**
     * if groups is empty,do nothing,,this is the default behaviour
     */
    private boolean groups2remove = false;
}

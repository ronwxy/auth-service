package com.springcloud.service.auth.util;

public enum AuthorizationType {
    /**
     * authorize roles to user
     */
    user,
    /**
     * authorize org/job to user,implicit authorize roles to user
     */
    organization_job,
    /**
     * authorize org group to user,implicit authorize roles to user
     */
    organization_group,
    /**
     * authorize user group to user,implicit authoriza roles to user
     */
    user_group;
}

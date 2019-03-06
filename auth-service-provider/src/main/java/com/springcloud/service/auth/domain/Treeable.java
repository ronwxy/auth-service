package com.springcloud.service.auth.domain;

import java.util.List;

public interface Treeable<ID, T> {

    T findParent();

    boolean hasParent();

    boolean hasChild();

    List<T> findDirectChildren();

    List<T> findChildren();

    List<T> findSibling();

    default boolean isRoot() {
        return !hasParent();
    }

    default boolean isLeaf() {
        return !hasChild();
    }


}

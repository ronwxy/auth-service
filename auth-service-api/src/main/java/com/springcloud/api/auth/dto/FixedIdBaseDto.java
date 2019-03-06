package com.springcloud.api.auth.dto;

public abstract class FixedIdBaseDto<ID> extends BaseDto<ID> {
    private ID id;

    public void setId(ID id) {
        this.id = id;
    }

    public ID getId() {
        return id;
    }
}

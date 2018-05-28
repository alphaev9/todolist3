package com.alpha.repository.rdb.singleTable;

import com.alpha.repository.PersistId;

public class RdbId implements PersistId {
    private Integer id;
    public RdbId(Integer id) {
        this.id=id;
    }

    @Override
    public Integer getRealId() {
        return id;
    }
}

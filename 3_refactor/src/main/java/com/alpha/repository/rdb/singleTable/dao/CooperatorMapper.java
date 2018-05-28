package com.alpha.repository.rdb.singleTable.dao;

import com.alpha.repository.entity.Cooperator;

import java.util.List;

public interface CooperatorMapper {
    List<Cooperator> getCooperatorsByBacklogId(Integer backlogId);
}
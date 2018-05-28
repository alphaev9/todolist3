package com.alpha.repository.rdb.singleTable.dao;

import com.alpha.repository.entity.Backlog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BacklogMapper {
    int deleteById(Integer id);

    Backlog selectById(Integer id);

    int updateState(Backlog backlog);

    void insert(@Param("backlog") Backlog backlog,@Param("userId") Integer userId);

    Backlog selectByUserId(Integer userId);

}
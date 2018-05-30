package com.alpha.repository;

import com.alpha.repository.entity.Backlog;
import com.alpha.repository.entity.BacklogState;

import java.util.List;

/********************
 * 对底层存储的唯一要求是每个实体有全局唯一的Id
 * */

public interface BacklogRepository {
    void updateBacklog(Backlog backlog);
    Backlog getBacklogById(PersistId backlogId);
}

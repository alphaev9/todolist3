package com.alpha.repository.rdb.singleTable;

import com.alpha.repository.BacklogRepository;
import com.alpha.repository.PersistId;
import com.alpha.repository.rdb.singleTable.dao.BacklogMapper;
import com.alpha.repository.entity.Backlog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BacklogRepositoryImpl implements BacklogRepository {
    @Autowired
    private BacklogMapper backlogMapper;

    @Override
    public void updateBacklogState(Backlog backlog) {
        backlogMapper.updateState(backlog);
    }

    @Override
    public void removeBacklog(PersistId backlogId) {
        backlogMapper.deleteById(backlogId.getRealId());
    }

    @Override
    public Backlog getBacklogById(PersistId backlogId) {
        return backlogMapper.selectById(backlogId.getRealId());
    }
}

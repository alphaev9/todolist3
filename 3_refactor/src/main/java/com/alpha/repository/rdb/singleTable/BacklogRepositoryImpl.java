package com.alpha.repository.rdb.singleTable;

import com.alpha.repository.BacklogRepository;
import com.alpha.repository.PersistId;
import com.alpha.repository.entity.BacklogState;
import com.alpha.repository.rdb.singleTable.dao.BacklogMapper;
import com.alpha.repository.entity.Backlog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile("mybatis")
public class BacklogRepositoryImpl implements BacklogRepository {
    @Autowired
    private BacklogMapper backlogMapper;

    @Override
    public void updateBacklog(Backlog backlog) {
        backlogMapper.updateState(backlog);
    }

    @Override
    public Backlog getBacklogById(PersistId backlogId) {
        return backlogMapper.selectById(backlogId.getRealId());
    }
}

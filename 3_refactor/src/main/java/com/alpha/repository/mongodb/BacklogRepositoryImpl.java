package com.alpha.repository.mongodb;

import com.alpha.repository.BacklogRepository;
import com.alpha.repository.PersistId;
import com.alpha.repository.entity.Backlog;
import com.alpha.repository.mongodb.dao.BacklogDAO;
import com.alpha.repository.mongodb.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("mongodb")
public class BacklogRepositoryImpl implements BacklogRepository {
    @Autowired
    private BacklogDAO backlogDAO;
    @Autowired
    private UserDAO userDAO;

    @Override
    public void updateBacklog(Backlog backlog) {
        backlogDAO.update(backlog.getId(),backlog,"Backlog");
    }

    @Override
    public Backlog getBacklogById(PersistId backlogId) {
        Backlog backlog = backlogDAO.getById(backlogId, Backlog.class);
        return backlog;
    }
}

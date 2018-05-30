package com.alpha.repository.rdb.singleTable;

import com.alpha.repository.PersistId;
import com.alpha.repository.UserRepository;
import com.alpha.repository.entity.Backlog;
import com.alpha.repository.entity.User;
import com.alpha.repository.rdb.singleTable.dao.BacklogMapper;
import com.alpha.repository.rdb.singleTable.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
//@Repository
public class UserRepositoryImpl implements UserRepository {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BacklogMapper backlogMapper;

    @Override
    public List<User> getAllUsers() {
        return userMapper.selectAll();
    }

    @Override
    public void addUser(User user) {
        userMapper.insert(user);
    }

    @Override
    public User getUserByName(String name) {
        return userMapper.selectByName(name);
    }

    @Override
    public User getUserByPersistId(PersistId id) {

        return userMapper.selectById(id.getRealId());
    }

    @Override
    public void addBacklog(PersistId userId, Backlog backlog) {
        backlogMapper.insert(backlog,userId.getRealId());
    }

    @Override
    public void removeBacklog(PersistId userId, PersistId backlogId) {
        backlogMapper.deleteById(backlogId.getRealId());
    }

}

package com.alpha.repository;

import com.alpha.repository.entity.Backlog;
import com.alpha.repository.entity.User;

import java.util.List;

public interface UserRepository {
    List<User> getAllUsers();

    void addUser(User user);

    User getUserByName(String name);

    User getUserByPersistId(PersistId id);

    void updateUserBacklogs(User user, Backlog backlog);
}

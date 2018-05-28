package com.alpha.repository.rdb.singleTable.dao;

import com.alpha.repository.entity.User;

import java.util.List;

public interface UserMapper {
    int insert(User user);

    User selectById(Integer id);

    List<User> selectAll();

    User selectByName(String name);
}
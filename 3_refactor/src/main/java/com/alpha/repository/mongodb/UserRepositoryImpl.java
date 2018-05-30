package com.alpha.repository.mongodb;

import com.alpha.repository.PersistId;
import com.alpha.repository.UserRepository;
import com.alpha.repository.entity.Backlog;
import com.alpha.repository.entity.User;
import com.alpha.repository.mongodb.dao.BacklogDAO;
import com.alpha.repository.mongodb.dao.UserDAO;
import com.alpha.repository.mongodb.dto.UserDTO;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Profile("mongodb")
public class UserRepositoryImpl implements UserRepository {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private BacklogDAO backlogDAO;

    @Override
    public List<User> getAllUsers() {
        List<UserDTO> user4DTOList = userDAO.getAll(UserDTO.class);
        List<User> users = new ArrayList<>();
        user4DTOList.forEach(
                user4DTO -> {
                    User user = mapUserDTO2User(user4DTO);
                    users.add(user);
                }
        );
        return users;
    }


    @Override
    public void addUser(User user) {
        UserDTO user4DTO = mapUser2UserDTO(user);
        PersistId id = userDAO.insert(user4DTO,"User");
        user.setId(id);
    }

    @Override
    public User getUserByName(String name) {
        UserDTO user4DTO = userDAO.getUser4DTOByName(name);
        User user = mapUserDTO2User(user4DTO);
        return user;
    }

    @Override
    public User getUserByPersistId(PersistId id) {
        UserDTO user4DTO = userDAO.getById(id, UserDTO.class);
        User user = mapUserDTO2User(user4DTO);
        user.setBacklogs(new ArrayList<>());
        user4DTO.getObjectIdsOfBacklog().forEach(
                backlogObjectId -> {
                    MongoID mongoID = new MongoID(backlogObjectId);
                    Backlog backlog = backlogDAO.getById(mongoID, Backlog.class);
                    user.getBacklogs().add(backlog);
                }
        );
        return user;
    }

    @Override
    public void addBacklog(PersistId userId, Backlog backlog) {
        PersistId backlogId = backlogDAO.insert(backlog,"Backlog");
        UserDTO userDTO = userDAO.getById(userId, UserDTO.class);


        List<ObjectId> objectIdsOfBacklog = userDTO.getObjectIdsOfBacklog();
        if (objectIdsOfBacklog == null) {
            objectIdsOfBacklog = new ArrayList<>();
            userDTO.setObjectIdsOfBacklog(objectIdsOfBacklog);
        }
        objectIdsOfBacklog.add(backlogId.getRealId());
        userDAO.update(userId, userDTO,"User");
    }

    @Override
    public void removeBacklog(PersistId userId, PersistId backlogId) {
        UserDTO userDTO = userDAO.getById(userId, UserDTO.class);
        userDTO.getObjectIdsOfBacklog().remove(backlogId.getRealId());
        userDAO.update(userId,userDTO,"User");
        backlogDAO.delete(backlogId);
    }

    private User mapUserDTO2User(UserDTO user4DTO) {
        User user = new User();
        user.setId(new MongoID(user4DTO.getId()));
        user.setName(user4DTO.getName());
        user.setPassword(user4DTO.getPassword());
        return user;
    }

    private UserDTO mapUser2UserDTO(User user) {
        UserDTO user4DTO = new UserDTO();
        user4DTO.setName(user.getName());
        user4DTO.setPassword(user.getPassword());
        user4DTO.setObjectIdsOfBacklog(null);
        return user4DTO;
    }


}

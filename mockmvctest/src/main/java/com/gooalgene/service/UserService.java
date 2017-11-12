package com.gooalgene.service;

import com.gooalgene.beans.User;
import com.gooalgene.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by crabime on 11/2/17.
 */
@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public List<User> findAllUsers(){
        return userDao.findAllUsers();
    }

    public User findUserByName(String name){
        return userDao.findUserByName(name);
    }

    public User findUserById(int id){
        return userDao.findUserbyId(id);
    }
}

package com.gooalgene.dao;

import com.gooalgene.annotation.MyBatisDao;
import com.gooalgene.beans.User;

import java.util.List;

/**
 * Created by crabime on 11/2/17.
 */
@MyBatisDao
public interface UserDao {
    List<User> findAllUsers();

    User findUserByName(String name);

    User findUserbyId(int id);
}

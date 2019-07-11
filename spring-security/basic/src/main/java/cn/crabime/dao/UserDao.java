package cn.crabime.dao;

import cn.crabime.beans.User;
import cn.crabime.service.MyBatisDao;
import org.springframework.security.access.annotation.Secured;

import java.util.List;

/**
 * Created by crabime on 5/30/18.
 */
@MyBatisDao
public interface UserDao {

    @Secured("ROLE_ADMIN")
    List<User> findUserById(int id);
}

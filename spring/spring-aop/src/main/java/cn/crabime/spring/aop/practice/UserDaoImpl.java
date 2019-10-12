package cn.crabime.spring.aop.practice;

import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {

    @Override
    public int addUser() {
        System.out.println("添加一个用户");
        return 0;
    }
}

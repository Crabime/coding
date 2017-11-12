package jstl.dao;

import jstl.beans.Configuration;
import jstl.mybatis.config.MyBatisDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by crabime on 10/31/17.
 */
@MyBatisDao
public interface ConfigurationDao {
    public List<Configuration> getAllConfig();
}

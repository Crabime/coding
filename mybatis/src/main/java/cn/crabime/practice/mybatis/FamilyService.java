package cn.crabime.practice.mybatis;

import cn.crabime.practice.mybatis.dao.FamilyInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FamilyService {

    @Autowired
    private FamilyInterface familyInterface;

    @Transactional
    public void insertFamily(Family family) {
        familyInterface.insertFamily(family);
        throw new RuntimeException("数据错误");
    }
}

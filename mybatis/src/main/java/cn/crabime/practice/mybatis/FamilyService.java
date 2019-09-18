package cn.crabime.practice.mybatis;

import cn.crabime.practice.mybatis.dao.FamilyInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FamilyService {

    @Autowired
    private FamilyInterface familyInterface;

    public void insertFamily(Family family) {
        familyInterface.insertFamily(family);
    }
}

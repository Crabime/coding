package cn.crabime.practice.annotation;

import cn.crabime.practice.annotation.dao.EducationInterface;
import cn.crabime.practice.mybatis.Education;
import cn.crabime.practice.mybatis.SimpleMapEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EducationService {

    @Autowired
    private EducationInterface educationInterface;

    @Transactional
    public void insertOneEducation(Education education) {
        educationInterface.insertEducation(education);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void insertOneEducationWithoutOwnTransaction(Education education) {
        educationInterface.insertEducation(education);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void insertOneEducationUsingMandatoryTransaction(Education education) {
        educationInterface.insertEducation(education);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void insertOneEducationUsingRequiresNewTransaction(Education education) {
        educationInterface.insertEducation(education);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void insertOneEducationUsingNotSupportTransaction(Education education) {
        educationInterface.insertEducation(education);
    }

    @Transactional(propagation = Propagation.NEVER)
    public void insertOneEducationNeverUsingTransaction(Education education) {
        educationInterface.insertEducation(education);
    }

    @Transactional(propagation = Propagation.NESTED)
    public void insertOneEducationUsingNestedTransaction(Education education) {
        educationInterface.insertEducation(education);
    }

    public void truncateEdu() {
        educationInterface.truncateEdu();
    }

    public List<SimpleMapEntry> getAllMappings() {
        return educationInterface.getAllMappings();
    }

    public List<Education> getByType(String type, int familyId) {
        return educationInterface.getByType(type, familyId);
    }

    public Education getByTypeAndUsername(String type, String username) {
        return educationInterface.getByTypeAndUsername(type, username);
    }
}

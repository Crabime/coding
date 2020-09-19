package cn.crabime.practice.annotation;

import cn.crabime.practice.annotation.dao.FamilyInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class FamilyService {

    @Autowired
    private FamilyInterface familyInterface;

    @Autowired
    private EducationService educationService;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Transactional
    public void insertFamily(Family family) {
        familyInterface.insertFamily(family);
        Education education = family.getEducation();
        if (education != null) {
            education.setFamilyId(family.getId());
            // 使用SUPPORTS传播特性，依附在当前事务上
            educationService.insertOneEducationUsingRequiresNewTransaction(education);
        }
        throw new RuntimeException("插入异常");
    }

    @Transactional
    public void insertFamilyWithNonEducationTransaction(Family family) {
        familyInterface.insertFamily(family);
        Education education = family.getEducation();
        if (education != null) {
            education.setFamilyId(family.getId());
            educationService.insertOneEducationWithoutOwnTransaction(education);
        }
    }

    // 都没有事务
    public void insertFamilyUsingSupportsPropagation(Family family) {
        familyInterface.insertFamily(family);
        Education education = family.getEducation();
        if (education != null) {
            education.setFamilyId(family.getId());
            // 当前无事务，但调用方法有事务，且事务传播特性为supports，相当于调用方法也没有事务
            educationService.insertOneEducationWithoutOwnTransaction(education);
        }
        throw new RuntimeException("执行异常");
    }

    // 调用educationService抛出异常，但familyService正常插入
    public void insertFamilyUsingMandatoryPropagation(Family family) {
        familyInterface.insertFamily(family);
        Education education = family.getEducation();
        if (education != null) {
            education.setFamilyId(family.getId());
            // 当前无事务，但调用方法有事务，且事务传播特性为Mandatory，这里调用会报错
            educationService.insertOneEducationUsingMandatoryTransaction(education);
        }
        throw new RuntimeException("执行异常");
    }

    // familyService插入异常，但educationService插入正常
    @Transactional
    public void insertFamilyUsingRequiredNewPropagation(Family family) {
        TransactionTemplate template = new TransactionTemplate(transactionManager);
        template.execute(ac -> {
            try {
                familyInterface.insertFamily(family);
            } catch (Exception e){
                ac.setRollbackOnly();
            }
            return null;
        });
        Education education = family.getEducation();
        if (education != null) {
             education.setFamilyId(family.getId());
            // 当前无事务，但调用方法有事务，且事务传播特性为requires_new，子方法中重新创建一个新事务，当前事务挂起
            educationService.insertOneEducationUsingRequiresNewTransaction(education);
        }
    }

    // familyService插入异常，但educationService插入正常
    @Transactional(propagation = Propagation.REQUIRED)
    public void insertFamilyWithoutIdGeneratorUsingRequiredNewPropagation(Family family) {
        familyInterface.insertFamilyWithoutIdReturn(family);
        Education education = family.getEducation();
        if (education != null) {
            // education.setFamilyId(family.getId());
            // 当前无事务，但调用方法有事务，且事务传播特性为requires_new，子方法中重新创建一个新事务，当前事务挂起
            educationService.insertOneEducationUsingRequiresNewTransaction(education);
        }

        throw new RuntimeException("执行异常");
    }

    // familyService插入异常，但educationService插入正常
    public void insertFamilyUsingNotSupportPropagation(Family family) {
        familyInterface.insertFamily(family);
        Education education = family.getEducation();
        if (education != null) {
            education.setFamilyId(family.getId());
            // 当前无事务，但调用方法有事务，且事务传播特性为requires_new，子方法中重新创建一个新事务，当前事务挂起
            educationService.insertOneEducationUsingNestedTransaction(education);
        }

        throw new RuntimeException("Nested异常");
    }

    public void truncateFamily() {
        familyInterface.truncateFamily();
        educationService.truncateEdu();
    }
}

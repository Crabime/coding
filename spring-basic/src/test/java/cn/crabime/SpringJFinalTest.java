package cn.crabime;

import jfinal.dao.AddressDao;
import jfinal.dao.MemberDao;
import jfinal.entity.Address;
import jfinal.entity.Member;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by crabime on 6/27/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy(@ContextConfiguration(locations = "classpath:beans.xml"))
public class SpringJFinalTest {
    @Autowired
    @Qualifier(value = "memberDao")
    private MemberDao memberModelDao;
    @Autowired
    @Qualifier(value = "memberRecordDao")
    private MemberDao memberRecordDao;
    @Autowired
    private AddressDao addressDao;
    public static Log log = LogFactory.getLog(SpringJFinalTest.class);

    @Test
    public void testAddOneMember(){
        Member member = new Member();
        member.setName("Crabime");
        Boolean result = memberModelDao.addOneMember(member);
        if (result){
            System.out.println("insert successfully");
        }
    }

    /**
     * 通过Db+Record形式直接操作数据表
     */
    @Test
    public void testAddOneMemberThroughRecord(){
        Member member = new Member();
        member.setName("cap tower");
        Boolean result = memberRecordDao.addOneMember(member);
        if (result){
            System.out.println("insert value through record successfully");
        }
    }

    /**
     * 测试jfinal model findById方法
     */
    @Test
    public void testFindById(){
        Member member = memberModelDao.findById(2L);
        System.out.println("当前member的名字为:" + member.getName());
    }

    /**
     * 使用Db+Record形式进行查找操作
     */
    @Test
    public void testFindByIdThroughDb(){
        Member member = memberRecordDao.findById(2L);
        System.out.println(member.getName());
    }

    /**
     * 测试jfinal的findFirst方法
     */
    @Test
    public void testGetFirstOne(){
        Member member = memberModelDao.findFirstOne("facebook");
        System.out.println("member id:" + member.getId() + " , name:" + member.getName());
    }

    /**
     * 测试使用Db.deleteById方式删除记录
     * 加入事务管理
     */
    @Test
    public void testDeleteByIdThroughDb(){
        Boolean result = memberRecordDao.deleteById(9L);
        if (result){
            log.info("删除id为9的record成功");
        }
    }

    /**
     * 测试jfinal的deleteById方法
     */
    @Test
    public void testDeleteById(){
        Boolean result = memberModelDao.deleteById(11L);
        if (result){
            System.out.println("delete successfully");
        } else{
            System.out.println("delete failed");
        }
    }

    /**
     * 测试jfinal中sql template
     */
    @Test
    public void testDeleteByName(){
        Boolean result = memberModelDao.deleteByName("hello");
        if (result){
            System.out.println("delete successfully");
        }else {
            System.out.println("delete false");
        }
    }

    @Test
    public void testDeleteAddressByMemberName(){
        Boolean result = memberRecordDao.deleteAddressByMemberName("袁");
        if (result){
            System.out.println("删除袁所对应的地址成功");
        }
    }

    /**
     * 使用Db+Record形式进行分页查询
     */
    @Test
    public void testPaginateThroughRecord(){
        List<Member> members = memberRecordDao.findByPage(2, 3, false, "select * ", "from member", new Object[]{});
        for(Member member : members){
            System.out.println("member [id : " + member.getId() + ", name : " + member.getName() + "]");
        }
    }

    /**
     * 使用Model形式进行分页查询
     */
    @Test
    public void testPaginate(){
        Long start = System.currentTimeMillis();
        List<Member> members = memberModelDao.findByPage(2, 3, false, "select * ", "from member", new Object[]{});
        for(Member member : members){
            System.out.println("member [id : " + member.getId() + ", name : " + member.getName() + "]");
        }
        System.out.println("complete duration : " + (System.currentTimeMillis() - start));
    }

    /**
     * 使用缓存的方式进行分页查询,多次执行与上面的单元测试进行比较
     */
    @Test
    public void testPaginateByCache(){
        Long start = System.currentTimeMillis();
        List<Member> members = memberRecordDao.findByPageUsingCache(2, 3, false, "select * ", "from member", new Object[]{});
        for(Member member : members){
            System.out.println("member [id : " + member.getId() + ", name : " + member.getName() + "]");
        }
        System.out.println("complete duration : " + (System.currentTimeMillis() - start));
    }

    /**
     * 数据库中已经有Texia这个记录,但是我设置程序删除成功则抛出异常,看程序是否会回滚已经删除的数据
     * 结果为:不回滚
     */
    @Test
    public void testDataRollback(){
        Boolean result = memberRecordDao.deleteByName("Texia");
        if (result){
            System.out.println("查看数据是否回滚");
        }
    }

    @Test
    public void testDataRollbackUsingJFinal(){
        Boolean result = memberModelDao.deleteByName("张三");
        if (result){
            System.out.println("查看数据是否回滚");
        }
    }

    @Test
    public void testFindByCache(){
        long cacheStart = System.currentTimeMillis();
        Member member = memberRecordDao.findEntityInCache(5L);
        System.out.println(member);
        System.out.println((System.currentTimeMillis() - cacheStart));
    }

    @Test
    public void testFindWithoutCache(){
        long cacheStart = System.currentTimeMillis();
        Member member = memberRecordDao.findById(5L);
        System.out.println(member);
        System.out.println((System.currentTimeMillis() - cacheStart));
    }

    @Test
    public void addAddress(){
        Address address = new Address();
        address.setDes("San Diego");
        address.setMember_id(11L);
        boolean result = addressDao.addAddress(address);
        if (result){
            System.out.println("保存成功:" + address.toString());
        }
    }

    /**
     * 测试使用jfinal进行连表查询,在member表中获取所有指定id下的所有address对象
     */
    @Test
    public void testFindAllAddress(){
        List<Address> allAddress = memberModelDao.getAllAddress(11L);
        for (Address address : allAddress){
            System.out.println(address.toString());
        }
    }
}

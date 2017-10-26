package jfinal.dao.record;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.*;
import jfinal.dao.MemberDao;
import jfinal.entity.Address;
import jfinal.entity.Member;
import jfinal.tools.BaseBean;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by crabime on 7/2/17.
 */
@Repository(value = "memberRecordDao")
public class MemberRecord implements MemberDao {

    @Override
    public Boolean addOneMember(Member member) {
        Record m = new Record().set("name", member.getName());
        return Db.save("member", m);
    }

    @Override
    public Member findById(Long id) {
        Member m = new Member();
        Record member = Db.findById("member", 10);
        new BaseBean<Member, Record>().convertRecordToBean(member, m);
        return m;
    }

    @Override
    public Member findEntityInCache(Long id){
        Member m = new Member();
        Record record = Db.findFirstByCache("testCache", "member", "select * from member where id = ?", id);
        new BaseBean<Member, Record>().convertRecordToBean(record, m);
        return m;
    }

    @Override
    public List<Address> getAllAddress(Long id) {
        return null;
    }

    @Override
    public Member findFirstOne(String name) {
        return null;
    }

    /**
     * 使用Db.tx(IAtom)方式进行数据库操作事务管理
     */
    @Override
    public Boolean deleteById(Long id) {
        Boolean result = Db.tx(new IAtom() {
            @Override
            public boolean run() throws SQLException {
                return Db.deleteById("member", id);
            }
        });
        return result;
    }

    @Override
    public Boolean deleteAddressByMemberName(String memberName) {
        Kv kv = Kv.by("name", memberName);
        //这里先拿到sqlPara,然后赋值
        SqlPara sqlPara = Db.getSqlPara("deleteAddressByMemberName");
        int update = Db.update(sqlPara.getSql(), kv);
        if (update == 0){
            return false;
        }else {
            return true;
        }
    }

    public Boolean deleteByName(String name){
        boolean result = false;
        Record record = Db.findFirst("select * from member where name = ?", name);
        if (null != record){
            result = Db.delete("member", record);
        }
        return result;
    }

    @Override
    public List<Member> findByPage(int pageNumber, int pageSize, boolean isGroupBySql, String select, String sqlExceptSelect, Object... paras) {
        Page<Record> pages = Db.paginate(pageNumber, pageSize, isGroupBySql, select, sqlExceptSelect, paras);
        List<Record> recordList = pages.getList();
        List<Member> memberList = new ArrayList<>();
        Member member = null;
        for (Record record : recordList){
            member = new Member();
            new BaseBean<Member, Record>().convertRecordToBean(record, member);
            memberList.add(member);
        }
        return memberList;
    }

    @Override
    public List<Member> findByPageUsingCache(int pageNumber, int pageSize, boolean isGroupBySql, String select, String sqlExceptSelect, Object... paras) {
        List<Member> memberList = new ArrayList<>();
        Page<Record> page = Db.paginateByCache("testCache", "memberList", pageNumber, pageSize, select, sqlExceptSelect, paras);
        List<Record> recordList = page.getList();
        Member member = null;
        for (Record record : recordList){
            member = new Member();
            new BaseBean<Member, Record>().convertRecordToBean(record, member);
            memberList.add(member);
        }
        return memberList;
    }


}

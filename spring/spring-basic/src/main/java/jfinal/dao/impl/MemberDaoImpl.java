package jfinal.dao.impl;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;
import jfinal.JFinalTableAnnotation;
import jfinal.dao.MemberDao;
import jfinal.entity.Address;
import jfinal.entity.Member;
import jfinal.tools.BaseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by crabime on 6/30/17.
 */
@Repository(value = "memberDao")
@JFinalTableAnnotation(dataSourceName = "druidDataSource", tableName = "member", primaryColumn = "id")
public class MemberDaoImpl extends Model<MemberDaoImpl> implements MemberDao {
    @Autowired
    private AddressDaoImpl model;
    @Override
    public Boolean addOneMember(Member member) {
        MemberDaoImpl model = new MemberDaoImpl();
        model.set("name", member.getName());
        boolean result = model.save();
        Integer id = model.get("id");
        System.out.println("存储后ID值为:" + id);
        return result;
    }

    @Override
    public Member findById(Long id){
        Member member = new Member();
        MemberDaoImpl resultModel = this.findById(new Object[]{id});
        new BaseBean<Member, MemberDaoImpl>().convertModelToBean(resultModel, member);
        return member;
    }

    @Override
    public Member findFirstOne(String searchName) {
        Member member = new Member();
        MemberDaoImpl model = this.findFirst("select * from member where name = ?", searchName);
        //java.lang.ClassCastException: java.lang.Integer cannot be cast to java.lang.Long
        //存进去的是Long类型,为什么返回的必须是Integer类型?
        //因为mysql中id字段为int类型,那么返回的肯定是Integer类型,只有将mysql中id字段改为signed bigint 类型,程序返回的才是Long类型
//        Long id = model.getLong("id");
//        String name = model.getStr("name");
//        member.setId(id);
//        member.setName(name);
        new BaseBean<Member, MemberDaoImpl>().convertModelToBean(model, member);
        return member;
    }

    @Override
    public Boolean deleteById(Long id) {
        return this.deleteById(new Object[]{id});
    }

    @Override
    public Boolean deleteAddressByMemberName(String memberName) {
        return null;
    }

    @Before(Tx.class)
    @Override
    public Boolean deleteByName(String name) {
        MemberDaoImpl model = this.findFirst(Db.getSql("findMemberByName"), name);
        return model.delete();
    }

    @Override
    public Member findEntityInCache(Long id) {
        return null;
    }

    @Override
    public List<Address> getAllAddress(Long id) {
        List<Address> addressList = new ArrayList<>();
        List<AddressDaoImpl> list = model.find(getSql("findAllAddress"), id);
        Address address = null;
        for (AddressDaoImpl addressDaoImpl : list){
            address = new Address();
            new BaseBean<AddressDaoImpl, Address>().convertModelToBean(addressDaoImpl, address);
            addressList.add(address);
        }
        return addressList;
    }

    @Override
    public List<Member> findByPage(int pageNumber, int pageSize, boolean isGroupBySql, String select, String sqlExceptSelect, Object... paras) {
        List<Member> memberList = new ArrayList<>();
        Page<MemberDaoImpl> page = this.paginate(pageNumber, pageSize, isGroupBySql, select, sqlExceptSelect, paras);
        List<MemberDaoImpl> list = page.getList();
        Member member = null;
        for (MemberDaoImpl model : list){
            member = new Member();
            new BaseBean<Member, MemberDaoImpl>().convertModelToBean(model, member);
            memberList.add(member);
        }
        return memberList;
    }

    @Override
    public List<Member> findByPageUsingCache(int pageNumber, int pageSize, boolean isGroupBySql, String select, String sqlExceptSelect, Object... paras) {
        return null;
    }


}

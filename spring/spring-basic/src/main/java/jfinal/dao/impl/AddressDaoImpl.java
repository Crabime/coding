package jfinal.dao.impl;

import com.jfinal.plugin.activerecord.Model;
import jfinal.JFinalTableAnnotation;
import jfinal.dao.AddressDao;
import jfinal.dao.MemberDao;
import jfinal.entity.Address;
import jfinal.entity.Member;
import jfinal.tools.BaseBean;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * Created by crabime on 7/7/17.
 */
@Repository
@JFinalTableAnnotation(dataSourceName = "druidDataSource", tableName = "address", primaryColumn = "id")
public class AddressDaoImpl extends Model<AddressDaoImpl> implements AddressDao {
    @Resource(name = "memberDao")
    private MemberDao memberDao;
    @Override
    public boolean addAddress(Address address) {
        Long memberId = address.getMember_id();
        Member memberResult = memberDao.findById(memberId);
        if (null == memberResult){
            throw new RuntimeException("该address没有对应的member");
        }
        AddressDaoImpl model = new AddressDaoImpl();
        model.set("des", address.getDes());
        model.set("member_id", address.getMember_id());
        new BaseBean<Address, AddressDaoImpl>().convertBeanToModel(address, model);
        return model.save();
    }
}

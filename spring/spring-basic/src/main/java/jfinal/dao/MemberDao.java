package jfinal.dao;

import jfinal.entity.Address;
import jfinal.entity.Member;

import java.util.List;

/**
 * Created by crabime on 6/30/17.
 */
public interface MemberDao {
    Boolean addOneMember(Member member);

    Member findById(Long id);

    Member findFirstOne(String name);

    Boolean deleteById(Long id);

    Boolean deleteAddressByMemberName(String name);

    Boolean deleteByName(String name);

    Member findEntityInCache(Long id);

    List<Address> getAllAddress(Long id);

    List<Member> findByPage(int pageNumber, int pageSize, boolean isGroupBySql, String select, String sqlExceptSelect, Object... paras);

    List<Member> findByPageUsingCache(int pageNumber, int pageSize, boolean isGroupBySql, String select, String sqlExceptSelect, Object... paras);

}

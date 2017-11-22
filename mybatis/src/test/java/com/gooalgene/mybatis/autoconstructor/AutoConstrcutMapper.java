package com.gooalgene.mybatis.autoconstructor;

import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by crabime on 11/13/17.
 */
public interface AutoConstrcutMapper {

    @Select("select * from subject where id = #{id}")
    PrimitiveSubject getSubject(final int id);

    @Select("select * from subject")
    List<PrimitiveSubject> getSubjects();

    @Select("select * from subject")
    List<WrapperSubject> getWrapperSubjects();

    @Select("select * from subject")
    List<AnnotatedSubject> getAnnotatedSubjects();

    @Select("select * from subject")
    List<BadSubject> getBadSubjects();
}

package cn.crabime.redis.rwsep.service;

import cn.crabime.redis.rwsep.beans.PGene;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PGeneService {

    List<PGene> findGeneBySpecies(@Param("species") String species, @Param("pageNo") int pageNo, @Param("pageSize") int pageSize);
}

package com.zslin.bus.dao;

import com.zslin.basic.repository.BaseRepository;
import com.zslin.bus.model.Dictionary;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by zsl on 2019/4/8.
 */
public interface IDictionaryDao extends BaseRepository<Dictionary, Integer>, JpaSpecificationExecutor<Dictionary> {

    @Query("FROM Dictionary WHERE pid IS NULL")
    List<Dictionary> findParent();

    List<Dictionary> findByPid(Integer pid);

    List<Dictionary> findByPcode(String pcode);

    Dictionary findByPidAndCode(Integer pid, String code);

    Dictionary findByPcodeAndCode(String pcode, String code);

    @Query("FROM Dictionary WHERE code=?1 AND pid IS NULL")
    Dictionary findParentByCode(String code);
}

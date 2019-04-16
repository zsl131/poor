package com.zslin.bus.dao;

import com.zslin.basic.repository.BaseRepository;
import com.zslin.bus.model.Personal;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zsl on 2019/4/15.
 */
public interface IPersonalDao extends BaseRepository<Personal, Integer>, JpaSpecificationExecutor<Personal> {

    /** 修改年龄 */
    @Query("UPDATE Personal p SET p.nl=?2 WHERE p.sfzh=?1")
    @Modifying
    @Transactional
    void updateNl(String sfzh, Integer nl);

    @Query("FROM Personal p WHERE p.id>?1 ")
    List<Personal> findById(Integer id, Sort sort);
}

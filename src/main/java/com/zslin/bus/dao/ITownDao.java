package com.zslin.bus.dao;

import com.zslin.basic.repository.BaseRepository;
import com.zslin.bus.model.Town;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by zsl on 2019/4/5.
 */
public interface ITownDao extends BaseRepository<Town, Integer>, JpaSpecificationExecutor<Town> {

    @Query("FROM Town t WHERE t.pid IS NULL")
    List<Town> findParent(Sort sort);

    List<Town> findByPid(Integer pid, Sort sort);

    Town findByName(String name);

    @Query("SELECT t FROM Town t, UserTown u WHERE u.townId=t.id AND u.username=?1")
    List<Town> findByUsername(String username, Sort sort);

    /** 即将删除 */
    @Query("FROM Town t WHERE t.name LIKE CONCAT('%',?1,'%')")
    Town findByNameLike(String name);

    /** 查询乡镇 */
    @Query("FROM Town t WHERE t.pid IS NULL AND t.name LIKE CONCAT('%',?1,'%')")
    Town findParentByNameLike(String name);

    /** 查询村 */
    @Query("FROM Town t WHERE t.pid IS NOT NULL AND t.name LIKE CONCAT('%',?1,'%')")
    Town findCunByNameLike(String name);

    /** 获取所有村 */
    @Query("FROM Town t WHERE t.pid IS NOT NULL")
    List<Town> findAllCun();
}

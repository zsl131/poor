package com.zslin.bus.dao;

import com.zslin.basic.repository.BaseRepository;
import com.zslin.bus.model.Town;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by zsl on 2019/4/5.
 */
public interface ITownDao extends BaseRepository<Town, Integer>, JpaSpecificationExecutor<Town> {

    Town findByName(String name);

    @Query("SELECT t FROM Town t, UserTown u WHERE u.townId=t.id AND u.username=?1")
    List<Town> findByUsername(String username);
}

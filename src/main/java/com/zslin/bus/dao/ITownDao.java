package com.zslin.bus.dao;

import com.zslin.basic.repository.BaseRepository;
import com.zslin.bus.model.Town;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by zsl on 2019/4/5.
 */
public interface ITownDao extends BaseRepository<Town, Integer>, JpaSpecificationExecutor<Town> {

    Town findByName(String name);
}

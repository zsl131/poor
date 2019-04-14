package com.zslin.bus.dao;

import com.zslin.basic.repository.BaseRepository;
import com.zslin.bus.model.Personal;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by zsl on 2019/4/15.
 */
public interface IPersonalDao extends BaseRepository<Personal, Integer>, JpaSpecificationExecutor<Personal> {
}

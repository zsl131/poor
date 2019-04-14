package com.zslin.bus.dao;

import com.zslin.basic.repository.BaseRepository;
import com.zslin.bus.model.HelperPersonal;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by zsl on 2019/4/15.
 */
public interface IHelperPersonalDao extends BaseRepository<HelperPersonal, Integer>, JpaSpecificationExecutor<HelperPersonal> {
}

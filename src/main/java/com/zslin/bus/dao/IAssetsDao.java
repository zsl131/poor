package com.zslin.bus.dao;

import com.zslin.basic.repository.BaseRepository;
import com.zslin.bus.model.Assets;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by zsl on 2019/4/15.
 */
public interface IAssetsDao extends BaseRepository<Assets, Integer>, JpaSpecificationExecutor<Assets> {
}

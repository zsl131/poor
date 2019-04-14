package com.zslin.bus.dao;

import com.zslin.basic.repository.BaseRepository;
import com.zslin.bus.model.Family;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by zsl on 2019/4/15.
 */
public interface IFamilyDao extends BaseRepository<Family, Integer>, JpaSpecificationExecutor<Family> {
}

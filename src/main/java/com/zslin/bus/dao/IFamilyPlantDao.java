package com.zslin.bus.dao;

import com.zslin.basic.repository.BaseRepository;
import com.zslin.bus.model.FamilyPlant;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by zsl on 2019/5/14.
 */
public interface IFamilyPlantDao extends BaseRepository<FamilyPlant, Integer>, JpaSpecificationExecutor<FamilyPlant> {

    List<FamilyPlant> findByHzsfzh(String hzsfzh);

    FamilyPlant findByHzsfzhAndZzpzdm(String hzsfzh, String zzpzdm);
}

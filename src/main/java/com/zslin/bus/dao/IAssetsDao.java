package com.zslin.bus.dao;

import com.zslin.basic.repository.BaseRepository;
import com.zslin.bus.model.Assets;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by zsl on 2019/4/15.
 */
public interface IAssetsDao extends BaseRepository<Assets, Integer>, JpaSpecificationExecutor<Assets> {

    List<Assets> findByGssfzh(String gssfzh);

    List<Assets> findByGsid(Integer gsid);

    List<Assets> findByHzid(Integer hzid);

    List<Assets> findByHzsfzh(String hzsfzh);

    List<Assets> findByGsidAndMc(Integer gsid, String mc);

    List<Assets> findByHzidAndMc(Integer hzid, String mc);
}

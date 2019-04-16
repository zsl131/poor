package com.zslin.bus.common.idao;

import com.zslin.basic.repository.BaseRepository;
import com.zslin.bus.common.model.Record;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by zsl on 2019/4/16.
 */
public interface IRecordDao extends BaseRepository<Record, Integer>, JpaSpecificationExecutor<Record> {
}

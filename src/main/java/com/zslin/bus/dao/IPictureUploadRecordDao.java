package com.zslin.bus.dao;

import com.zslin.basic.repository.BaseRepository;
import com.zslin.bus.model.PictureUploadRecord;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zsl on 2019/4/26.
 */
public interface IPictureUploadRecordDao extends BaseRepository<PictureUploadRecord, Integer>, JpaSpecificationExecutor<PictureUploadRecord> {

    List<PictureUploadRecord> findByBatchNo(String batchNo);

    @Modifying
    @Transactional
    void deleteByBatchNo(String batchNo);
}

package com.zslin.bus.dao;

import com.zslin.basic.repository.BaseRepository;
import com.zslin.bus.model.PictureUpload;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zsl on 2019/4/26.
 */
public interface IPictureUploadDao extends BaseRepository<PictureUpload, Integer>, JpaSpecificationExecutor<PictureUpload> {

    PictureUpload findByBatchNo(String batchNo);

    @Modifying
    @Transactional
    void deleteByBatchNo(String batchNo);
}

package com.zslin.bus.service;

import com.zslin.basic.annotations.AdminAuth;
import com.zslin.basic.repository.SimplePageBuilder;
import com.zslin.basic.repository.SimpleSortBuilder;
import com.zslin.bus.common.dto.QueryListDto;
import com.zslin.bus.common.tools.JsonTools;
import com.zslin.bus.common.tools.QueryTools;
import com.zslin.bus.dao.IPictureUploadDao;
import com.zslin.bus.dao.IPictureUploadRecordDao;
import com.zslin.bus.dao.ITownDao;
import com.zslin.bus.model.PictureUpload;
import com.zslin.bus.model.PictureUploadRecord;
import com.zslin.bus.tools.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zsl on 2019/4/25.
 */
@Service
@AdminAuth(psn = "业务管理", name = "数据上传", orderNum = 1, type = "1", url = "/admin/pictureUpload")
public class PictureUploadService {

    @Autowired
    private IPictureUploadDao pictureUploadDao;

    @Autowired
    private IPictureUploadRecordDao pictureUploadRecordDao;

    /** 列表资产信息 */
    public JsonResult list(String params) {
        QueryListDto qld = QueryTools.buildQueryListDto(params);
        Page<PictureUpload> res = pictureUploadDao.findAll(QueryTools.getInstance().buildSearch(qld.getConditionDtoList()),
                SimplePageBuilder.generate(qld.getPage(), qld.getSize(), SimpleSortBuilder.generateSort(qld.getSort())));
        return JsonResult.success().set("size", (int)res.getTotalElements()).set("data", res.getContent());
    }

    public JsonResult delete(String params) {
        try {
            String batchNo = JsonTools.getJsonParam(params, "batchNo");
            pictureUploadDao.deleteByBatchNo(batchNo);
            pictureUploadRecordDao.deleteByBatchNo(batchNo);
            return JsonResult.success("删除成功");
        } catch (Exception e) {
            return JsonResult.error("删除失败："+e.getMessage());
        }
    }

    /** 获取一个批次的数据 */
    public JsonResult loadOne(String params) {
        String batchNo = JsonTools.getJsonParam(params, "batchNo");
        PictureUpload pu = pictureUploadDao.findByBatchNo(batchNo);
        List<PictureUploadRecord> list = pictureUploadRecordDao.findByBatchNo(batchNo);
        return JsonResult.success("获取成功").set("pictureUpload", pu).set("record", list);
    }
}

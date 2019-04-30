package com.zslin.bus.dao;

import com.zslin.basic.repository.BaseRepository;
import com.zslin.bus.model.Family;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zsl on 2019/4/15.
 */
public interface IFamilyDao extends BaseRepository<Family, Integer>, JpaSpecificationExecutor<Family> {

    Family findBySfzh(String sfzh);

    @Query("UPDATE Family f SET f.zjd=?1, f.ld=?2, f.gd=?3, f.zzpz=?4, f.zzdmj=?5 WHERE f.sfzh=?6")
    @Modifying
    @Transactional
    void updateIndustry(Float zjd, Float ld, Float gd, String zzpz, Float zzdmj, String sfzh);

    /** 获取所有户数 */
    @Query("SELECT COUNT(f.id) FROM Family f ")
    Integer findAllCount();

    /** 获取所有户数 */
    @Query("SELECT COUNT(f.id) FROM Family f WHERE f.lx=?1 ")
    Integer findAllCount(String lx);

    @Query("UPDATE Family f SET f.ldlrs=?1,f.jyrs=?2 WHERE f.sfzh=?3")
    @Modifying
    @Transactional
    void updateCount(Integer ldlrs, Integer jyrs, String hzsfzh);
}

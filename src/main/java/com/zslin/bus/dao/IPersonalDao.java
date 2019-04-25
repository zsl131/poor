package com.zslin.bus.dao;

import com.zslin.basic.repository.BaseRepository;
import com.zslin.bus.dto.PieDto;
import com.zslin.bus.model.Personal;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zsl on 2019/4/15.
 */
public interface IPersonalDao extends BaseRepository<Personal, Integer>, JpaSpecificationExecutor<Personal> {

    /** 修改年龄 */
    @Query("UPDATE Personal p SET p.nl=?2 WHERE p.sfzh=?1")
    @Modifying
    @Transactional
    void updateNl(String sfzh, Integer nl);

    Personal findBySfzh(String sfzh);

    @Query("FROM Personal p WHERE p.id>?1 ")
    List<Personal> findById(Integer id, Sort sort);

    List<Personal> findByHzsfzh(String hzsfzh);

    /** 修改性别 */
    @Query("UPDATE Personal p SET p.xb=?2 WHERE p.sfzh=?1")
    @Modifying
    @Transactional
    void updateXb(String sfzh, String xb);

    @Query("UPDATE Personal p SET p.jylx=?1 WHERE p.sfzh=?2")
    @Modifying
    @Transactional
    void updateJylx(String jylx, String sfzh);

    @Query("SELECT new com.zslin.bus.dto.PieDto(p.xb, COUNT(p.id)) FROM Personal p GROUP BY p.xb")
    List<PieDto> findPieByXb();

    @Query("SELECT new com.zslin.bus.dto.PieDto(p.sfsldl, COUNT(p.id)) FROM Personal p GROUP BY p.sfsldl")
    List<PieDto> findPieByLdl();

    @Query("SELECT new com.zslin.bus.dto.PieDto(p.mz, COUNT(p.id)) FROM Personal p GROUP BY p.mz")
    List<PieDto> findPieByMz();

    @Query("SELECT new com.zslin.bus.dto.PieDto(p.pksx, COUNT(p.id)) FROM Personal p GROUP BY p.pksx")
    List<PieDto> findPieByPksx();

    @Query("SELECT new com.zslin.bus.dto.PieDto(p.lx, COUNT(p.id)) FROM Personal p GROUP BY p.lx")
    List<PieDto> findPieByLx();

    /** 是否医保 */
    @Query("SELECT new com.zslin.bus.dto.PieDto(p.sfyb, COUNT(p.id)) FROM Personal p GROUP BY p.sfyb")
    List<PieDto> findPieBySfyb();

    /** 参保险种 */
    @Query("SELECT new com.zslin.bus.dto.PieDto(p.cbxz, COUNT(p.id)) FROM Personal p GROUP BY p.cbxz")
    List<PieDto> findPieByCbxz();

    /** 是否患病 */
    @Query("SELECT new com.zslin.bus.dto.PieDto(p.sfhb, COUNT(p.id)) FROM Personal p GROUP BY p.sfhb")
    List<PieDto> findPieBySfhb();

    /** 就业类型 */
    @Query("SELECT new com.zslin.bus.dto.PieDto(p.jylx, COUNT(p.id)) FROM Personal p GROUP BY p.jylx")
    List<PieDto> findPieByJylx();

    /** 就学情况 */
    @Query("UPDATE Personal p SET p.sfzx=?1,p.jyjd=?2,p.jdxx=?3,p.jdnj=?4,p.sfxszz=?5,p.zzje=?6,p.zzxm=?7,p.zzxmmc=?8 WHERE p.sfzh=?9")
    @Modifying
    @Transactional
    Integer updateJxqk(String sfzx, String jyjd, String jdxx, String jdnj, String sfxszz, Float zzje, String zzxm, String zzxmmc, String sfzh);

    /** 医保情况 */
    @Query("UPDATE Personal p SET p.sfyb=?1, p.cbxz=?2, p.cbdw=?3, p.sfhb=?4 WHERE p.sfzh=?5")
    @Modifying
    @Transactional
    Integer updateYbj(String sfyb, String cbxz, String cbdw, String sfhb, String sfzh);

    @Query("UPDATE Personal p SET p.cjhzpx=?1, p.wgdd=?2, p.qymc=?3, p.gwmc=?4, p.wgsj=?5, p.ygz=?6, p.cyxm=?7, p.cydd=?8, p.cysj=?9, p.ysr=?10, p.wgqx=?11, p.gyxgw=?12, p.zzcy=?13, p.wfwcyy=?14, p.pxxq=?15, p.lxdh=?16," +
            "p.sfyb=?17, p.cbxz=?18, p.cbdw=?19, p.sfhb=?20 WHERE p.sfzh=?21")
    @Modifying
    @Transactional
    Integer updateYbj(String cjhzpx, String wgdd, String qymc, String gwmc, String wgsj, Float ygz, String cyxm, String cydd, String cysj, Float ysr,
                      String wgqx, String gyxgw, String zzcy, String wfwcyy, String pxxq, String lxdh,
                      String sfyb, String cbxz, String cbdw, String sfhb, String sfzh);

    @Query("UPDATE Personal p SET p.lxdh=?1 WHERE p.sfzh=?2")
    @Modifying
    @Transactional
    void updateLxdh(String lxdh, String sfzh);
}

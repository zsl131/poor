package com.zslin.basic.dao;

import com.zslin.basic.model.AppConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by zsl-pc on 2016/9/7.
 */
public interface IAppConfigDao extends JpaRepository<AppConfig, Integer> {

    @Query("FROM AppConfig ")
    AppConfig loadOne();

    @Override
    <S extends AppConfig> S save(S s);
}

package com.zslin.basic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 钟述林 393156105@qq.com on 2017/1/6 14:44.
 */
@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

    List<T> listBySql(String sql, Object... args);

    List<T> listByHql(String hql, Object... args);

    /*Page<T> listByHql(String hql, Pageable pageable, Object... args);*/

    @Modifying
    @Transactional
    void updateBySql(String sql, Object... args);

    @Modifying
    @Transactional
    void updateByHql(String hql, Object... args);

    Object queryByHql(String hql, Object... args);
}

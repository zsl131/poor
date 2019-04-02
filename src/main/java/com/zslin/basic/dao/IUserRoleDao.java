package com.zslin.basic.dao;

import com.zslin.basic.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zsl-pc on 2016/9/1.
 */
public interface IUserRoleDao extends JpaRepository<UserRole, Integer> {

    @Query("SELECT ur.rid FROM UserRole ur WHERE ur.uid=:userId")
    List<Integer> queryRoleIds(@Param("userId") Integer userId);

    UserRole findByUidAndRid(Integer uid, Integer rid);

    @Transactional
    @Modifying
    @Query("DELETE UserRole ur WHERE ur.uid=?1")
    void deleteByUserId(Integer userId);
}

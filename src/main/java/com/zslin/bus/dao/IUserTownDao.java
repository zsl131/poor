package com.zslin.bus.dao;

import com.zslin.basic.repository.BaseRepository;
import com.zslin.bus.model.UserTown;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by zsl on 2019/4/11.
 */
public interface IUserTownDao extends BaseRepository<UserTown, Integer>, JpaSpecificationExecutor<UserTown> {

    @Query("SELECT u.townId FROM UserTown u WHERE u.username=?1")
    List<Integer> findTownId(String username);

    @Query("SELECT u.townId FROM UserTown u WHERE u.userId=?1")
    List<Integer> findTownId(Integer userId);

    @Query("SELECT t.level FROM Town t, UserTown u WHERE u.townId=t.id AND u.username=?1")
    String findLevelByUser(String username);

    @Query("SELECT t.level FROM Town t, UserTown u WHERE u.townId=t.id AND u.userId=?1")
    String findLevelByUser(Integer userId);

    UserTown findByUserIdAndTownId(Integer userId, Integer townId);
}

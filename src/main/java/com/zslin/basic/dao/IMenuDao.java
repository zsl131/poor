package com.zslin.basic.dao;

import com.zslin.basic.model.Menu;
import com.zslin.basic.repository.BaseRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by zsl-pc on 2016/9/1.
 */
public interface IMenuDao extends BaseRepository<Menu, Integer>, JpaSpecificationExecutor<Menu> {

//    @Query("SELECT m FROM Menu m WHERE m.display=1 AND m.type='2' AND m.id in (SELECT rm.mid FROM RoleMenu rm WHERE rm.rid IN (SELECT ur.rid FROM UserRole ur where ur.uid=?1))")
    //正面少了 AND m.type='2' 多了 AND m.href!='#'
    @Query("SELECT m FROM Menu m WHERE m.display=1 AND m.href!='#' AND m.id in (SELECT rm.mid FROM RoleMenu rm WHERE rm.rid IN (SELECT ur.rid FROM UserRole ur where ur.uid=?1))")
    List<Menu> findAuthMenuByUser(Integer userId);

    @Query("SELECT m FROM Menu m WHERE m.display=1 AND m.type='1' AND m.id in (SELECT rm.mid FROM RoleMenu rm WHERE rm.rid IN (SELECT ur.rid FROM UserRole ur where ur.uid=?1))")
    List<Menu> findByUser(Integer userId);

    @Query("SELECT m FROM Menu m WHERE m.display=1 AND m.type='1' AND m.psn=?2 AND m.id in (SELECT rm.mid FROM RoleMenu rm WHERE rm.rid IN (SELECT ur.rid FROM UserRole ur where ur.uid=?1)) ")
    List<Menu> findByUser(Integer userId, String psn, Sort sort);

    @Query("SELECT m FROM Menu m WHERE m.display=1 AND m.type='1' AND m.psn IS NULL AND m.id in (SELECT rm.mid FROM RoleMenu rm WHERE rm.rid IN (SELECT ur.rid FROM UserRole ur where ur.uid=?1)) ")
    List<Menu> findRootByUser(Integer userId, Sort sort);

    @Query("FROM Menu m WHERE m.display=1 AND m.pid IS NULL")
    List<Menu> findRootMenu(Sort sort);

    @Query("FROM Menu m WHERE m.display=1 AND m.pid=?1")
    List<Menu> findByParent(Integer pid, Sort sort);

    Menu findBySn(String sn);

    @Query("SELECT m.sn FROM Menu m WHERE m.display=1 AND m.id in (SELECT rm.mid FROM RoleMenu rm WHERE rm.rid IN (SELECT ur.rid FROM UserRole ur where ur.uid=?1))")
    List<String> listAuthByUser(Integer userId);

    @Query("FROM Menu m WHERE m.href is not null AND m.href!='' AND m.href!='#' ")
    List<Menu> listAllUrlMenu();

    @Query("SELECT id from Menu")
    List<Integer> findAllIds();
}

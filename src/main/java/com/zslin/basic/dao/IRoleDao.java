package com.zslin.basic.dao;

import com.zslin.basic.model.Role;
import com.zslin.basic.repository.BaseRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by 钟述林 393156105@qq.com on 2016/10/19 9:59.
 */
public interface IRoleDao extends BaseRepository<Role, Integer>, JpaSpecificationExecutor<Role> {

    @Query("SELECT rm.mid FROM RoleMenu rm WHERE rm.rid=?1")
    List<Integer> listRoleMenuIds(Integer roleId);

    Role findBySn(String sn);
}

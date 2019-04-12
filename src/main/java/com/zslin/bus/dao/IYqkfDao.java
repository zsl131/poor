package com.zslin.bus.dao;

import com.zslin.basic.model.Role;
import com.zslin.basic.repository.BaseRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IYqkfDao extends BaseRepository<Role, Integer>, JpaSpecificationExecutor<Role> {
}

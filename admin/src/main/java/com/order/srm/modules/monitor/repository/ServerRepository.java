package com.order.srm.modules.monitor.repository;

import com.order.srm.modules.monitor.domain.Server;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author Zhang houying
 * @date 2019-11-03
 */
public interface ServerRepository extends JpaRepository<Server, Integer>, JpaSpecificationExecutor<Server> {
}

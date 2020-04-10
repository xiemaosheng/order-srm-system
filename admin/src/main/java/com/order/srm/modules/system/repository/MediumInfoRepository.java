package com.order.srm.modules.system.repository;

import com.order.srm.modules.system.domain.MediumInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @date 2019-03-25
 */
@SuppressWarnings("all")
public interface MediumInfoRepository extends JpaRepository<MediumInfo, Long>, JpaSpecificationExecutor<MediumInfo> {

}
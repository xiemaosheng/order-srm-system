package com.order.srm.repository;

import com.order.srm.domain.AlipayConfig;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @date 2018-12-31
 */
public interface AliPayRepository extends JpaRepository<AlipayConfig, Long> {
}

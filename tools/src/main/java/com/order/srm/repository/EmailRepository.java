package com.order.srm.repository;

import com.order.srm.domain.EmailConfig;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @date 2018-12-26
 */
public interface EmailRepository extends JpaRepository<EmailConfig, Long> {
}

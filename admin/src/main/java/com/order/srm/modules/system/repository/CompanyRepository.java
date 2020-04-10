package com.order.srm.modules.system.repository;

import com.order.srm.modules.system.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @date 2019-03-25
 */
@SuppressWarnings("all")
public interface CompanyRepository extends JpaRepository<Company, Long>, JpaSpecificationExecutor<Company> {
    /**
     * 根据ID查询名称
     *
     * @param id ID
     * @return /
     */
    @Query(value = "select name from srm_company where id = ?1", nativeQuery = true)
    String findNameById(Long id);

    @Query(value = "select * from srm_company where code = ?1", nativeQuery = true)
    Optional<Company> findByCode(String code);

}
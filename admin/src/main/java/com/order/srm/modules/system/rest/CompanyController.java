package com.order.srm.modules.system.rest;

import com.order.srm.config.DataScope;
import com.order.srm.exception.BadRequestException;
import com.order.srm.log.Log;
import com.order.srm.modules.system.domain.Company;
import com.order.srm.modules.system.domain.Dept;
import com.order.srm.modules.system.service.CompanyService;
import com.order.srm.modules.system.service.dto.CompanyDto;
import com.order.srm.modules.system.service.dto.CompanyQueryCriteria;
import com.order.srm.modules.system.service.dto.DeptQueryCriteria;
import com.order.srm.utils.ThrowableUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @date 2019-03-25
 */
@RestController
@Api(tags = "系统：公司管理")
@RequestMapping("/api/company")
public class CompanyController {

    private final CompanyService companyService;

    private final DataScope dataScope;

    private static final String ENTITY_NAME = "company";

    public CompanyController(CompanyService companyService, DataScope dataScope) {
        this.companyService = companyService;
        this.dataScope = dataScope;
    }

    @Log("导出公司数据")
    @ApiOperation("导出公司数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('company:list')")
    public void download(HttpServletResponse response, CompanyQueryCriteria criteria) throws IOException {
        companyService.download(companyService.queryAll(criteria), response);
    }

    @Log("查询公司")
    @ApiOperation("查询公司")
    @GetMapping
    @PreAuthorize("@el.check('user:list','company:list')")
    public ResponseEntity<Object> getCompanys(CompanyQueryCriteria criteria) {
        // 数据权限
        Set<Long> set = new HashSet<>();
        set.add(1L);
        set.add(2L);
//        criteria.setIds(dataScope.getDeptIds());
        criteria.setIds(set);
        List<CompanyDto> companyDtos = companyService.queryAll(criteria);
        Map<String, Object> map = new HashMap<>(2);
        map.put("totalElements", companyDtos.size());
        map.put("content", companyDtos);
        return new ResponseEntity
                <>(map, HttpStatus.OK);
    }

    @Log("新增公司")
    @ApiOperation("新增公司")
    @PostMapping
    @PreAuthorize("@el.check('company:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Company resources) {
        if (resources.getId() != null) {
            throw new BadRequestException("A new " + ENTITY_NAME + " cannot already have an ID");
        }
        return new ResponseEntity<>(companyService.create(resources), HttpStatus.CREATED);
    }

    @Log("修改公司")
    @ApiOperation("修改公司")
    @PutMapping
    @PreAuthorize("@el.check('company:edit')")
    public ResponseEntity<Object> update(@Validated(Company.Update.class) @RequestBody Company resources) {
        companyService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除公司")
    @ApiOperation("删除公司")
    @DeleteMapping
    @PreAuthorize("@el.check('company:del')")
    public ResponseEntity<Object> delete(@RequestBody Set<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            throw new BadRequestException("删除失败，请选择要删除的公司");
        }
        try {
            companyService.delete(ids);
        } catch (Throwable e) {
            ThrowableUtil.throwForeignKeyException(e, "删除失败");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
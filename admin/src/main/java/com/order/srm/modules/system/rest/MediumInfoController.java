package com.order.srm.modules.system.rest;

import com.order.srm.config.DataScope;
import com.order.srm.exception.BadRequestException;
import com.order.srm.log.Log;
import com.order.srm.modules.system.domain.MediumInfo;
import com.order.srm.modules.system.service.MediumInfoService;
import com.order.srm.modules.system.service.dto.MediumInfoQueryCriteria;
import com.order.srm.utils.ThrowableUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * @date 2019-03-25
 */
@RestController
@Api(tags = "资料库：中介信息管理")
@RequestMapping("/api/mediumInfo")
public class MediumInfoController {

    private final MediumInfoService mediumInfoService;

    private final DataScope dataScope;

    private static final String ENTITY_NAME = "mediumInfo";

    public MediumInfoController(MediumInfoService mediumInfoService, DataScope dataScope) {
        this.mediumInfoService = mediumInfoService;
        this.dataScope = dataScope;
    }

    @Log("导出中介信息")
    @ApiOperation("导出中介信息")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('mediumInfo:list')")
    public void download(HttpServletResponse response, MediumInfoQueryCriteria criteria) throws IOException {
        mediumInfoService.download(mediumInfoService.queryAll(criteria), response);
    }

    @Log("查询中介信息")
    @ApiOperation("查询中介信息")
    @GetMapping
    @PreAuthorize("@el.check('user:list','mediumInfo:list')")
    public ResponseEntity<Object> getMediumInfos(MediumInfoQueryCriteria criteria, Pageable pageable) {
        // 数据权限
        criteria.setCompanyId(dataScope.getCompanyId());
        return new ResponseEntity<>(mediumInfoService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @Log("新增中介信息")
    @ApiOperation("新增中介信息")
    @PostMapping
    @PreAuthorize("@el.check('mediumInfo:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody MediumInfo resources) {
        if (resources.getId() != null) {
            throw new BadRequestException("A new " + ENTITY_NAME + " cannot already have an ID");
        }
        resources.setCompanyId(dataScope.getCompanyId());
        resources.setAddUserId(dataScope.getLoginUserId());
        return new ResponseEntity<>(mediumInfoService.create(resources), HttpStatus.CREATED);
    }

    @Log("修改中介信息")
    @ApiOperation("修改中介信息")
    @PutMapping
    @PreAuthorize("@el.check('mediumInfo:edit')")
    public ResponseEntity<Object> update(@Validated(MediumInfo.Update.class) @RequestBody MediumInfo resources) {
        mediumInfoService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除中介信息")
    @ApiOperation("删除中介信息")
    @DeleteMapping
    @PreAuthorize("@el.check('mediumInfo:del')")
    public ResponseEntity<Object> delete(@RequestBody Set<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            throw new BadRequestException("删除失败，请选择要删除的公司");
        }
        try {
            mediumInfoService.delete(ids);
        } catch (Throwable e) {
            ThrowableUtil.throwForeignKeyException(e, "删除失败");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
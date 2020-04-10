package com.order.srm.modules.mnt.service.mapper;

import com.order.srm.base.BaseMapper;
import com.order.srm.modules.mnt.domain.Database;
import com.order.srm.modules.mnt.service.dto.DatabaseDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author zhanghouying
 * @date 2019-08-24
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DatabaseMapper extends BaseMapper<DatabaseDto, Database> {

}

package com.order.srm.modules.system.service.mapper;

import com.order.srm.base.BaseMapper;
import com.order.srm.modules.system.domain.GatherInfo;
import com.order.srm.modules.system.service.dto.GatherInfoDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @date 2019-03-25
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GatherInfoMapper extends BaseMapper<GatherInfoDto, GatherInfo> {

}
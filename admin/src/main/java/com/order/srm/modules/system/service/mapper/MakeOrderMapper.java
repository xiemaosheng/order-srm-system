package com.order.srm.modules.system.service.mapper;

import com.order.srm.base.BaseMapper;
import com.order.srm.modules.system.domain.MakeOrder;
import com.order.srm.modules.system.service.dto.MakeOrderDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @date 2019-03-25
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MakeOrderMapper extends BaseMapper<MakeOrderDto, MakeOrder> {

}
package com.order.srm.modules.monitor.service.mapper;

import com.order.srm.base.BaseMapper;
import com.order.srm.modules.monitor.domain.Server;
import com.order.srm.modules.monitor.service.dto.ServerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author Zhang houying
 * @date 2019-11-03
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ServerMapper extends BaseMapper<ServerDTO, Server> {

}

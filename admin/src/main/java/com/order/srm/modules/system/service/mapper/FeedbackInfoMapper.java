package com.order.srm.modules.system.service.mapper;

import com.order.srm.base.BaseMapper;
import com.order.srm.modules.system.domain.FeedbackInfo;
import com.order.srm.modules.system.service.dto.FeedbackInfoDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @date 2019-03-25
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FeedbackInfoMapper extends BaseMapper<FeedbackInfoDto, FeedbackInfo> {

}
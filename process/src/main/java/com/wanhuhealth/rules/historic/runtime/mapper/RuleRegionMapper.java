package com.wanhuhealth.rules.historic.runtime.mapper;

import com.wanhuhealth.rules.historic.model.CfgRegionRuleSystemCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by admin on 2017/5/31.
 */
@Mapper
public interface RuleRegionMapper {

	List<CfgRegionRuleSystemCategory> findByRuleId(@Param("ruleId") Long ruleId);
}

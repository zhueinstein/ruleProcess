package com.wanhuhealth.rules.record.runtime.mapper;

import com.wanhuhealth.rules.record.model.CfgOrderDrugRules;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by admin on 2017/5/31.
 */
@Mapper
public interface RuleFilesMapper {

	List<CfgOrderDrugRules> findAll();

	CfgOrderDrugRules getByRuleMark(@Param("ruleMark") String ruleMark);
}

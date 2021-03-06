package com.wanhuhealth.rules.historic.runtime.service;

import com.wanhuhealth.rules.historic.model.CfgOrderDrugRules;

import java.util.List;

/**
 * Created by admin on 2017/5/31.
 */
public interface IWanhuHealthRuleService {

	List<CfgOrderDrugRules> findAll();

	CfgOrderDrugRules getByRuleMark(String ruleLog);
}

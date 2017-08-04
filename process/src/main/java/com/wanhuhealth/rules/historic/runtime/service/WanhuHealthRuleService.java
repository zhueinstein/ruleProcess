package com.wanhuhealth.rules.historic.runtime.service;

import com.wanhuhealth.rules.historic.model.CfgOrderDrugRules;
import com.wanhuhealth.rules.historic.runtime.mapper.RuleFilesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2017/5/31.
 */
@Service
public class WanhuHealthRuleService implements IWanhuHealthRuleService {

	@Autowired
	private RuleFilesMapper ruleFilesMapper;

	@Override
	public List<CfgOrderDrugRules> findAll() {
		return ruleFilesMapper.findAll();
	}

	@Override
	public CfgOrderDrugRules getByRuleMark(String ruleMark) {
		return ruleFilesMapper.getByRuleMark(ruleMark);
	}
}

package com.wanhuhealth.rules.utils;

import org.kie.api.runtime.rule.AgendaFilter;
import org.kie.api.runtime.rule.Match;

import java.util.List;

/**
 * Created by admin on 2017/2/13.
 */
public class RulesFilter implements AgendaFilter{
	private List<String> effectiveRules;

	public RulesFilter(List<String> effectiveRules) {
		this.effectiveRules = effectiveRules;
	}

	/**
	 * 取出规则文件的名称，使用分割得到的第一部分来和议程中有效的规则进行比对，使该规则可以触发
	 * @param match
	 * @return
	 */
	@Override
	public boolean accept(Match match) {
		if(effectiveRules.contains(match.getRule().getName().split(":")[0])){
			return true;
		}
		return false;
	}
}

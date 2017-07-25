package com.wanhuhealth.rules.record.runtime.handlers;

import com.google.common.collect.Lists;
import com.wanhuhealth.rules.record.fact.DrugReferFact;
import com.wanhuhealth.rules.record.fact.OrderFact;
import org.kie.api.runtime.KieSession;

import java.util.List;

/**
 * Created by admin on 2017/6/5.
 */
public class DrugBusinessHandler extends RuleHandler {
	private static List<String> ruleMarks = Lists.newArrayList("drugTypeExceed", "newDrug","dupGenericName","dupDrugType");

	@Override
	public List<String> getRuleMark() {
		return ruleMarks;
	}

	@Override
	public void handle(OrderFact orderFact, KieSession kieSession) {
		DrugReferFact drugReferFact = new DrugReferFact();
		drugReferFact.setDrugFacts(orderFact.getDrugFacts());

		kieSession.insert(drugReferFact);
	}
}

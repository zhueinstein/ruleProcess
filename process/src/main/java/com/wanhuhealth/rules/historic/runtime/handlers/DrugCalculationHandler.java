package com.wanhuhealth.rules.historic.runtime.handlers;

import com.google.common.collect.Lists;
import com.wanhuhealth.rules.historic.fact.DrugFact;
import com.wanhuhealth.rules.historic.fact.DrugReferFact;
import com.wanhuhealth.rules.historic.fact.OrderFact;
import org.kie.api.runtime.KieSession;

import java.util.List;

/** 处理超量、超限的规则
 * Created by admin on 2017/6/2.
 */
public class DrugCalculationHandler extends RuleHandler {

	private static List<String> ruleMark = Lists.newArrayList("hisAmtExceed", "buyAmtOutLimit", "buyAmtMeetMax", "buyAmtExceed", "useAmtExceed","dailyAmtExceed");

	@Override
	public List<String> getRuleMark() {
		return ruleMark;
	}

	@Override
	public void handle(OrderFact orderFact, KieSession kieSession) {
		DrugReferFact fact = new DrugReferFact();
		List<DrugFact> validateFacts = Lists.newArrayList();
		for (DrugFact drugFact : orderFact.getDrugFacts()) {
			if(drugFact.getBuyAmount() != null &&
				drugFact.getFrequency() != null &&
				drugFact.getUseAmount() != null){
				validateFacts.add(drugFact);
			}
		}
		if(validateFacts.size() > 0) {
			fact.setDrugFacts(validateFacts);
			fact.setCycle(orderFact.getCycle());
			kieSession.insert(fact);
		}
	}
}

package com.wanhuhealth.rules.historic.runtime.handlers;

import com.google.common.collect.Lists;
import com.wanhuhealth.rules.historic.fact.OrderFact;
import com.wanhuhealth.rules.historic.fact.OrderReferFact;
import org.kie.api.runtime.KieSession;

import java.util.List;

/**
 * Created by admin on 2017/6/5.
 */
public class OrderBusinessHandler extends RuleHandler {
	List<String> ruleMarks = Lists.newArrayList("dupOrder");

	@Override
	public List<String> getRuleMark() {
		return ruleMarks;
	}

	@Override
	public void handle(OrderFact orderFact, KieSession kieSession) {
		List<OrderReferFact> orderReferFacts = Lists.newArrayList();
		OrderReferFact orderReferFact;
		for (OrderFact fact : orderFact.getHisOrderFacts()) {
			orderReferFact = new OrderReferFact();
			orderReferFact.setId(fact.getId());
			orderReferFact.setStatus(fact.getStatus());
			orderReferFacts.add(orderReferFact);
		}
		orderReferFact = new OrderReferFact();
		orderReferFact.setHisOrderFacts(orderReferFacts);
		kieSession.insert(orderReferFact);
	}
}

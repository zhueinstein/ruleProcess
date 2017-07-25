package com.wanhuhealth.rules.record.runtime.handlers;

import com.google.common.collect.Lists;
import com.wanhuhealth.rules.record.fact.*;
import org.kie.api.runtime.KieSession;

import java.util.List;

/**
 * Created by admin on 2017/6/5.
 */
public class DiseaseBusinessHandler extends RuleHandler {
	static List<String> ruleMarks = Lists.newArrayList("diseaseNotMatch", "newDisease");

	@Override
	public List<String> getRuleMark() {
		return ruleMarks;
	}

	@Override
	public void handle(OrderFact orderFact, KieSession kieSession) {
		DiseaseReferFact diseaseReferFact = new DiseaseReferFact();
		List<DiseaseFact> diseaseFacts = orderFact.getDiseaseFacts();
		DrugIndicationFact drugIndicationFact;
		diseaseReferFact.setDiseaseFacts(diseaseFacts);
		for (DrugFact drugFact : orderFact.getDrugFacts()) {
			drugIndicationFact = new DrugIndicationFact();
			drugIndicationFact.setDrugId(drugFact.getDrugId());
			List<DiseaseFact> indictions = drugFact.getIndications();
			drugIndicationFact.setIndications(indictions);
		}
		kieSession.insert(diseaseReferFact);
	}

}

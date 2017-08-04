package com.wanhuhealth.rules.historic.fact;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by admin on 2017/6/5.
 */
public class DiseaseReferFact {

	private List<DiseaseFact> diseaseFacts = Lists.newArrayList();
	private List<DrugIndicationFact> indicationFacts = Lists.newArrayList();
	private List<DiseaseFact> hisDiseaseFacts = Lists.newArrayList();

	public List<DiseaseFact> getDiseaseFacts() {
		return diseaseFacts;
	}

	public void setDiseaseFacts(List<DiseaseFact> diseaseFacts) {
		this.diseaseFacts = diseaseFacts;
	}

	public List<DrugIndicationFact> getIndicationFacts() {
		return indicationFacts;
	}

	public void setIndicationFacts(List<DrugIndicationFact> indicationFacts) {
		this.indicationFacts = indicationFacts;
	}

	public List<DiseaseFact> getHisDiseaseFacts() {
		return hisDiseaseFacts;
	}

	public void setHisDiseaseFacts(List<DiseaseFact> hisDiseaseFacts) {
		this.hisDiseaseFacts = hisDiseaseFacts;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}

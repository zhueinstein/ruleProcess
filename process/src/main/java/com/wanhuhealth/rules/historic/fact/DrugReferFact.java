package com.wanhuhealth.rules.historic.fact;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by admin on 2017/6/2.
 */
public class DrugReferFact {

	private List<DrugFact> drugFacts = Lists.newArrayList();
	private Integer cycle;
	private List<DrugFact> hisDrugFacts = Lists.newArrayList();

	public List<DrugFact> getDrugFacts() {
		return drugFacts;
	}

	public void setDrugFacts(List<DrugFact> drugFacts) {
		this.drugFacts = drugFacts;
	}

	public Integer getCycle() {
		return cycle;
	}

	public void setCycle(Integer cycle) {
		this.cycle = cycle;
	}

	public List<DrugFact> getHisDrugFacts() {
		return hisDrugFacts;
	}

	public void setHisDrugFacts(List<DrugFact> hisDrugFacts) {
		this.hisDrugFacts = hisDrugFacts;
	}
	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}

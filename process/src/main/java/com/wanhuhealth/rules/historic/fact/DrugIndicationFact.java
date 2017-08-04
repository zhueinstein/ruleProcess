package com.wanhuhealth.rules.historic.fact;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by admin on 2017/6/5.
 */
public class DrugIndicationFact {

	private Long drugId;
	private List<DiseaseFact> indications = Lists.newArrayList();

	public Long getDrugId() {
		return drugId;
	}

	public void setDrugId(Long drugId) {
		this.drugId = drugId;
	}

	public List<DiseaseFact> getIndications() {
		return indications;
	}

	public void setIndications(List<DiseaseFact> indications) {
		this.indications = indications;
	}
	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}

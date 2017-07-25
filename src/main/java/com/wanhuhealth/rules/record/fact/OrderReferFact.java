package com.wanhuhealth.rules.record.fact;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by admin on 2017/6/5.
 */
public class OrderReferFact {
	private String id;
	private Integer status;
	List<OrderReferFact> hisOrderFacts = Lists.newArrayList();
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<OrderReferFact> getHisOrderFacts() {
		return hisOrderFacts;
	}

	public void setHisOrderFacts(List<OrderReferFact> hisOrderFacts) {
		this.hisOrderFacts = hisOrderFacts;
	}
	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}

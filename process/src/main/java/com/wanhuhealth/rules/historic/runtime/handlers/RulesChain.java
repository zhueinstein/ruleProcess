package com.wanhuhealth.rules.historic.runtime.handlers;

/**
 * Created by admin on 2017/6/2.
 */
public class RulesChain {

	public static RuleHandler getRuleHandlerChain(){
		OrderBusinessHandler r_a = new OrderBusinessHandler();
		DrugCalculationHandler r_b = new DrugCalculationHandler();
		DrugBusinessHandler r_c = new DrugBusinessHandler();
		DiseaseBusinessHandler r_d = new DiseaseBusinessHandler();

		r_a.setNextHandler(r_b);
		r_b.setNextHandler(r_c);
		r_c.setNextHandler(r_d);
		r_d.setNextHandler(null);
		return r_a;
	}

}

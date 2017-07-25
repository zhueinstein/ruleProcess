package com.wanhuhealth.rules.record.runtime.handlers;

import com.wanhuhealth.rules.record.fact.OrderFact;
import org.apache.log4j.Logger;
import org.kie.api.runtime.KieSession;

import java.util.List;

/**
 * Created by admin on 2017/6/2.
 */
public abstract  class RuleHandler {
	private Logger logger = Logger.getLogger(RuleHandler.class);

	/**
	 * 维持下一个处理者
	 */
	protected RuleHandler nextHandler;

	protected void setNextHandler(RuleHandler nextHandler){
		this.nextHandler = nextHandler;
	}


	/**
	 * 做链式处理
	 * @param ruleMark
	 * @param orderFact
	 * @param kieSession
	 */
	public void handleRequest(String ruleMark, OrderFact orderFact, KieSession kieSession) {
		System.err.println(getRuleMark() + "&&&&&&&&&&&&&&&" + ruleMark);
		if (getRuleMark().contains(ruleMark)) {
			handle(orderFact, kieSession);
		}else {
			if(nextHandler != null) {
				this.nextHandler.handleRequest(ruleMark, orderFact, kieSession);  //转发请求
			}else {
				logger.error(String.format("不支持的规则ruleMark=%s", ruleMark));
			}
		}
	}
	/**
	 *  返回当前能处理的规则
	 * @return
	 */
	public abstract List<String> getRuleMark();

	/**
	 * 具体的处理业务
	 */
	public abstract void handle(OrderFact orderFact, KieSession kieSession);

}

package com.wanhuhealth.rules.utils;

import com.alibaba.fastjson.JSON;
import com.wanhuhealth.rules.redis.service.RedisServiceImpl;
import org.apache.log4j.Logger;
import org.kie.api.event.rule.*;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.KnowledgeBase;
import org.kie.internal.KnowledgeBaseFactory;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;

import java.util.Hashtable;
import java.util.Iterator;

/**
 * Created by admin on 2017/2/28.
 */
public class KieSessionUtil {
	private static Logger logger = Logger.getLogger(KieSessionUtil.class);
	public static KieSession session ;
	public static KnowledgeBase kBase;
	static RedisServiceImpl redis = SpringContextHolder.getBean(RedisServiceImpl.class);
	static String DSL = "ccc";

	static{
		if(kBase == null){
			createKieBase();
		}
	}

	/**
	 * 使用 .drl文件形式的kieSession创建
	 * @param sessionName
	 * @return
	 */
	public static KieSession getKieSession(String sessionName) {
		if(session == null) {
			session = KnowledgeSessionHelper.getStatefulKnowledgeSession(sessionName);
			addLogger(session);
		}
		return session;
	}

	/**
	 * 创建KnowlwdgeBase，包含规则文件，议程等
	 */
	public static void createKieBase(){
		KnowledgeBuilder kb = KnowledgeBuilderFactory.newKnowledgeBuilder();
		RulesUtils.redisCacheRulesHash();
		RulesUtils.redisCacheRegionRule();
		Hashtable<String, String> rulesHash = JSON.parseObject(redis.get(RulesUtils.CACHE_RULES_HASHTABLE), Hashtable.class);
		if(rulesHash.size() == 0){
			kBase = null;
		}else {
			Iterator<String> iter = rulesHash.values().iterator();
			while ((iter.hasNext())){
				kb.add(ResourceFactory.newByteArrayResource(iter.next().getBytes()), ResourceType.DRL);
			}
			if (kb.hasErrors()) {
				logger.error("规则文件错误 = " + kb.getErrors());
			}
			kBase = KnowledgeBaseFactory.newKnowledgeBase();
			kBase.addKnowledgePackages(kb.getKnowledgePackages());
		}
	}

	/**
	 * 创建kieSession，为每一个请求新建一个会话，使用完释放该会话
	 * @return
	 */
	public static KieSession getStatefulKnowledgeSessionFromTableField() {
		if(kBase != null) {
			session = kBase.newStatefulKnowledgeSession();
			addLogger(session);
			return session;
		}else {
			return null;
		}
	}

	/**
	 * 规则引擎事件加日志
	 * @param session
	 */
	public static void addLogger(KieSession session){
		session.addEventListener(new RuleRuntimeEventListener() {
			public void objectInserted(ObjectInsertedEvent event) {
				logger.info("fact被insert到workingMemory 中信息如下：\n" + event.getObject().toString());
			}

			public void objectUpdated(ObjectUpdatedEvent event) {
				logger.info("fact被修改，新的信息如下: \n" + event.getObject().toString());
			}

			public void objectDeleted(ObjectDeletedEvent event) {
				logger.info("fact 被移除： \n"
					+ event.getOldObject().toString());
			}
		});
		session.addEventListener(new AgendaEventListener() {
			public void matchCreated(MatchCreatedEvent event) {
				logger.info("规则  "
					+ event.getMatch().getRule().getName()
					+ " 在议程中能够被触发");
			}

			public void matchCancelled(MatchCancelledEvent event) {
				logger.info("规则 "
					+ event.getMatch().getRule().getName()
					+ " 在议程中不能能够被触发");
			}

			public void beforeMatchFired(BeforeMatchFiredEvent event) {
				logger.info("规则 "
					+ event.getMatch().getRule().getName()
					+ " 将要被触发");
			}

			public void afterMatchFired(AfterMatchFiredEvent event) {
				logger.info("规则 "
					+ event.getMatch().getRule().getName()
					+ " 被触发");
			}

			public void agendaGroupPopped(AgendaGroupPoppedEvent event) {
			}

			public void agendaGroupPushed(AgendaGroupPushedEvent event) {
			}

			public void beforeRuleFlowGroupActivated(RuleFlowGroupActivatedEvent event) {
			}

			public void afterRuleFlowGroupActivated(RuleFlowGroupActivatedEvent event) {
			}

			public void beforeRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent event) {
			}

			public void afterRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent event) {
			}
		});
	}
}

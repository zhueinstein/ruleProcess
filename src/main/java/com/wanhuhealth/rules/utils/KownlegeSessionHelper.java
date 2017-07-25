package com.wanhuhealth.rules.utils;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;

/**
 * Created by admin on 2017/2/28.
 */
public class KownlegeSessionHelper {
	public static KieContainer createRuleBase(){
		KieServices ks = KieServices.Factory.get();
		KieContainer kieContainer = ks.getKieClasspathContainer();
		return  kieContainer;
	}

	public static StatelessKieSession getStatelessKownlegeSession(KieContainer kieContainer, String sessionName){
		StatelessKieSession statelessKieSession = kieContainer.newStatelessKieSession(sessionName);
		return statelessKieSession;
	}

	public static KieSession getStatefulKownlegeSession(String sessionName){
		KieSession kieSession = createRuleBase().newKieSession(sessionName);
		return kieSession;
	}
}

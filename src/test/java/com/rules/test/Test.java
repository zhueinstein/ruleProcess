package com.rules.test;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

/**
 * Created by admin on 2017/6/2.
 */
public class Test {

	public static void main(String[] args) {

		KieServices ks = KieServices.Factory.get();
		KieContainer kc = ks.getKieClasspathContainer();
		KieSession kieSession = kc.newKieSession("helloWorld1");
		kieSession.fireAllRules();
		kieSession.dispose();
	}
}

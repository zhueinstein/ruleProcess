package com.wanhuhealth.rules.batch.service;

import com.wanhuhealth.rules.drools.OrderInfoRuleModel;
import com.wanhuhealth.rules.drools.ResResult;
import com.wanhuhealth.rules.drools.RulesConstant;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieSession;

/**
 * Created by zcx on 2017/8/4.
 */
public class WarningProcesser {
    static KieSession kieSession;
    static {
        KieServices kieService = KieServices.Factory.get();
        kieSession = kieService.getKieClasspathContainer().newKieSession(RulesConstant.warning);

    }
    public static void processor(OrderInfoRuleModel orderFact, ResResult global){
        kieSession.setGlobal("resResult", global);
        kieSession.insert(orderFact);
        kieSession.fireAllRules();
        kieSession.dispose();
    }

}

package com.wanhuhealth.rules.batch.service;

import com.wanhuhealth.rules.batch.drools.OrderInfoRuleModel;
import com.wanhuhealth.rules.batch.drools.ResResult;
import com.wanhuhealth.rules.batch.drools.RulesConstant;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Component;

/**
 * Created by zcx on 2017/8/4.
 */
@Component
public class CommonProcessor {

    static KieContainer kieContainer;
    static KieSession kieSession;
    static {
        KieServices kieService = KieServices.Factory.get();
        kieContainer = kieService.getKieClasspathContainer();

    }
    public void processor(OrderInfoRuleModel orderFact, ResResult global){
        kieSession = kieContainer.newKieSession(RulesConstant.sessionName);
        kieSession.setGlobal("resResult", global);
        kieSession.insert(orderFact);
        kieSession.fireAllRules();
        kieSession.dispose();
    }

}

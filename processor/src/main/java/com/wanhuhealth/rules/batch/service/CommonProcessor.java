package com.wanhuhealth.rules.batch.service;

import com.wanhuhealth.rules.drools.OrderInfoRuleModel;
import com.wanhuhealth.rules.drools.ResResult;
import org.kie.api.cdi.KContainer;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Component;

/**
 * Created by zcx on 2017/8/4.
 */
@Component
public class CommonProcessor {

    @KContainer
    KieContainer kieContainer;
    public void processor(OrderInfoRuleModel orderFact, ResResult global){
        KieSession kieSession = kieContainer.newKieSession("wanHu");
        kieSession.setGlobal("resResult", global);
        kieSession.insert(orderFact);
        kieSession.fireAllRules();
        kieSession.dispose();
    }

}

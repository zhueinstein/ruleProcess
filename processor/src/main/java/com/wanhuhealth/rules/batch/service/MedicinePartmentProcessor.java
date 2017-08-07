package com.wanhuhealth.rules.batch.service;

import com.wanhuhealth.rules.drools.ComputeSalience;
import com.wanhuhealth.rules.drools.OrderInfoRuleModel;
import com.wanhuhealth.rules.drools.ResResult;
import com.wanhuhealth.rules.drools.RulesConstant;
import org.kie.api.KieServices;
import org.kie.api.cdi.KContainer;
import org.kie.api.cdi.KSession;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by zcx on 2017/8/4.
 */
@Component
public class MedicinePartmentProcessor {

    @Autowired
    ComputeSalience computeSalience;
    @KContainer
    KieContainer kieContainer;

    public void processor(OrderInfoRuleModel orderFact, ResResult global){
        KieSession kieSession = kieContainer.newKieSession("HL");
        kieSession.setGlobal("resResult", global);
        kieSession.setGlobal("computeSalience", computeSalience);
        kieSession.insert(orderFact);
        kieSession.fireAllRules();
        kieSession.dispose();
    }

}

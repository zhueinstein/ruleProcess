package com.wanhuhealth.rules.batch.service;

import com.wanhuhealth.rules.drools.OrderInfoRuleModel;
import com.wanhuhealth.rules.drools.ResResult;
import com.wanhuhealth.rules.drools.RulesConstant;
import org.kie.api.KieServices;
import org.kie.api.cdi.KSession;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Component;

/**
 * Created by zcx on 2017/8/4.
 */
@Component
public class CommonProcessor {

    @KSession("wanHu")
    KieSession kieSession;
    public void processor(OrderInfoRuleModel orderFact, ResResult global){
        kieSession.setGlobal("resResult", global);
        kieSession.insert(orderFact);
        kieSession.fireAllRules();
    }

}

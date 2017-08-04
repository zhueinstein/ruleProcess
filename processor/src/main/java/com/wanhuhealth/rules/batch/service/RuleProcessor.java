package com.wanhuhealth.rules.batch.service;

import com.wanhuhealth.rules.drools.OrderInfoRuleModel;
import com.wanhuhealth.rules.drools.ResResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by zcx on 2017/8/4.
 */
@Component
public class RuleProcessor {
    @Autowired
    CommonProcessor commonProcessor;
    @Autowired
    MedicinePartmentProcessor medicinePartmentProcessor;

    public void processor(OrderInfoRuleModel orderFact, ResResult global){
        commonProcessor.processor(orderFact, global);
        medicinePartmentProcessor.processor(orderFact, global);
//        WarningProcesser.processor(orderFact, global);
    }
}

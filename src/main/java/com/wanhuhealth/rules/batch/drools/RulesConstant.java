package com.wanhuhealth.rules.batch.drools;

import com.wanhuhealth.rules.batch.excel.OutFormatMessage;
import com.wanhuhealth.rules.utils.DateUtils;
import org.assertj.core.util.Lists;

import java.util.Date;
import java.util.List;

/**
 * Created by zcx on 2017/8/1.
 */
public interface RulesConstant {
     List<String> common = Lists.newArrayList("单日用量超限","同通用名","种类超标","药品种类重复","药品超限(60天)","适应症不符");

     List<String> medicine = Lists.newArrayList("中成药与西药成分重复","中成药主要成分相同","中成药功效类别重复","中成药毒副作用累加","中西复方制剂与西药制剂成分重复","合并症用药不合理","年龄限制","联合用药不合理-种类","西药主要成分重复","西药相互作用","西药药理作用机制冲突","西药药理作用重复");

     String data_direction = "/Users/zcx/drools_data/"; // mac
     // static String data_direction = "D:\\drools_data//"; // window
     String tail = DateUtils.formatDate(new Date(), "yyyyMMddHHmm");

     String outFileName = String.format("%sRuleResult_%s.xlsx",data_direction, tail);
     String outRulePercentFileName = String.format("%sRulePercent_%s.xlsx",data_direction, tail);
     String outTotalPercentFileName = String.format("%sTotalPercent_%s.xlsx",data_direction, tail);


     String sessionName = "wanHu";
     String hlSessionName = "HL";

}

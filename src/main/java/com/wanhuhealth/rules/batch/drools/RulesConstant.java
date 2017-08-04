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

     List<String> medicineNames = Lists.newArrayList("年龄限制(含冰片中成药种类过多)","联合用药不合理-种类(降血压药种类数过多)","合并症用药不合理(高血压糖尿病哮喘－β受体阻滞剂)",
             "合并症用药不合理(高血压痛风发作期－排钾利尿剂)","合并症用药不合理(高血压痛风发作期－氨苯蝶啶)","中西复方制剂与西药制剂成分重复(二维三七桂利嗪胶囊－盐酸氟桂利嗪)",
             "合并症用药不合理(高血压痛风发作期－氢氯噻嗪)","合并症用药不合理(高血压高尿酸血症－排钾利尿剂)","合并症用药不合理(高血压高尿酸血症－氨苯蝶啶)",
             "合并症用药不合理(高血压高尿酸血症－氢氯噻嗪)","西药主要成分重复","西药药理作用重复(非利尿剂)",
             "西药药理作用重复(非袢利尿剂/噻嗪类利尿剂/类噻嗪类利尿剂)","西药药理作用重复(非排钾利尿剂)","西药药理作用重复(特定诊断－利尿剂)",
             "西药药理作用重复(特定诊断－袢利尿剂/噻嗪类利尿剂/类噻嗪类利尿剂)","西药药理作用重复(特定诊断－排钾利尿剂)","西药药理作用机制冲突(格列齐特－那格列奈)",
             "西药相互作用(利福平－硝苯地平)","西药相互作用(硫酸氯吡格雷－奥美拉唑)","西药主要成分重复(西药主要成分重复)",
             "西药相互作用(硫酸氯吡格雷－埃索拉唑)","西药相互作用(特定诊断－苯磺酸氨氯地平－胺碘酮)","西药相互作用(阿法骨化醇－碳酸钙D3)",
             "西药主要成分重复(西药－西药复方制剂)","西药主要成分重复(西药复方制剂)","中成药毒副作用累加(脑安胶囊－复方丹参滴丸)",
             "中成药毒副作用累加(脑安胶囊－复方丹参片)","中成药功效类别重复(脑心通－利脑心)","中成药功效类别重复(脑心通－血塞通软胶囊)","中成药功效类别重复(脑心通－血塞通片)",
             "中成药功效类别重复(脑心通－通心络)","中成药功效类别重复(复方丹参滴丸－心元胶囊)","中成药功效类别重复(复方丹参滴丸－芪参益气滴丸)",
             "中成药功效类别重复(复方丹参滴丸－复方丹参片)","中成药功效类别重复(复方丹参滴丸－心可舒片)","中成药功效类别重复(稳心颗粒－参松养心胶囊)",
             "中成药与西药成分重复(血脂康－他汀类)","中成药主要成分相同(金水宝胶囊－百令胶囊)","中成药主要成分相同(脑安胶囊－华佗再造丸)",
             "中西复方制剂与西药制剂成分重复(消渴丸－格列本脲)","中西复方制剂与西药制剂成分重复(珍菊降压片－盐酸可乐定)","中西复方制剂与西药制剂成分重复(珍菊降压片－氢氯噻嗪)"
             );
//     String data_direction = "/Users/zcx/drools_data/"; // mac
      static String data_direction = "D:\\drools_data//"; // window
     String tail = DateUtils.formatDate(new Date(), "yyyyMMddHHmm");

     String outFileName = String.format("%sRuleResult_%s.xlsx",data_direction, tail);
     String outRulePercentFileName = String.format("%sRulePercent_%s.xlsx",data_direction, tail);
     String outTotalPercentFileName = String.format("%sTotalPercent_%s.xlsx",data_direction, tail);


     String sessionName = "wanHu";
     String hlSessionName = "HL";

}

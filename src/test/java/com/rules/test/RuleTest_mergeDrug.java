package com.rules.test;

import com.wanhuhealth.rules.batch.drools.DrugInfoRuleModel;
import com.wanhuhealth.rules.batch.drools.OrderInfoRuleModel;
import com.wanhuhealth.rules.batch.drools.PatientInfoRuleModel;
import com.wanhuhealth.rules.batch.drools.ResResult;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.AgendaFilter;
import org.kie.api.runtime.rule.Match;

import java.util.List;

/**
 * Created by admin on 2017/6/2.
 */
public class RuleTest_mergeDrug {

	/**
	 * 联合用药不合理-种类	处方中适应症为高血压的药品	西药类别3降血压药单方＞3种		禁忌	优先扣除处方中处方中总价高药品，最终保留3种。

	 * 联合用药不合理-种类(适应症为高血压的药品)"
	 */
		@Test
		public void mergeDrug(){
			OrderInfoRuleModel orderInfoRuleModel = new OrderInfoRuleModel();
			PatientInfoRuleModel patientInfoRuleModel = new PatientInfoRuleModel();
			patientInfoRuleModel.setAge(65);
			patientInfoRuleModel.setPatientId("abc123");
			orderInfoRuleModel.setPatientInfoRuleModel(patientInfoRuleModel);
			orderInfoRuleModel.setCycle(80);
			List<DrugInfoRuleModel> drugInfoRuleModels = Lists.newArrayList();
			DrugInfoRuleModel drugInfoRuleModel1 = new DrugInfoRuleModel();
			drugInfoRuleModel1.setDrugId(1L);
			drugInfoRuleModel1.setCommonName("drug01");
			drugInfoRuleModel1.setPrice(1.00);
			drugInfoRuleModel1.setBuyAmount(3.0);
			drugInfoRuleModel1.setThirCategory("降血压药单方");
			drugInfoRuleModel1.setDiseases(Lists.newArrayList("高血压"));
			drugInfoRuleModels.add(drugInfoRuleModel1);
			DrugInfoRuleModel drugInfoRuleModel2 = new DrugInfoRuleModel();
            drugInfoRuleModel2.setDrugId(2L);
            drugInfoRuleModel2.setCommonName("drug02");
            drugInfoRuleModel2.setPrice(2.00);
            drugInfoRuleModel2.setBuyAmount(3.00);
            drugInfoRuleModel2.setThirCategory("降血压药单方");
            drugInfoRuleModel2.setDiseases(Lists.newArrayList("高血压"));
            drugInfoRuleModels.add(drugInfoRuleModel2);
            DrugInfoRuleModel drugInfoRuleModel3 = new DrugInfoRuleModel();
            drugInfoRuleModel3.setDrugId(3L);
            drugInfoRuleModel3.setCommonName("drug03");
            drugInfoRuleModel3.setPrice(2.00);
            drugInfoRuleModel3.setBuyAmount(4.00);
            drugInfoRuleModel3.setThirCategory("降血压药单方");
            drugInfoRuleModel3.setDiseases(Lists.newArrayList("高血压"));
            drugInfoRuleModels.add(drugInfoRuleModel3);
            DrugInfoRuleModel drugInfoRuleModel4 = new DrugInfoRuleModel();
            drugInfoRuleModel4.setDrugId(4L);
            drugInfoRuleModel4.setCommonName("drug04");
            drugInfoRuleModel4.setPrice(2.00);
            drugInfoRuleModel4.setBuyAmount(13.00);
            drugInfoRuleModel4.setThirCategory("降血压药单方");
            drugInfoRuleModel4.setDiseases(Lists.newArrayList("高血压"));
            drugInfoRuleModels.add(drugInfoRuleModel4);
			orderInfoRuleModel.setDrugInfoList(drugInfoRuleModels);
			ResResult resResult = new ResResult();
			KieSession kieSession = KieServices.Factory.get().getKieClasspathContainer().newKieSession("HL");
			kieSession.insert(orderInfoRuleModel);
			kieSession.setGlobal("resResult", resResult);
			kieSession.fireAllRules(new AgendaFilter() {
				@Override
				public boolean accept(Match match) {
					return match.getRule().getName().startsWith("mergeDrug");
				}
			});
			kieSession.dispose();
			assert (orderInfoRuleModel.getDrugInfoList().get(0).getBuyAmount() != 0.0);
			assert (orderInfoRuleModel.getDrugInfoList().get(1).getBuyAmount() != 0.0);
			assert (orderInfoRuleModel.getDrugInfoList().get(2).getBuyAmount() != 0.0);
			assert (orderInfoRuleModel.getDrugInfoList().get(3).getBuyAmount() == 0.0);
		}

}

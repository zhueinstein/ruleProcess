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
public class RuleTest_ChinesePatentMedicineIngredientDuplicate {
	/**
	 * 中成药主要成分相同(金水宝胶囊 with 百令胶囊)
	 */
	@Test
	public void ChinesePatentMedicineIngredientDuplicate_01(){
		OrderInfoRuleModel orderInfoRuleModel = new OrderInfoRuleModel();
		PatientInfoRuleModel patientInfoRuleModel = new PatientInfoRuleModel();
		patientInfoRuleModel.setAge(65);
		patientInfoRuleModel.setPatientId("abc123");
		orderInfoRuleModel.setPatientInfoRuleModel(patientInfoRuleModel);
		orderInfoRuleModel.setCycle(80);
		List<DrugInfoRuleModel> drugInfoRuleModels = Lists.newArrayList();
		DrugInfoRuleModel drugInfoRuleModel1 = new DrugInfoRuleModel();
		drugInfoRuleModel1.setDrugId(1L);
		drugInfoRuleModel1.setCommonName("金水宝胶囊");
		drugInfoRuleModel1.setPrice(1.00);
		drugInfoRuleModel1.setBuyAmount(3.0);
		drugInfoRuleModel1.setMainIngredients(Lists.newArrayList("冰片"));
		drugInfoRuleModel1.setFirstCategory("西药");
		drugInfoRuleModels.add(drugInfoRuleModel1);
		DrugInfoRuleModel drugInfoRuleModel2 = new DrugInfoRuleModel();
		drugInfoRuleModel2.setDrugId(2L);
		drugInfoRuleModel2.setCommonName("百令胶囊");
		drugInfoRuleModel2.setFirstCategory("西药");
		drugInfoRuleModel2.setOtherCategory(Lists.newArrayList("他汀类"));
		drugInfoRuleModel2.setPrice(2.00);
		drugInfoRuleModel2.setBuyAmount(3.00);
		drugInfoRuleModel2.setMainIngredients(Lists.newArrayList("冰片"));
		drugInfoRuleModels.add(drugInfoRuleModel2);
		orderInfoRuleModel.setDrugInfoList(drugInfoRuleModels);
		ResResult resResult = new ResResult();
		KieSession kieSession = KieServices.Factory.get().getKieClasspathContainer().newKieSession("HL");
		kieSession.insert(orderInfoRuleModel);
		kieSession.setGlobal("resResult", resResult);
		kieSession.fireAllRules(new AgendaFilter() {
			@Override
			public boolean accept(Match match) {
				return match.getRule().getName().startsWith("ChinesePatentMedicineIngredientDuplicate");
			}
		});
		kieSession.dispose();
		assert (orderInfoRuleModel.getDrugInfoList().get(1).getBuyAmount() == 0.0);
	}

	/**
	 * 中成药主要成分相同(脑安胶囊 with 华佗再造丸)
	 */
	@Test
	public void ChinesePatentMedicineIngredientDuplicate_02(){
		OrderInfoRuleModel orderInfoRuleModel = new OrderInfoRuleModel();
		PatientInfoRuleModel patientInfoRuleModel = new PatientInfoRuleModel();
		patientInfoRuleModel.setAge(65);
		patientInfoRuleModel.setPatientId("abc123");
		orderInfoRuleModel.setPatientInfoRuleModel(patientInfoRuleModel);
		orderInfoRuleModel.setCycle(80);
		List<DrugInfoRuleModel> drugInfoRuleModels = Lists.newArrayList();
		DrugInfoRuleModel drugInfoRuleModel1 = new DrugInfoRuleModel();
		drugInfoRuleModel1.setDrugId(1L);
		drugInfoRuleModel1.setCommonName("脑安胶囊");
		drugInfoRuleModel1.setPrice(1.00);
		drugInfoRuleModel1.setBuyAmount(3.0);
		drugInfoRuleModel1.setFirstCategory("中成药");
		drugInfoRuleModels.add(drugInfoRuleModel1);
		DrugInfoRuleModel drugInfoRuleModel2 = new DrugInfoRuleModel();
		drugInfoRuleModel2.setDrugId(2L);
		drugInfoRuleModel2.setCommonName("华佗再造丸");
		drugInfoRuleModel2.setFirstCategory("中成药");
		drugInfoRuleModel2.setPrice(2.00);
		drugInfoRuleModel2.setBuyAmount(3.00);
		drugInfoRuleModels.add(drugInfoRuleModel2);
		orderInfoRuleModel.setDrugInfoList(drugInfoRuleModels);
		ResResult resResult = new ResResult();
		KieSession kieSession = KieServices.Factory.get().getKieClasspathContainer().newKieSession("HL");
		kieSession.insert(orderInfoRuleModel);
		kieSession.setGlobal("resResult", resResult);
		kieSession.fireAllRules(new AgendaFilter() {
			@Override
			public boolean accept(Match match) {
				return match.getRule().getName().startsWith("ChinesePatentMedicineIngredientDuplicate");
			}
		});
		kieSession.dispose();
		assert (orderInfoRuleModel.getDrugInfoList().get(1).getBuyAmount() == 0.0);
	}

}

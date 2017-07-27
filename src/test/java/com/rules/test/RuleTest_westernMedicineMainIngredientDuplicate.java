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
public class RuleTest_westernMedicineMainIngredientDuplicate {

	/**
	 * 西药主要成分重复	类别1为西药的药品	"1.类别1为西药的药品   2.类别7（主要成分）与条件1的药品相同"		禁忌	优先扣除处方中总价高的药品
	 * 西药主要成分重复(西药 mainIngredient duplicate 西药)"
	 */
		@Test
		public void westernMedicineMainIngredientDuplicate_01(){
			OrderInfoRuleModel orderInfoRuleModel = new OrderInfoRuleModel();
			PatientInfoRuleModel patientInfoRuleModel = new PatientInfoRuleModel();
			patientInfoRuleModel.setAge(65);
			patientInfoRuleModel.setPatientId("abc123");
			orderInfoRuleModel.setPatientInfoRuleModel(patientInfoRuleModel);
			orderInfoRuleModel.setCycle(80);
			List<DrugInfoRuleModel> drugInfoRuleModels = Lists.newArrayList();
			DrugInfoRuleModel drugInfoRuleModel1 = new DrugInfoRuleModel();
			drugInfoRuleModel1.setDrugId(1L);
			drugInfoRuleModel1.setCommonName("消渴丸");
			drugInfoRuleModel1.setPrice(1.00);
			drugInfoRuleModel1.setBuyAmount(3.0);
			drugInfoRuleModel1.setFirstCategory("西药");
			drugInfoRuleModel1.setMainIngredients(Lists.newArrayList("冰片"));
			drugInfoRuleModels.add(drugInfoRuleModel1);
			DrugInfoRuleModel drugInfoRuleModel2 = new DrugInfoRuleModel();
			drugInfoRuleModel2.setDrugId(2L);
			drugInfoRuleModel2.setCommonName("消渴丸");
			drugInfoRuleModel2.setPrice(2.00);
			drugInfoRuleModel2.setBuyAmount(3.00);
			drugInfoRuleModel2.setFirstCategory("西药");
			drugInfoRuleModel2.setMainIngredients(Lists.newArrayList("冰片", "盐酸可乐定"));
			drugInfoRuleModels.add(drugInfoRuleModel2);
			orderInfoRuleModel.setDrugInfoList(drugInfoRuleModels);
			ResResult resResult = new ResResult();
			KieSession kieSession = KieServices.Factory.get().getKieClasspathContainer().newKieSession("HL");
			kieSession.insert(orderInfoRuleModel);
			kieSession.setGlobal("resResult", resResult);
			kieSession.fireAllRules(new AgendaFilter() {
				@Override
				public boolean accept(Match match) {
					return match.getRule().getName().startsWith("westernMedicineMainIngredientDuplicate");
				}
			});
			kieSession.dispose();
			assert (orderInfoRuleModel.getDrugInfoList().get(0).getBuyAmount() != 0.0);
			assert (orderInfoRuleModel.getDrugInfoList().get(1).getBuyAmount() == 0.0);
		}
	/**
	 * 西药主要成分重复	类别1为西药	"1.类别1为西药复方制剂    2.类别7（主要成分）与条件1中的药品有相同的成分"		禁忌	优先扣除处方中总价高的药品
	 * 西药主要成分重复(西药 mainIngredient duplicate 西药复方制剂)"
	 */
	@Test
	public void westernMedicineMainIngredientDuplicate_02(){
		OrderInfoRuleModel orderInfoRuleModel = new OrderInfoRuleModel();
		PatientInfoRuleModel patientInfoRuleModel = new PatientInfoRuleModel();
		patientInfoRuleModel.setAge(65);
		patientInfoRuleModel.setPatientId("abc123");
		orderInfoRuleModel.setPatientInfoRuleModel(patientInfoRuleModel);
		orderInfoRuleModel.setCycle(80);
		List<DrugInfoRuleModel> drugInfoRuleModels = Lists.newArrayList();
		DrugInfoRuleModel drugInfoRuleModel1 = new DrugInfoRuleModel();
		drugInfoRuleModel1.setDrugId(1L);
		drugInfoRuleModel1.setCommonName("消渴丸");
		drugInfoRuleModel1.setPrice(1.00);
		drugInfoRuleModel1.setBuyAmount(3.0);
		drugInfoRuleModel1.setFirstCategory("西药");
		drugInfoRuleModel1.setMainIngredients(Lists.newArrayList("冰片"));
		drugInfoRuleModels.add(drugInfoRuleModel1);
		DrugInfoRuleModel drugInfoRuleModel2 = new DrugInfoRuleModel();
		drugInfoRuleModel2.setDrugId(2L);
		drugInfoRuleModel2.setCommonName("消渴丸");
		drugInfoRuleModel2.setPrice(2.00);
		drugInfoRuleModel2.setBuyAmount(3.00);
		drugInfoRuleModel2.setFirstCategory("西药复方制剂");
		drugInfoRuleModel2.setMainIngredients(Lists.newArrayList("冰片", "盐酸可乐定"));
		drugInfoRuleModels.add(drugInfoRuleModel2);
		orderInfoRuleModel.setDrugInfoList(drugInfoRuleModels);
		ResResult resResult = new ResResult();
		KieSession kieSession = KieServices.Factory.get().getKieClasspathContainer().newKieSession("HL");
		kieSession.insert(orderInfoRuleModel);
		kieSession.setGlobal("resResult", resResult);
		DrugInfoRuleModel drugInfoRuleModel3 = new DrugInfoRuleModel();
		drugInfoRuleModel3.setDrugId(3L);
		drugInfoRuleModel3.setCommonName("消渴丸");
		drugInfoRuleModel3.setPrice(2.00);
		drugInfoRuleModel3.setBuyAmount(9.00);
		drugInfoRuleModel3.setFirstCategory("西药复方制剂");
		drugInfoRuleModel3.setMainIngredients(Lists.newArrayList("冰片", "盐酸可乐定"));
		drugInfoRuleModels.add(drugInfoRuleModel3);
		orderInfoRuleModel.setDrugInfoList(drugInfoRuleModels);
		kieSession.insert(orderInfoRuleModel);
		kieSession.setGlobal("resResult", resResult);
		kieSession.fireAllRules(new AgendaFilter() {
			@Override
			public boolean accept(Match match) {
				return match.getRule().getName().startsWith("westernMedicineMainIngredientDuplicate");
			}
		});
		kieSession.dispose();
		assert (orderInfoRuleModel.getDrugInfoList().get(0).getBuyAmount() != 0.0);
		assert (orderInfoRuleModel.getDrugInfoList().get(1).getBuyAmount() == 0.0);
		assert (orderInfoRuleModel.getDrugInfoList().get(2).getBuyAmount() == 0.0);
	}

	/**
	 * 西药主要成分重复	类别1为西药复方制剂	"1.类别1为西药复方制剂    2.类别7（主要成分）与条件1中的药品有相同的成分"		禁忌	优先扣除处方中总价高的药品
	 * 西药主要成分重复(西药复方制剂 mainIngredient duplicate 西药复方制剂)"
	 */
	@Test
	public void westernMedicineMainIngredientDuplicate_03(){
		OrderInfoRuleModel orderInfoRuleModel = new OrderInfoRuleModel();
		PatientInfoRuleModel patientInfoRuleModel = new PatientInfoRuleModel();
		patientInfoRuleModel.setAge(65);
		patientInfoRuleModel.setPatientId("abc123");
		orderInfoRuleModel.setPatientInfoRuleModel(patientInfoRuleModel);
		orderInfoRuleModel.setCycle(80);
		List<DrugInfoRuleModel> drugInfoRuleModels = Lists.newArrayList();
		DrugInfoRuleModel drugInfoRuleModel1 = new DrugInfoRuleModel();
		drugInfoRuleModel1.setDrugId(1L);
		drugInfoRuleModel1.setCommonName("消渴丸");
		drugInfoRuleModel1.setPrice(1.00);
		drugInfoRuleModel1.setBuyAmount(3.0);
		drugInfoRuleModel1.setFirstCategory("西药复方制剂");
		drugInfoRuleModel1.setMainIngredients(Lists.newArrayList("冰片"));
		drugInfoRuleModels.add(drugInfoRuleModel1);
		DrugInfoRuleModel drugInfoRuleModel2 = new DrugInfoRuleModel();
		drugInfoRuleModel2.setDrugId(2L);
		drugInfoRuleModel2.setCommonName("消渴丸");
		drugInfoRuleModel2.setPrice(2.00);
		drugInfoRuleModel2.setBuyAmount(3.00);
		drugInfoRuleModel2.setFirstCategory("西药复方制剂");
		drugInfoRuleModel2.setMainIngredients(Lists.newArrayList("冰片", "盐酸可乐定"));
		drugInfoRuleModels.add(drugInfoRuleModel2);
		orderInfoRuleModel.setDrugInfoList(drugInfoRuleModels);
		ResResult resResult = new ResResult();
		KieSession kieSession = KieServices.Factory.get().getKieClasspathContainer().newKieSession("HL");
		kieSession.insert(orderInfoRuleModel);
		kieSession.setGlobal("resResult", resResult);
		DrugInfoRuleModel drugInfoRuleModel3 = new DrugInfoRuleModel();
		drugInfoRuleModel3.setDrugId(3L);
		drugInfoRuleModel3.setCommonName("消渴丸");
		drugInfoRuleModel3.setPrice(2.00);
		drugInfoRuleModel3.setBuyAmount(1.00);
		drugInfoRuleModel3.setFirstCategory("西药复方制剂");
		drugInfoRuleModel3.setMainIngredients(Lists.newArrayList("冰片", "盐酸可乐定"));
		drugInfoRuleModels.add(drugInfoRuleModel3);
		orderInfoRuleModel.setDrugInfoList(drugInfoRuleModels);
		kieSession.insert(orderInfoRuleModel);
		kieSession.setGlobal("resResult", resResult);
		kieSession.fireAllRules(new AgendaFilter() {
			@Override
			public boolean accept(Match match) {
				return match.getRule().getName().startsWith("westernMedicineMainIngredientDuplicate");
			}
		});
		kieSession.dispose();
		assert (orderInfoRuleModel.getDrugInfoList().get(0).getBuyAmount() == 0.0);
		assert (orderInfoRuleModel.getDrugInfoList().get(1).getBuyAmount() == 0.0);
		assert (orderInfoRuleModel.getDrugInfoList().get(2).getBuyAmount() != 0.0);
	}
}

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
public class RuleTest_CompoundChineseWesternAndWesternMedicine {

	/**
	 * 中西复方制剂与西药制剂成分重复(消渴丸 drug with 格列本脲)
	 */
	@Test
	public void CompoundChineseWesternAndWesternMedicine_01(){
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
		drugInfoRuleModels.add(drugInfoRuleModel1);
		DrugInfoRuleModel drugInfoRuleModel2 = new DrugInfoRuleModel();
		drugInfoRuleModel2.setDrugId(2L);
		drugInfoRuleModel2.setCommonName("其他药品");
		drugInfoRuleModel2.setPrice(2.00);
		drugInfoRuleModel2.setBuyAmount(3.00);
		drugInfoRuleModel2.setFirstCategory("西药");
		drugInfoRuleModel2.setMainIngredients(Lists.newArrayList("格列本脲"));
		drugInfoRuleModels.add(drugInfoRuleModel2);
		orderInfoRuleModel.setDrugInfoList(drugInfoRuleModels);
		ResResult resResult = new ResResult();
		KieSession kieSession = KieServices.Factory.get().getKieClasspathContainer().newKieSession("HL");
		kieSession.insert(orderInfoRuleModel);
		kieSession.setGlobal("resResult", resResult);
		kieSession.fireAllRules(new AgendaFilter() {
			@Override
			public boolean accept(Match match) {
				return match.getRule().getName().startsWith("CompoundChineseWesternAndWesternMedicine");
			}
		});
		kieSession.dispose();
		assert (orderInfoRuleModel.getDrugInfoList().get(0).getBuyAmount() == 0.0);
	}
	/**
	 * 中西复方制剂与西药制剂成分重复(珍菊降压片 drug with 盐酸可乐定)
	 */
	@Test
	public void CompoundChineseWesternAndWesternMedicine_02(){
		OrderInfoRuleModel orderInfoRuleModel = new OrderInfoRuleModel();
		PatientInfoRuleModel patientInfoRuleModel = new PatientInfoRuleModel();
		patientInfoRuleModel.setAge(65);
		patientInfoRuleModel.setPatientId("abc123");
		orderInfoRuleModel.setPatientInfoRuleModel(patientInfoRuleModel);
		orderInfoRuleModel.setCycle(80);
		List<DrugInfoRuleModel> drugInfoRuleModels = Lists.newArrayList();
		DrugInfoRuleModel drugInfoRuleModel1 = new DrugInfoRuleModel();
		drugInfoRuleModel1.setDrugId(1L);
		drugInfoRuleModel1.setCommonName("珍菊降压片");
		drugInfoRuleModel1.setPrice(1.00);
		drugInfoRuleModel1.setBuyAmount(3.0);
		drugInfoRuleModels.add(drugInfoRuleModel1);
		DrugInfoRuleModel drugInfoRuleModel2 = new DrugInfoRuleModel();
		drugInfoRuleModel2.setDrugId(2L);
		drugInfoRuleModel2.setCommonName("其他药品");
		drugInfoRuleModel2.setPrice(2.00);
		drugInfoRuleModel2.setBuyAmount(3.00);
		drugInfoRuleModel2.setFirstCategory("西药");
		drugInfoRuleModel2.setMainIngredients(Lists.newArrayList("盐酸可乐定"));
		drugInfoRuleModels.add(drugInfoRuleModel2);
		orderInfoRuleModel.setDrugInfoList(drugInfoRuleModels);
		ResResult resResult = new ResResult();
		KieSession kieSession = KieServices.Factory.get().getKieClasspathContainer().newKieSession("HL");
		kieSession.insert(orderInfoRuleModel);
		kieSession.setGlobal("resResult", resResult);
		kieSession.fireAllRules(new AgendaFilter() {
			@Override
			public boolean accept(Match match) {
				return match.getRule().getName().startsWith("CompoundChineseWesternAndWesternMedicine");
			}
		});
		kieSession.dispose();
		assert (orderInfoRuleModel.getDrugInfoList().get(0).getBuyAmount() == 0.0);
	}
	/**
	 * 中西复方制剂与西药制剂成分重复(珍菊降压片 drug with 氢氯噻嗪)
	 */
	@Test
	public void CompoundChineseWesternAndWesternMedicine_03(){
		OrderInfoRuleModel orderInfoRuleModel = new OrderInfoRuleModel();
		PatientInfoRuleModel patientInfoRuleModel = new PatientInfoRuleModel();
		patientInfoRuleModel.setAge(65);
		patientInfoRuleModel.setPatientId("abc123");
		orderInfoRuleModel.setPatientInfoRuleModel(patientInfoRuleModel);
		orderInfoRuleModel.setCycle(80);
		List<DrugInfoRuleModel> drugInfoRuleModels = Lists.newArrayList();
		DrugInfoRuleModel drugInfoRuleModel1 = new DrugInfoRuleModel();
		drugInfoRuleModel1.setDrugId(1L);
		drugInfoRuleModel1.setCommonName("珍菊降压片");
		drugInfoRuleModel1.setPrice(1.00);
		drugInfoRuleModel1.setBuyAmount(3.0);
		drugInfoRuleModels.add(drugInfoRuleModel1);
		DrugInfoRuleModel drugInfoRuleModel2 = new DrugInfoRuleModel();
		drugInfoRuleModel2.setDrugId(2L);
		drugInfoRuleModel2.setCommonName("其他药品");
		drugInfoRuleModel2.setPrice(2.00);
		drugInfoRuleModel2.setBuyAmount(3.00);
		drugInfoRuleModel2.setFirstCategory("西药");
		drugInfoRuleModel2.setMainIngredients(Lists.newArrayList("氢氯噻嗪"));
		drugInfoRuleModels.add(drugInfoRuleModel2);
		orderInfoRuleModel.setDrugInfoList(drugInfoRuleModels);
		ResResult resResult = new ResResult();
		KieSession kieSession = KieServices.Factory.get().getKieClasspathContainer().newKieSession("HL");
		kieSession.insert(orderInfoRuleModel);
		kieSession.setGlobal("resResult", resResult);
		kieSession.fireAllRules(new AgendaFilter() {
			@Override
			public boolean accept(Match match) {
				return match.getRule().getName().startsWith("CompoundChineseWesternAndWesternMedicine");
			}
		});
		kieSession.dispose();
		assert (orderInfoRuleModel.getDrugInfoList().get(0).getBuyAmount() == 0.0);
	}
	/**
	 * 中西复方制剂与西药制剂成分重复(二维三七桂利嗪胶囊 drug with 盐酸氟桂利嗪)
	 */
	@Test
	public void CompoundChineseWesternAndWesternMedicine_04(){
		OrderInfoRuleModel orderInfoRuleModel = new OrderInfoRuleModel();
		PatientInfoRuleModel patientInfoRuleModel = new PatientInfoRuleModel();
		patientInfoRuleModel.setAge(65);
		patientInfoRuleModel.setPatientId("abc123");
		orderInfoRuleModel.setPatientInfoRuleModel(patientInfoRuleModel);
		orderInfoRuleModel.setCycle(80);
		List<DrugInfoRuleModel> drugInfoRuleModels = Lists.newArrayList();
		DrugInfoRuleModel drugInfoRuleModel1 = new DrugInfoRuleModel();
		drugInfoRuleModel1.setDrugId(1L);
		drugInfoRuleModel1.setCommonName("二维三七桂利嗪胶囊");
		drugInfoRuleModel1.setPrice(1.00);
		drugInfoRuleModel1.setBuyAmount(3.0);
		drugInfoRuleModels.add(drugInfoRuleModel1);
		DrugInfoRuleModel drugInfoRuleModel2 = new DrugInfoRuleModel();
		drugInfoRuleModel2.setDrugId(2L);
		drugInfoRuleModel2.setCommonName("其他药品");
		drugInfoRuleModel2.setPrice(2.00);
		drugInfoRuleModel2.setBuyAmount(3.00);
		drugInfoRuleModel2.setFirstCategory("西药");
		drugInfoRuleModel2.setMainIngredients(Lists.newArrayList("盐酸氟桂利嗪"));
		drugInfoRuleModels.add(drugInfoRuleModel2);
		orderInfoRuleModel.setDrugInfoList(drugInfoRuleModels);
		ResResult resResult = new ResResult();
		KieSession kieSession = KieServices.Factory.get().getKieClasspathContainer().newKieSession("HL");
		kieSession.insert(orderInfoRuleModel);
		kieSession.setGlobal("resResult", resResult);
		kieSession.fireAllRules(new AgendaFilter() {
			@Override
			public boolean accept(Match match) {
				return match.getRule().getName().startsWith("CompoundChineseWesternAndWesternMedicine");
			}
		});
		kieSession.dispose();
		assert (orderInfoRuleModel.getDrugInfoList().get(0).getBuyAmount() == 0.0);
	}
}

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

import java.io.File;
import java.util.List;

/**
 * Created by admin on 2017/6/2.
 */
public class RulesTest {

		@Test
		public void test01(){
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
			drugInfoRuleModel1.setMainIngredients(Lists.newArrayList("冰片"));
			drugInfoRuleModels.add(drugInfoRuleModel1);
			DrugInfoRuleModel drugInfoRuleModel2 = new DrugInfoRuleModel();
			drugInfoRuleModel2.setDrugId(2L);
			drugInfoRuleModel2.setCommonName("消渴丸");
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
					return match.getRule().getName().startsWith("ageLimit");
				}
			});
			kieSession.dispose();
			System.out.println(resResult.getRuleResultList().size());
		}

	@Test
	public void test02(){
		OrderInfoRuleModel orderInfoRuleModel = new OrderInfoRuleModel();
		PatientInfoRuleModel patientInfoRuleModel = new PatientInfoRuleModel();
		patientInfoRuleModel.setAge(65);
		patientInfoRuleModel.setPatientId("abc123");
		orderInfoRuleModel.setPatientInfoRuleModel(patientInfoRuleModel);
		orderInfoRuleModel.setCycle(80);
		List<DrugInfoRuleModel> drugInfoRuleModels = Lists.newArrayList();
		DrugInfoRuleModel drugInfoRuleModel1 = new DrugInfoRuleModel();
		drugInfoRuleModel1.setDrugId(1L);
		drugInfoRuleModel1.setCommonName("血脂康胶囊");
		drugInfoRuleModel1.setPrice(1.00);
		drugInfoRuleModel1.setBuyAmount(3.0);
		drugInfoRuleModel1.setMainIngredients(Lists.newArrayList("冰片"));
		drugInfoRuleModel1.setFirstCategory("西药");
		drugInfoRuleModels.add(drugInfoRuleModel1);
		DrugInfoRuleModel drugInfoRuleModel2 = new DrugInfoRuleModel();
		drugInfoRuleModel2.setDrugId(2L);
		drugInfoRuleModel2.setCommonName("消渴丸");
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
				return match.getRule().getName().startsWith("ChinesePatentWesternDuplicate");
			}
		});
		kieSession.dispose();
		System.out.println(resResult.getRuleResultList().size());
	}
	@Test
	public void test03(){
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
				return match.getRule().getName().startsWith("ChinesePatentMedicineInGredientDuplicate");
			}
		});
		kieSession.dispose();
		System.out.println(resResult.getRuleResultList().size());
	}

	@Test
	public void test(){
		File  file = new File("d://aab");
		if(file.exists()){
			if(!file.isDirectory()){
				System.out.println("aab的文件已经存在");
			}
		}else{
			file.mkdir();
		}
	}
}

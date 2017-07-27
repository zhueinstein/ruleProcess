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
public class RuleTest_westernMedicineInteraction {
	/**
	 * 西药相互作用	商品名为利福平	类别７（主要成分）中含有硝苯地平		禁忌	优先扣除处方中总价高的药品
	 * 西药相互作用(利福平 drug with 硝苯地平)"
	 */
		@Test
		public void westernMedicineInteraction_01(){
			OrderInfoRuleModel orderInfoRuleModel = new OrderInfoRuleModel();
			PatientInfoRuleModel patientInfoRuleModel = new PatientInfoRuleModel();
			patientInfoRuleModel.setAge(65);
			patientInfoRuleModel.setPatientId("abc123");
			orderInfoRuleModel.setPatientInfoRuleModel(patientInfoRuleModel);
			orderInfoRuleModel.setCycle(80);
			List<DrugInfoRuleModel> drugInfoRuleModels = Lists.newArrayList();
			DrugInfoRuleModel drugInfoRuleModel1 = new DrugInfoRuleModel();
			drugInfoRuleModel1.setDrugId(1L);
			drugInfoRuleModel1.setCommonName("利福平");
			drugInfoRuleModel1.setPrice(1.00);
			drugInfoRuleModel1.setBuyAmount(3.0);
			drugInfoRuleModel1.setMainIngredients(Lists.newArrayList("OTHER"));
			drugInfoRuleModels.add(drugInfoRuleModel1);
			DrugInfoRuleModel drugInfoRuleModel2 = new DrugInfoRuleModel();
			drugInfoRuleModel2.setDrugId(2L);

			drugInfoRuleModel2.setCommonName("OTHER");
			drugInfoRuleModel2.setPrice(2.00);
			drugInfoRuleModel2.setBuyAmount(3.00);
			drugInfoRuleModel2.setMainIngredients(Lists.newArrayList("硝苯地平"));
			drugInfoRuleModels.add(drugInfoRuleModel2);
			orderInfoRuleModel.setDrugInfoList(drugInfoRuleModels);
			ResResult resResult = new ResResult();
			KieSession kieSession = KieServices.Factory.get().getKieClasspathContainer().newKieSession("HL");
			kieSession.insert(orderInfoRuleModel);
			kieSession.setGlobal("resResult", resResult);
			kieSession.fireAllRules(new AgendaFilter() {
				@Override
				public boolean accept(Match match) {
					return match.getRule().getName().startsWith("westernMedicineInteraction");
				}
			});
			kieSession.dispose();
			assert (orderInfoRuleModel.getDrugInfoList().get(0).getBuyAmount() != 0.0);
			assert (orderInfoRuleModel.getDrugInfoList().get(1).getBuyAmount() == 0.0);
		}

    /**
     * 西药相互作用	类别７（主要成分）中含有硫酸氯吡格雷	类别７（主要成分）中含有奥美拉唑		禁忌	优先扣除处方中总价高的药品
     * 西药相互作用(硫酸氯吡格雷 drug with 奥美拉唑)"
     */
    @Test
    public void westernMedicineInteraction_02(){
        OrderInfoRuleModel orderInfoRuleModel = new OrderInfoRuleModel();
        PatientInfoRuleModel patientInfoRuleModel = new PatientInfoRuleModel();
        patientInfoRuleModel.setAge(65);
        patientInfoRuleModel.setPatientId("abc123");
        orderInfoRuleModel.setPatientInfoRuleModel(patientInfoRuleModel);
        orderInfoRuleModel.setCycle(80);
        List<DrugInfoRuleModel> drugInfoRuleModels = Lists.newArrayList();
        DrugInfoRuleModel drugInfoRuleModel1 = new DrugInfoRuleModel();
        drugInfoRuleModel1.setDrugId(1L);
        drugInfoRuleModel1.setCommonName("drug1");
        drugInfoRuleModel1.setPrice(1.00);
        drugInfoRuleModel1.setBuyAmount(3.0);
        drugInfoRuleModel1.setMainIngredients(Lists.newArrayList("硫酸氯吡格雷"));
        drugInfoRuleModels.add(drugInfoRuleModel1);
        DrugInfoRuleModel drugInfoRuleModel2 = new DrugInfoRuleModel();
        drugInfoRuleModel2.setDrugId(2L);

        drugInfoRuleModel2.setCommonName("OTHER");
        drugInfoRuleModel2.setPrice(2.00);
        drugInfoRuleModel2.setBuyAmount(3.00);
        drugInfoRuleModel2.setMainIngredients(Lists.newArrayList("奥美拉唑"));
        drugInfoRuleModels.add(drugInfoRuleModel2);
        orderInfoRuleModel.setDrugInfoList(drugInfoRuleModels);
        ResResult resResult = new ResResult();
        KieSession kieSession = KieServices.Factory.get().getKieClasspathContainer().newKieSession("HL");
        kieSession.insert(orderInfoRuleModel);
        kieSession.setGlobal("resResult", resResult);
        kieSession.fireAllRules(new AgendaFilter() {
            @Override
            public boolean accept(Match match) {
                return match.getRule().getName().startsWith("westernMedicineInteraction");
            }
        });
        kieSession.dispose();
        assert (orderInfoRuleModel.getDrugInfoList().get(0).getBuyAmount() != 0.0);
        assert (orderInfoRuleModel.getDrugInfoList().get(1).getBuyAmount() == 0.0);
    }

    /**
     * 西药相互作用	类别７（主要成分）中含有硫酸氯吡格雷	类别７（主要成分）中含有埃索拉唑		禁忌	优先扣除处方中总价高的药品
     * 西药相互作用(硫酸氯吡格雷 drug with 埃索拉唑)"
     */
    @Test
    public void westernMedicineInteraction_03(){
        OrderInfoRuleModel orderInfoRuleModel = new OrderInfoRuleModel();
        PatientInfoRuleModel patientInfoRuleModel = new PatientInfoRuleModel();
        patientInfoRuleModel.setAge(65);
        patientInfoRuleModel.setPatientId("abc123");
        orderInfoRuleModel.setPatientInfoRuleModel(patientInfoRuleModel);
        orderInfoRuleModel.setCycle(80);
        List<DrugInfoRuleModel> drugInfoRuleModels = Lists.newArrayList();
        DrugInfoRuleModel drugInfoRuleModel1 = new DrugInfoRuleModel();
        drugInfoRuleModel1.setDrugId(1L);
        drugInfoRuleModel1.setCommonName("drug1");
        drugInfoRuleModel1.setPrice(1.40);
        drugInfoRuleModel1.setBuyAmount(3.0);
        drugInfoRuleModel1.setMainIngredients(Lists.newArrayList("硫酸氯吡格雷"));
        drugInfoRuleModels.add(drugInfoRuleModel1);
        DrugInfoRuleModel drugInfoRuleModel2 = new DrugInfoRuleModel();
        drugInfoRuleModel2.setDrugId(2L);

        drugInfoRuleModel2.setCommonName("OTHER");
        drugInfoRuleModel2.setPrice(2.00);
        drugInfoRuleModel2.setBuyAmount(3.00);
        drugInfoRuleModel2.setMainIngredients(Lists.newArrayList("埃索拉唑"));
        drugInfoRuleModels.add(drugInfoRuleModel2);
        orderInfoRuleModel.setDrugInfoList(drugInfoRuleModels);
        ResResult resResult = new ResResult();
        KieSession kieSession = KieServices.Factory.get().getKieClasspathContainer().newKieSession("HL");
        kieSession.insert(orderInfoRuleModel);
        kieSession.setGlobal("resResult", resResult);
        kieSession.fireAllRules(new AgendaFilter() {
            @Override
            public boolean accept(Match match) {
                return match.getRule().getName().startsWith("westernMedicineInteraction");
            }
        });
        kieSession.dispose();
        assert (orderInfoRuleModel.getDrugInfoList().get(0).getBuyAmount() != 0.0);
        assert (orderInfoRuleModel.getDrugInfoList().get(1).getBuyAmount() == 0.0);
    }

    /**
     * 西药相互作用	诊断中含有“不完全房室传导阻滞”或“病窦综合征”或“病态窦房结综合征”	类别７（主要成分）中含有苯磺酸氨氯地平	类别７（主要成分）中含有胺碘酮	禁忌	优先扣除处方中总价高的药品
     * 西药相互作用(苯磺酸氨氯地平 drug with 胺碘酮)
     */
    @Test
    public void westernMedicineInteraction_04(){
        OrderInfoRuleModel orderInfoRuleModel = new OrderInfoRuleModel();
        orderInfoRuleModel.setDiseaseInfoList(Lists.newArrayList("不完全房室传导阻滞","病窦综合征","病态窦房结综合征"));
        PatientInfoRuleModel patientInfoRuleModel = new PatientInfoRuleModel();
        patientInfoRuleModel.setAge(65);
        patientInfoRuleModel.setPatientId("abc123");
        orderInfoRuleModel.setPatientInfoRuleModel(patientInfoRuleModel);
        orderInfoRuleModel.setCycle(80);
        List<DrugInfoRuleModel> drugInfoRuleModels = Lists.newArrayList();
        DrugInfoRuleModel drugInfoRuleModel1 = new DrugInfoRuleModel();
        drugInfoRuleModel1.setDrugId(1L);
        drugInfoRuleModel1.setCommonName("drug1");
        drugInfoRuleModel1.setPrice(1.40);
        drugInfoRuleModel1.setBuyAmount(3.0);
        drugInfoRuleModel1.setMainIngredients(Lists.newArrayList("苯磺酸氨氯地平"));
        drugInfoRuleModels.add(drugInfoRuleModel1);
        DrugInfoRuleModel drugInfoRuleModel2 = new DrugInfoRuleModel();
        drugInfoRuleModel2.setDrugId(2L);

        drugInfoRuleModel2.setCommonName("OTHER");
        drugInfoRuleModel2.setPrice(2.00);
        drugInfoRuleModel2.setBuyAmount(3.00);
        drugInfoRuleModel2.setMainIngredients(Lists.newArrayList("胺碘酮"));
        drugInfoRuleModels.add(drugInfoRuleModel2);
        orderInfoRuleModel.setDrugInfoList(drugInfoRuleModels);
        ResResult resResult = new ResResult();
        KieSession kieSession = KieServices.Factory.get().getKieClasspathContainer().newKieSession("HL");
        kieSession.insert(orderInfoRuleModel);
        kieSession.setGlobal("resResult", resResult);
        kieSession.fireAllRules(new AgendaFilter() {
            @Override
            public boolean accept(Match match) {
                return match.getRule().getName().startsWith("westernMedicineInteraction");
            }
        });
        kieSession.dispose();
        assert (orderInfoRuleModel.getDrugInfoList().get(0).getBuyAmount() != 0.0);
        assert (orderInfoRuleModel.getDrugInfoList().get(1).getBuyAmount() == 0.0);
    }

    /**
     * 西药相互作用	类别７（主要成分）中含有阿法骨化醇	类别７（主要成分）中含有碳酸钙D3		禁忌	优先扣除处方中总价高的药品
     * 西药相互作用(阿法骨化醇 drug with 碳酸钙D3)"
     */
    @Test
    public void westernMedicineInteraction_05(){
        OrderInfoRuleModel orderInfoRuleModel = new OrderInfoRuleModel();
        PatientInfoRuleModel patientInfoRuleModel = new PatientInfoRuleModel();
        patientInfoRuleModel.setAge(65);
        patientInfoRuleModel.setPatientId("abc123");
        orderInfoRuleModel.setPatientInfoRuleModel(patientInfoRuleModel);
        orderInfoRuleModel.setCycle(80);
        List<DrugInfoRuleModel> drugInfoRuleModels = Lists.newArrayList();
        DrugInfoRuleModel drugInfoRuleModel1 = new DrugInfoRuleModel();
        drugInfoRuleModel1.setDrugId(1L);
        drugInfoRuleModel1.setCommonName("drug1");
        drugInfoRuleModel1.setPrice(1.40);
        drugInfoRuleModel1.setBuyAmount(3.0);
        drugInfoRuleModel1.setMainIngredients(Lists.newArrayList("阿法骨化醇"));
        drugInfoRuleModels.add(drugInfoRuleModel1);
        DrugInfoRuleModel drugInfoRuleModel2 = new DrugInfoRuleModel();
        drugInfoRuleModel2.setDrugId(2L);

        drugInfoRuleModel2.setCommonName("OTHER");
        drugInfoRuleModel2.setPrice(2.00);
        drugInfoRuleModel2.setBuyAmount(3.00);
        drugInfoRuleModel2.setMainIngredients(Lists.newArrayList("碳酸钙D3"));
        drugInfoRuleModels.add(drugInfoRuleModel2);
        orderInfoRuleModel.setDrugInfoList(drugInfoRuleModels);
        ResResult resResult = new ResResult();
        KieSession kieSession = KieServices.Factory.get().getKieClasspathContainer().newKieSession("HL");
        kieSession.insert(orderInfoRuleModel);
        kieSession.setGlobal("resResult", resResult);
        kieSession.fireAllRules(new AgendaFilter() {
            @Override
            public boolean accept(Match match) {
                return match.getRule().getName().startsWith("westernMedicineInteraction");
            }
        });
        kieSession.dispose();
        assert (orderInfoRuleModel.getDrugInfoList().get(0).getBuyAmount() != 0.0);
        assert (orderInfoRuleModel.getDrugInfoList().get(1).getBuyAmount() == 0.0);
    }
}

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
public class RuleTest_westernMedicineDuplicate {

	/**
	 * 西药药理作用重复	类别1为西药的药品	"1.类别1为西药的药品；  2.类别4与条件1药品类别4相同；3.类别4不为利尿剂。"		禁忌	优先扣除处方中总价高的药品
	 * 西药药理作用重复(fourth not contains 利尿剂)"
	 */
		@Test
		public void westernMedicineDuplicate_01_positive(){
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
			drugInfoRuleModel1.setFirstCategory("西药");
			drugInfoRuleModel1.setOtherCategory(Lists.newArrayList("not 利尿剂"));
			drugInfoRuleModels.add(drugInfoRuleModel1);
			DrugInfoRuleModel drugInfoRuleModel2 = new DrugInfoRuleModel();
			drugInfoRuleModel2.setDrugId(2L);
			drugInfoRuleModel2.setCommonName("drug02");
			drugInfoRuleModel2.setPrice(2.00);
			drugInfoRuleModel2.setBuyAmount(3.00);
			drugInfoRuleModel2.setFirstCategory("西药");
			drugInfoRuleModel2.setOtherCategory(Lists.newArrayList("not 利尿剂"));
			drugInfoRuleModels.add(drugInfoRuleModel2);
			orderInfoRuleModel.setDrugInfoList(drugInfoRuleModels);
			ResResult resResult = new ResResult();
			KieSession kieSession = KieServices.Factory.get().getKieClasspathContainer().newKieSession("HL");
			kieSession.insert(orderInfoRuleModel);
			kieSession.setGlobal("resResult", resResult);
			kieSession.fireAllRules(new AgendaFilter() {
				@Override
				public boolean accept(Match match) {
					return match.getRule().getName().startsWith("westernMedicineDuplicate");
				}
			});
			kieSession.dispose();
			assert (orderInfoRuleModel.getDrugInfoList().get(0).getBuyAmount() != 0.0);
			assert (orderInfoRuleModel.getDrugInfoList().get(1).getBuyAmount() == 0.0);
		}

    @Test
    public void westernMedicineDuplicate_01_negative(){
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
        drugInfoRuleModel1.setFirstCategory("西药");
        drugInfoRuleModel1.setOtherCategory(Lists.newArrayList("利尿剂"));
        drugInfoRuleModels.add(drugInfoRuleModel1);
        DrugInfoRuleModel drugInfoRuleModel2 = new DrugInfoRuleModel();
        drugInfoRuleModel2.setDrugId(2L);
        drugInfoRuleModel2.setCommonName("drug02");
        drugInfoRuleModel2.setPrice(2.00);
        drugInfoRuleModel2.setBuyAmount(3.00);
        drugInfoRuleModel2.setFirstCategory("西药");
        drugInfoRuleModel2.setOtherCategory(Lists.newArrayList("not 利尿剂"));
        drugInfoRuleModels.add(drugInfoRuleModel2);
        orderInfoRuleModel.setDrugInfoList(drugInfoRuleModels);
        ResResult resResult = new ResResult();
        KieSession kieSession = KieServices.Factory.get().getKieClasspathContainer().newKieSession("HL");
        kieSession.insert(orderInfoRuleModel);
        kieSession.setGlobal("resResult", resResult);
        kieSession.fireAllRules(new AgendaFilter() {
            @Override
            public boolean accept(Match match) {
                return match.getRule().getName().startsWith("westernMedicineDuplicate");
            }
        });
        kieSession.dispose();
        assert (orderInfoRuleModel.getDrugInfoList().get(0).getBuyAmount() != 0.0);
        assert (orderInfoRuleModel.getDrugInfoList().get(1).getBuyAmount() != 0.0);
    }

    /**
     * 西药药理作用重复	类别1为西药的药品	"1.类别1为西药的药品；2.类别5与条件1药品类别5相同；   3.类别5不为袢利尿剂、噻嗪类和类噻嗪类利尿剂。"		禁忌	优先扣除处方中总价高的药品
     * 西药药理作用重复(fifth not contains 袢利尿剂、噻嗪类和类噻嗪类利尿剂)"
     */
    @Test
    public void westernMedicineDuplicate_02_positive(){
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
        drugInfoRuleModel1.setFirstCategory("西药");
        drugInfoRuleModel1.setOtherCategory(Lists.newArrayList("other1","not 袢利尿剂"));
        drugInfoRuleModels.add(drugInfoRuleModel1);
        DrugInfoRuleModel drugInfoRuleModel2 = new DrugInfoRuleModel();
        drugInfoRuleModel2.setDrugId(2L);
        drugInfoRuleModel2.setCommonName("drug02");
        drugInfoRuleModel2.setPrice(2.00);
        drugInfoRuleModel2.setBuyAmount(3.00);
        drugInfoRuleModel2.setFirstCategory("西药");
        drugInfoRuleModel2.setOtherCategory(Lists.newArrayList("other2","not 袢利尿剂"));
        drugInfoRuleModels.add(drugInfoRuleModel2);
        orderInfoRuleModel.setDrugInfoList(drugInfoRuleModels);
        ResResult resResult = new ResResult();
        KieSession kieSession = KieServices.Factory.get().getKieClasspathContainer().newKieSession("HL");
        kieSession.insert(orderInfoRuleModel);
        kieSession.setGlobal("resResult", resResult);
        kieSession.fireAllRules(new AgendaFilter() {
            @Override
            public boolean accept(Match match) {
                return match.getRule().getName().startsWith("westernMedicineDuplicate");
            }
        });
        kieSession.dispose();
        assert (orderInfoRuleModel.getDrugInfoList().get(0).getBuyAmount() != 0.0);
        assert (orderInfoRuleModel.getDrugInfoList().get(1).getBuyAmount() == 0.0);
    }


    @Test
    public void westernMedicineDuplicate_02_negative(){
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
        drugInfoRuleModel1.setFirstCategory("西药");
        drugInfoRuleModel1.setOtherCategory(Lists.newArrayList("other1","袢利尿剂"));
        drugInfoRuleModels.add(drugInfoRuleModel1);
        DrugInfoRuleModel drugInfoRuleModel2 = new DrugInfoRuleModel();
        drugInfoRuleModel2.setDrugId(2L);
        drugInfoRuleModel2.setCommonName("drug02");
        drugInfoRuleModel2.setPrice(2.00);
        drugInfoRuleModel2.setBuyAmount(3.00);
        drugInfoRuleModel2.setFirstCategory("西药");
        drugInfoRuleModel2.setOtherCategory(Lists.newArrayList("other2","袢利尿剂"));
        drugInfoRuleModels.add(drugInfoRuleModel2);
        orderInfoRuleModel.setDrugInfoList(drugInfoRuleModels);
        ResResult resResult = new ResResult();
        KieSession kieSession = KieServices.Factory.get().getKieClasspathContainer().newKieSession("HL");
        kieSession.insert(orderInfoRuleModel);
        kieSession.setGlobal("resResult", resResult);
        kieSession.fireAllRules(new AgendaFilter() {
            @Override
            public boolean accept(Match match) {
                return match.getRule().getName().startsWith("westernMedicineDuplicate");
            }
        });
        kieSession.dispose();
        assert (orderInfoRuleModel.getDrugInfoList().get(0).getBuyAmount() != 0.0);
        assert (orderInfoRuleModel.getDrugInfoList().get(1).getBuyAmount() != 0.0);
    }

    /**
     * 西药药理作用重复	类别1为西药的药品	"1.类别1为西药的药品；    2.类别6与条件1药品类别6相同；    3.类别6不为排钾利尿剂。"		禁忌	优先扣除处方中总价高的药品
     * 西药药理作用重复(sixth not contains 排钾利尿剂)"
     */
    @Test
    public void westernMedicineDuplicate_03_positive(){
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
        drugInfoRuleModel1.setFirstCategory("西药");
        drugInfoRuleModel1.setOtherCategory(Lists.newArrayList("other1","oth1","not 排钾利尿剂"));
        drugInfoRuleModels.add(drugInfoRuleModel1);
        DrugInfoRuleModel drugInfoRuleModel2 = new DrugInfoRuleModel();
        drugInfoRuleModel2.setDrugId(2L);
        drugInfoRuleModel2.setCommonName("drug02");
        drugInfoRuleModel2.setPrice(2.00);
        drugInfoRuleModel2.setBuyAmount(3.00);
        drugInfoRuleModel2.setFirstCategory("西药");
        drugInfoRuleModel2.setOtherCategory(Lists.newArrayList("other2","oth2","not 排钾利尿剂"));
        drugInfoRuleModels.add(drugInfoRuleModel2);
        orderInfoRuleModel.setDrugInfoList(drugInfoRuleModels);
        ResResult resResult = new ResResult();
        KieSession kieSession = KieServices.Factory.get().getKieClasspathContainer().newKieSession("HL");
        kieSession.insert(orderInfoRuleModel);
        kieSession.setGlobal("resResult", resResult);
        kieSession.fireAllRules(new AgendaFilter() {
            @Override
            public boolean accept(Match match) {
                return match.getRule().getName().startsWith("westernMedicineDuplicate");
            }
        });
        kieSession.dispose();
        assert (orderInfoRuleModel.getDrugInfoList().get(0).getBuyAmount() != 0.0);
        assert (orderInfoRuleModel.getDrugInfoList().get(1).getBuyAmount() == 0.0);
    }
    @Test
    public void westernMedicineDuplicate_03_negative(){
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
        drugInfoRuleModel1.setFirstCategory("西药");
        drugInfoRuleModel1.setOtherCategory(Lists.newArrayList("other1","oth1","排钾利尿剂"));
        drugInfoRuleModels.add(drugInfoRuleModel1);
        DrugInfoRuleModel drugInfoRuleModel2 = new DrugInfoRuleModel();
        drugInfoRuleModel2.setDrugId(2L);
        drugInfoRuleModel2.setCommonName("drug02");
        drugInfoRuleModel2.setPrice(2.00);
        drugInfoRuleModel2.setBuyAmount(3.00);
        drugInfoRuleModel2.setFirstCategory("西药");
        drugInfoRuleModel2.setOtherCategory(Lists.newArrayList("other2","oth2","排钾利尿剂"));
        drugInfoRuleModels.add(drugInfoRuleModel2);
        orderInfoRuleModel.setDrugInfoList(drugInfoRuleModels);
        ResResult resResult = new ResResult();
        KieSession kieSession = KieServices.Factory.get().getKieClasspathContainer().newKieSession("HL");
        kieSession.insert(orderInfoRuleModel);
        kieSession.setGlobal("resResult", resResult);
        kieSession.fireAllRules(new AgendaFilter() {
            @Override
            public boolean accept(Match match) {
                return match.getRule().getName().startsWith("westernMedicineDuplicate");
            }
        });
        kieSession.dispose();
        assert (orderInfoRuleModel.getDrugInfoList().get(0).getBuyAmount() != 0.0);
        assert (orderInfoRuleModel.getDrugInfoList().get(1).getBuyAmount() != 0.0);
    }
    /**
     * 西药药理作用重复	类别1为西药的药品	"1.类别1为西药的药品；   2.类别4与条件1药品类别4相同；   3.类别4为利尿剂。"	诊断中含有“心功能不全”或“心力衰竭”或“心衰”或“慢性肾功能不全”或“尿毒症”或“慢性肾衰”	慎用	提示
     * 西药药理作用重复(fourth contains 利尿剂)"
     */
    @Test
    public void westernMedicineDuplicate_04(){
        OrderInfoRuleModel orderInfoRuleModel = new OrderInfoRuleModel();
        orderInfoRuleModel.setDiseaseInfoList(Lists.newArrayList("心功能不全", "心衰"));
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
        drugInfoRuleModel1.setFirstCategory("西药");
        drugInfoRuleModel1.setOtherCategory(Lists.newArrayList("利尿剂"));
        drugInfoRuleModels.add(drugInfoRuleModel1);
        DrugInfoRuleModel drugInfoRuleModel2 = new DrugInfoRuleModel();
        drugInfoRuleModel2.setDrugId(2L);
        drugInfoRuleModel2.setCommonName("drug02");
        drugInfoRuleModel2.setPrice(2.00);
        drugInfoRuleModel2.setBuyAmount(3.00);
        drugInfoRuleModel2.setFirstCategory("西药");
        drugInfoRuleModel2.setOtherCategory(Lists.newArrayList("not 利尿剂"));
        drugInfoRuleModels.add(drugInfoRuleModel2);
        orderInfoRuleModel.setDrugInfoList(drugInfoRuleModels);
        ResResult resResult = new ResResult();
        KieSession kieSession = KieServices.Factory.get().getKieClasspathContainer().newKieSession("HL");
        kieSession.insert(orderInfoRuleModel);
        kieSession.setGlobal("resResult", resResult);
        kieSession.fireAllRules(new AgendaFilter() {
            @Override
            public boolean accept(Match match) {
                return match.getRule().getName().startsWith("westernMedicineDuplicate");
            }
        });
        kieSession.dispose();
        assert (orderInfoRuleModel.getDrugInfoList().get(0).getBuyAmount() != 0.0);
        assert (resResult.getRuleResultList().get(0).getRuleNo().contains("慎用"));
        assert (orderInfoRuleModel.getDrugInfoList().get(1).getBuyAmount() != 0.0);
        assert (resResult.getRuleResultList().get(1).getRuleNo().contains("慎用"));
    }

    /**
     * 西药药理作用重复	类别1为西药的药品	"1.类别1为西药的药品；   2.类别5与条件1药品类别5相同；   3.类别5为袢利尿剂或噻嗪类或类噻嗪类利尿剂。"	诊断中含有“心功能不全”或“心力衰竭”或“心衰”或“慢性肾功能不全”或“尿毒症”或“慢性肾衰”	慎用	提示
     * 西药药理作用重复(fifth contains 袢利尿剂或噻嗪类或类噻嗪类利尿剂)"
     */
    @Test
    public void westernMedicineDuplicate_05(){
        OrderInfoRuleModel orderInfoRuleModel = new OrderInfoRuleModel();
        orderInfoRuleModel.setDiseaseInfoList(Lists.newArrayList("心功能不全"));
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
        drugInfoRuleModel1.setFirstCategory("西药");
        drugInfoRuleModel1.setOtherCategory(Lists.newArrayList("oth1", "袢利尿剂"));
        drugInfoRuleModels.add(drugInfoRuleModel1);
        DrugInfoRuleModel drugInfoRuleModel2 = new DrugInfoRuleModel();
        drugInfoRuleModel2.setDrugId(2L);
        drugInfoRuleModel2.setCommonName("drug02");
        drugInfoRuleModel2.setPrice(2.00);
        drugInfoRuleModel2.setBuyAmount(3.00);
        drugInfoRuleModel2.setFirstCategory("西药");
        drugInfoRuleModel2.setOtherCategory(Lists.newArrayList("not ", "袢利尿剂"));
        drugInfoRuleModels.add(drugInfoRuleModel2);
        orderInfoRuleModel.setDrugInfoList(drugInfoRuleModels);
        ResResult resResult = new ResResult();
        KieSession kieSession = KieServices.Factory.get().getKieClasspathContainer().newKieSession("HL");
        kieSession.insert(orderInfoRuleModel);
        kieSession.setGlobal("resResult", resResult);
        kieSession.fireAllRules(new AgendaFilter() {
            @Override
            public boolean accept(Match match) {
                return match.getRule().getName().startsWith("westernMedicineDuplicate");
            }
        });
        kieSession.dispose();
        assert (orderInfoRuleModel.getDrugInfoList().get(0).getBuyAmount() != 0.0);
        assert (resResult.getRuleResultList().get(0).getRuleNo().contains("慎用"));
        assert (orderInfoRuleModel.getDrugInfoList().get(1).getBuyAmount() != 0.0);
        assert (resResult.getRuleResultList().get(1).getRuleNo().contains("慎用"));
    }
    /**
     * 西药药理作用重复	类别1为西药的药品	"1.类别1为西药的药品；   2.类别6与条件1药品类别6相同；   3.类别6为排钾利尿剂。"	诊断中含有“心功能不全”或“心力衰竭”或“心衰”或“慢性肾功能不全”或“尿毒症”或“慢性肾衰”	慎用	提示
     * 西药药理作用重复(sixth contains 排钾利尿剂)"
     */
    @Test
    public void westernMedicineDuplicate_06(){
        OrderInfoRuleModel orderInfoRuleModel = new OrderInfoRuleModel();
        orderInfoRuleModel.setDiseaseInfoList(Lists.newArrayList("心功能不全"));
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
        drugInfoRuleModel1.setFirstCategory("西药");
        drugInfoRuleModel1.setOtherCategory(Lists.newArrayList("oth1", "oth2", "排钾利尿剂"));
        drugInfoRuleModels.add(drugInfoRuleModel1);
        DrugInfoRuleModel drugInfoRuleModel2 = new DrugInfoRuleModel();
        drugInfoRuleModel2.setDrugId(2L);
        drugInfoRuleModel2.setCommonName("drug02");
        drugInfoRuleModel2.setPrice(2.00);
        drugInfoRuleModel2.setBuyAmount(3.00);
        drugInfoRuleModel2.setFirstCategory("西药");
        drugInfoRuleModel2.setOtherCategory(Lists.newArrayList("not ", "not2", "排钾利尿剂"));
        drugInfoRuleModels.add(drugInfoRuleModel2);
        orderInfoRuleModel.setDrugInfoList(drugInfoRuleModels);
        ResResult resResult = new ResResult();
        KieSession kieSession = KieServices.Factory.get().getKieClasspathContainer().newKieSession("HL");
        kieSession.insert(orderInfoRuleModel);
        kieSession.setGlobal("resResult", resResult);
        kieSession.fireAllRules(new AgendaFilter() {
            @Override
            public boolean accept(Match match) {
                return match.getRule().getName().startsWith("westernMedicineDuplicate");
            }
        });
        kieSession.dispose();
        assert (orderInfoRuleModel.getDrugInfoList().get(0).getBuyAmount() != 0.0);
        assert (resResult.getRuleResultList().get(0).getRuleNo().contains("慎用"));
        assert (orderInfoRuleModel.getDrugInfoList().get(1).getBuyAmount() != 0.0);
        assert (resResult.getRuleResultList().get(1).getRuleNo().contains("慎用"));
    }

}

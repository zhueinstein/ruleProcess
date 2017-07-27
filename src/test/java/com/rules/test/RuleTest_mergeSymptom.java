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
public class RuleTest_mergeSymptom {

	/**
	 * 合并症用药不合理(高血压+糖尿病+哮喘 drug with β受体阻滞剂)
	 */
		@Test
		public void mergeSymptom_01(){
			OrderInfoRuleModel orderInfoRuleModel = new OrderInfoRuleModel();
			PatientInfoRuleModel patientInfoRuleModel = new PatientInfoRuleModel();
			orderInfoRuleModel.setDiseaseInfoList(Lists.newArrayList("高血压","糖尿病","哮喘"));
			patientInfoRuleModel.setAge(65);
			patientInfoRuleModel.setPatientId("abc123");
			orderInfoRuleModel.setPatientInfoRuleModel(patientInfoRuleModel);
			orderInfoRuleModel.setCycle(80);
			List<DrugInfoRuleModel> drugInfoRuleModels = Lists.newArrayList();
			DrugInfoRuleModel drugInfoRuleModel1 = new DrugInfoRuleModel();
			drugInfoRuleModel1.setDrugId(1L);
			drugInfoRuleModel1.setCommonName("药品01");
			drugInfoRuleModel1.setPrice(1.00);
			drugInfoRuleModel1.setBuyAmount(3.0);
			drugInfoRuleModel1.setOtherCategory(Lists.newArrayList("第四类","β受体阻滞剂","someOtherCategory"));
			drugInfoRuleModels.add(drugInfoRuleModel1);
			DrugInfoRuleModel drugInfoRuleModel2 = new DrugInfoRuleModel();
			drugInfoRuleModel2.setDrugId(2L);
			drugInfoRuleModel2.setCommonName("药品02");
			drugInfoRuleModel2.setPrice(2.00);
			drugInfoRuleModel2.setBuyAmount(3.00);
			drugInfoRuleModel2.setOtherCategory(Lists.newArrayList("第四类","β受体阻滞剂","someOtherCategory"));
			drugInfoRuleModels.add(drugInfoRuleModel2);
			orderInfoRuleModel.setDrugInfoList(drugInfoRuleModels);
			ResResult resResult = new ResResult();
			KieSession kieSession = KieServices.Factory.get().getKieClasspathContainer().newKieSession("HL");
			kieSession.insert(orderInfoRuleModel);
			kieSession.setGlobal("resResult", resResult);
			kieSession.fireAllRules(new AgendaFilter() {
				@Override
				public boolean accept(Match match) {
					return match.getRule().getName().startsWith("mergeSymptom");
				}
			});
			kieSession.dispose();
			assert (orderInfoRuleModel.getDrugInfoList().get(0).getBuyAmount() == 0.0);
			assert (orderInfoRuleModel.getDrugInfoList().get(1).getBuyAmount() == 0.0);
		}
    /**
     * 合并症用药不合理(高血压+痛风发作期 drug with 排钾利尿剂)
     */
    @Test
    public void mergeSymptom_02(){
        OrderInfoRuleModel orderInfoRuleModel = new OrderInfoRuleModel();
        PatientInfoRuleModel patientInfoRuleModel = new PatientInfoRuleModel();
        orderInfoRuleModel.setDiseaseInfoList(Lists.newArrayList("高血压","痛风发作期"));
        patientInfoRuleModel.setAge(65);
        patientInfoRuleModel.setPatientId("abc123");
        orderInfoRuleModel.setPatientInfoRuleModel(patientInfoRuleModel);
        orderInfoRuleModel.setCycle(80);
        List<DrugInfoRuleModel> drugInfoRuleModels = Lists.newArrayList();
        DrugInfoRuleModel drugInfoRuleModel1 = new DrugInfoRuleModel();
        drugInfoRuleModel1.setDrugId(1L);
        drugInfoRuleModel1.setCommonName("药品01");
        drugInfoRuleModel1.setPrice(1.00);
        drugInfoRuleModel1.setBuyAmount(3.0);
        drugInfoRuleModel1.setOtherCategory(Lists.newArrayList("第四类","other","排钾利尿剂"));
        drugInfoRuleModels.add(drugInfoRuleModel1);
        DrugInfoRuleModel drugInfoRuleModel2 = new DrugInfoRuleModel();
        drugInfoRuleModel2.setDrugId(2L);
        drugInfoRuleModel2.setCommonName("药品02");
        drugInfoRuleModel2.setPrice(2.00);
        drugInfoRuleModel2.setBuyAmount(3.00);
        drugInfoRuleModel2.setOtherCategory(Lists.newArrayList("第四类","other","排钾利尿剂"));
        drugInfoRuleModels.add(drugInfoRuleModel2);
        orderInfoRuleModel.setDrugInfoList(drugInfoRuleModels);
        ResResult resResult = new ResResult();
        KieSession kieSession = KieServices.Factory.get().getKieClasspathContainer().newKieSession("HL");
        kieSession.insert(orderInfoRuleModel);
        kieSession.setGlobal("resResult", resResult);
        kieSession.fireAllRules(new AgendaFilter() {
            @Override
            public boolean accept(Match match) {
                return match.getRule().getName().startsWith("mergeSymptom");
            }
        });
        kieSession.dispose();
        assert (orderInfoRuleModel.getDrugInfoList().get(0).getBuyAmount() == 0.0);
        assert (orderInfoRuleModel.getDrugInfoList().get(1).getBuyAmount() == 0.0);
    }

    /**
     * 合并症用药不合理(高血压+痛风发作期 drug with 氨苯蝶啶)
     */
    @Test
    public void mergeSymptom_03(){
        OrderInfoRuleModel orderInfoRuleModel = new OrderInfoRuleModel();
        PatientInfoRuleModel patientInfoRuleModel = new PatientInfoRuleModel();
        orderInfoRuleModel.setDiseaseInfoList(Lists.newArrayList("高血压","痛风发作期"));
        patientInfoRuleModel.setAge(65);
        patientInfoRuleModel.setPatientId("abc123");
        orderInfoRuleModel.setPatientInfoRuleModel(patientInfoRuleModel);
        orderInfoRuleModel.setCycle(80);
        List<DrugInfoRuleModel> drugInfoRuleModels = Lists.newArrayList();
        DrugInfoRuleModel drugInfoRuleModel1 = new DrugInfoRuleModel();
        drugInfoRuleModel1.setDrugId(1L);
        drugInfoRuleModel1.setCommonName("药品01");
        drugInfoRuleModel1.setPrice(1.00);
        drugInfoRuleModel1.setBuyAmount(3.0);
        drugInfoRuleModel1.setFirstCategory("西药复方制剂");
        drugInfoRuleModel1.setMainIngredients(Lists.newArrayList("氨苯蝶啶"));
        drugInfoRuleModels.add(drugInfoRuleModel1);
        DrugInfoRuleModel drugInfoRuleModel2 = new DrugInfoRuleModel();
        drugInfoRuleModel2.setDrugId(2L);
        drugInfoRuleModel2.setCommonName("药品02");
        drugInfoRuleModel2.setPrice(2.00);
        drugInfoRuleModel2.setBuyAmount(3.00);
        drugInfoRuleModel2.setFirstCategory("西药复方制剂");
        drugInfoRuleModel2.setMainIngredients(Lists.newArrayList("氨苯蝶啶","other"));
        drugInfoRuleModels.add(drugInfoRuleModel2);
        orderInfoRuleModel.setDrugInfoList(drugInfoRuleModels);
        ResResult resResult = new ResResult();
        KieSession kieSession = KieServices.Factory.get().getKieClasspathContainer().newKieSession("HL");
        kieSession.insert(orderInfoRuleModel);
        kieSession.setGlobal("resResult", resResult);
        kieSession.fireAllRules(new AgendaFilter() {
            @Override
            public boolean accept(Match match) {
                return match.getRule().getName().startsWith("mergeSymptom");
            }
        });
        kieSession.dispose();
        assert (orderInfoRuleModel.getDrugInfoList().get(0).getBuyAmount() == 0.0);
        assert (orderInfoRuleModel.getDrugInfoList().get(1).getBuyAmount() == 0.0);
    }

    /**
     * 合并症用药不合理	诊断中含有高血压+痛风发作期	"1.类别１为西药复方制剂；  2.类别７（主要成分）中含有氢氯噻嗪。"		禁忌	扣除此药品
     * 合并症用药不合理(高血压+痛风发作期 drug with 氢氯噻嗪)
     */
    @Test
    public void mergeSymptom_04(){
        OrderInfoRuleModel orderInfoRuleModel = new OrderInfoRuleModel();
        PatientInfoRuleModel patientInfoRuleModel = new PatientInfoRuleModel();
        orderInfoRuleModel.setDiseaseInfoList(Lists.newArrayList("高血压","痛风发作期"));
        patientInfoRuleModel.setAge(65);
        patientInfoRuleModel.setPatientId("abc123");
        orderInfoRuleModel.setPatientInfoRuleModel(patientInfoRuleModel);
        orderInfoRuleModel.setCycle(80);
        List<DrugInfoRuleModel> drugInfoRuleModels = Lists.newArrayList();
        DrugInfoRuleModel drugInfoRuleModel1 = new DrugInfoRuleModel();
        drugInfoRuleModel1.setDrugId(1L);
        drugInfoRuleModel1.setCommonName("药品01");
        drugInfoRuleModel1.setPrice(1.00);
        drugInfoRuleModel1.setBuyAmount(3.0);
        drugInfoRuleModel1.setFirstCategory("西药复方制剂");
        drugInfoRuleModel1.setMainIngredients(Lists.newArrayList("氢氯噻嗪"));
        drugInfoRuleModels.add(drugInfoRuleModel1);
        DrugInfoRuleModel drugInfoRuleModel2 = new DrugInfoRuleModel();
        drugInfoRuleModel2.setDrugId(2L);
        drugInfoRuleModel2.setCommonName("药品02");
        drugInfoRuleModel2.setPrice(2.00);
        drugInfoRuleModel2.setBuyAmount(3.00);
        drugInfoRuleModel2.setFirstCategory("西药复方制剂");
        drugInfoRuleModel2.setMainIngredients(Lists.newArrayList("氨苯蝶啶","氢氯噻嗪"));
        drugInfoRuleModels.add(drugInfoRuleModel2);
        orderInfoRuleModel.setDrugInfoList(drugInfoRuleModels);
        ResResult resResult = new ResResult();
        KieSession kieSession = KieServices.Factory.get().getKieClasspathContainer().newKieSession("HL");
        kieSession.insert(orderInfoRuleModel);
        kieSession.setGlobal("resResult", resResult);
        kieSession.fireAllRules(new AgendaFilter() {
            @Override
            public boolean accept(Match match) {
                return match.getRule().getName().startsWith("mergeSymptom");
            }
        });
        kieSession.dispose();
        assert (orderInfoRuleModel.getDrugInfoList().get(0).getBuyAmount() == 0.0);
        assert (orderInfoRuleModel.getDrugInfoList().get(1).getBuyAmount() == 0.0);
    }

    /**
     * 合并症用药不合理	诊断中含有高血压+高尿酸血症	类别６为排钾利尿剂的药品		禁忌	扣除此药品
     * 合并症用药不合理(高血压+高尿酸血症 drug with 排钾利尿剂)"
     */
    @Test
    public void mergeSymptom_05(){
        OrderInfoRuleModel orderInfoRuleModel = new OrderInfoRuleModel();
        PatientInfoRuleModel patientInfoRuleModel = new PatientInfoRuleModel();
        orderInfoRuleModel.setDiseaseInfoList(Lists.newArrayList("高血压","高尿酸血症"));
        patientInfoRuleModel.setAge(65);
        patientInfoRuleModel.setPatientId("abc123");
        orderInfoRuleModel.setPatientInfoRuleModel(patientInfoRuleModel);
        orderInfoRuleModel.setCycle(80);
        List<DrugInfoRuleModel> drugInfoRuleModels = Lists.newArrayList();
        DrugInfoRuleModel drugInfoRuleModel1 = new DrugInfoRuleModel();
        drugInfoRuleModel1.setDrugId(1L);
        drugInfoRuleModel1.setCommonName("药品01");
        drugInfoRuleModel1.setPrice(1.00);
        drugInfoRuleModel1.setBuyAmount(3.0);
        drugInfoRuleModel1.setOtherCategory(Lists.newArrayList("one", "two","排钾利尿剂"));
        drugInfoRuleModels.add(drugInfoRuleModel1);
        DrugInfoRuleModel drugInfoRuleModel2 = new DrugInfoRuleModel();
        drugInfoRuleModel2.setDrugId(2L);
        drugInfoRuleModel2.setCommonName("药品02");
        drugInfoRuleModel2.setPrice(2.00);
        drugInfoRuleModel2.setBuyAmount(3.00);
        drugInfoRuleModel2.setOtherCategory(Lists.newArrayList("other","排钾利尿剂"));
        drugInfoRuleModels.add(drugInfoRuleModel2);
        orderInfoRuleModel.setDrugInfoList(drugInfoRuleModels);
        ResResult resResult = new ResResult();
        KieSession kieSession = KieServices.Factory.get().getKieClasspathContainer().newKieSession("HL");
        kieSession.insert(orderInfoRuleModel);
        kieSession.setGlobal("resResult", resResult);
        kieSession.fireAllRules(new AgendaFilter() {
            @Override
            public boolean accept(Match match) {
                return match.getRule().getName().startsWith("mergeSymptom");
            }
        });
        kieSession.dispose();
        assert (orderInfoRuleModel.getDrugInfoList().get(0).getBuyAmount() == 0.0);
        assert (orderInfoRuleModel.getDrugInfoList().get(1).getBuyAmount() == 0.0);
    }
    /**
     *  合并症用药不合理	诊断中含有高血压+高尿酸血症	"1.类别１为西药复方制剂；   2.类别７（主要成分）中含有氨苯蝶啶。"		禁忌	扣除此药品
     *  合并症用药不合理(高血压+高尿酸血症 drug with 氨苯蝶啶)"
     */
    @Test
    public void mergeSymptom_06(){
        OrderInfoRuleModel orderInfoRuleModel = new OrderInfoRuleModel();
        PatientInfoRuleModel patientInfoRuleModel = new PatientInfoRuleModel();
        orderInfoRuleModel.setDiseaseInfoList(Lists.newArrayList("高血压","高尿酸血症"));
        patientInfoRuleModel.setAge(65);
        patientInfoRuleModel.setPatientId("abc123");
        orderInfoRuleModel.setPatientInfoRuleModel(patientInfoRuleModel);
        orderInfoRuleModel.setCycle(80);
        List<DrugInfoRuleModel> drugInfoRuleModels = Lists.newArrayList();
        DrugInfoRuleModel drugInfoRuleModel1 = new DrugInfoRuleModel();
        drugInfoRuleModel1.setDrugId(1L);
        drugInfoRuleModel1.setCommonName("药品01");
        drugInfoRuleModel1.setPrice(1.00);
        drugInfoRuleModel1.setBuyAmount(3.0);
        drugInfoRuleModel1.setFirstCategory("西药复方制剂");
        drugInfoRuleModel1.setMainIngredients(Lists.newArrayList("one", "two","氨苯蝶啶"));
        drugInfoRuleModels.add(drugInfoRuleModel1);
        DrugInfoRuleModel drugInfoRuleModel2 = new DrugInfoRuleModel();
        drugInfoRuleModel2.setDrugId(2L);
        drugInfoRuleModel2.setCommonName("药品02");
        drugInfoRuleModel2.setPrice(2.00);
        drugInfoRuleModel2.setBuyAmount(3.00);
        drugInfoRuleModel2.setFirstCategory("西药复方制剂");
        drugInfoRuleModel2.setMainIngredients(Lists.newArrayList("other","排钾利尿剂"));
        drugInfoRuleModels.add(drugInfoRuleModel2);
        orderInfoRuleModel.setDrugInfoList(drugInfoRuleModels);
        ResResult resResult = new ResResult();
        KieSession kieSession = KieServices.Factory.get().getKieClasspathContainer().newKieSession("HL");
        kieSession.insert(orderInfoRuleModel);
        kieSession.setGlobal("resResult", resResult);
        kieSession.fireAllRules(new AgendaFilter() {
            @Override
            public boolean accept(Match match) {
                return match.getRule().getName().startsWith("mergeSymptom");
            }
        });
        kieSession.dispose();
        assert (orderInfoRuleModel.getDrugInfoList().get(0).getBuyAmount() == 0.0);
        assert (orderInfoRuleModel.getDrugInfoList().get(1).getBuyAmount() != 0.0);
    }

    /**
     *  合并症用药不合理	诊断中含有高血压+高尿酸血症	"1.类别１为西药复方制剂；   2.类别７（主要成分）中含有氢氯噻嗪。"		禁忌	扣除此药品
     *  合并症用药不合理(高血压+高尿酸血症 drug with 氢氯噻嗪)"
     */
    @Test
    public void mergeSymptom_07(){
        OrderInfoRuleModel orderInfoRuleModel = new OrderInfoRuleModel();
        PatientInfoRuleModel patientInfoRuleModel = new PatientInfoRuleModel();
        orderInfoRuleModel.setDiseaseInfoList(Lists.newArrayList("高血压","高尿酸血症"));
        patientInfoRuleModel.setAge(65);
        patientInfoRuleModel.setPatientId("abc123");
        orderInfoRuleModel.setPatientInfoRuleModel(patientInfoRuleModel);
        orderInfoRuleModel.setCycle(80);
        List<DrugInfoRuleModel> drugInfoRuleModels = Lists.newArrayList();
        DrugInfoRuleModel drugInfoRuleModel1 = new DrugInfoRuleModel();
        drugInfoRuleModel1.setDrugId(1L);
        drugInfoRuleModel1.setCommonName("药品01");
        drugInfoRuleModel1.setPrice(1.00);
        drugInfoRuleModel1.setBuyAmount(3.0);
        drugInfoRuleModel1.setFirstCategory("西药复方制剂");
        drugInfoRuleModel1.setMainIngredients(Lists.newArrayList("one", "two","氢氯噻嗪"));
        drugInfoRuleModels.add(drugInfoRuleModel1);
        DrugInfoRuleModel drugInfoRuleModel2 = new DrugInfoRuleModel();
        drugInfoRuleModel2.setDrugId(2L);
        drugInfoRuleModel2.setCommonName("药品02");
        drugInfoRuleModel2.setPrice(2.00);
        drugInfoRuleModel2.setBuyAmount(3.00);
        drugInfoRuleModel2.setFirstCategory("西药复方制剂");
        drugInfoRuleModel2.setMainIngredients(Lists.newArrayList("other","排钾利尿剂"));
        drugInfoRuleModels.add(drugInfoRuleModel2);
        orderInfoRuleModel.setDrugInfoList(drugInfoRuleModels);
        ResResult resResult = new ResResult();
        KieSession kieSession = KieServices.Factory.get().getKieClasspathContainer().newKieSession("HL");
        kieSession.insert(orderInfoRuleModel);
        kieSession.setGlobal("resResult", resResult);
        kieSession.fireAllRules(new AgendaFilter() {
            @Override
            public boolean accept(Match match) {
                return match.getRule().getName().startsWith("mergeSymptom");
            }
        });
        kieSession.dispose();
        assert (orderInfoRuleModel.getDrugInfoList().get(0).getBuyAmount() == 0.0);
        assert (orderInfoRuleModel.getDrugInfoList().get(1).getBuyAmount() != 0.0);
    }
}

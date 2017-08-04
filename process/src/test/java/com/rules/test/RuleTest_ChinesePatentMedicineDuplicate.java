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
public class RuleTest_ChinesePatentMedicineDuplicate {

	/**
	 * 中成药功效类别重复(脑心通胶囊 with 利脑心胶囊)
	 */
		@Test
		public void ChinesePatentMedicineDuplicate_01(){
			OrderInfoRuleModel orderInfoRuleModel = new OrderInfoRuleModel();
			PatientInfoRuleModel patientInfoRuleModel = new PatientInfoRuleModel();
			patientInfoRuleModel.setAge(65);
			patientInfoRuleModel.setPatientId("abc123");
			orderInfoRuleModel.setPatientInfoRuleModel(patientInfoRuleModel);
			orderInfoRuleModel.setCycle(80);
			List<DrugInfoRuleModel> drugInfoRuleModels = Lists.newArrayList();
			DrugInfoRuleModel drugInfoRuleModel1 = new DrugInfoRuleModel();
			drugInfoRuleModel1.setDrugId(1L);
			drugInfoRuleModel1.setCommonName("脑心通胶囊");
			drugInfoRuleModel1.setPrice(1.00);
			drugInfoRuleModel1.setBuyAmount(3.0);
			drugInfoRuleModels.add(drugInfoRuleModel1);
			DrugInfoRuleModel drugInfoRuleModel2 = new DrugInfoRuleModel();
			drugInfoRuleModel2.setDrugId(2L);
			drugInfoRuleModel2.setCommonName("利脑心胶囊");
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
					return match.getRule().getName().startsWith("ChinesePatentMedicineDuplicate");
				}
			});
			kieSession.dispose();
			assert (orderInfoRuleModel.getDrugInfoList().get(1).getBuyAmount() == 0.0);
		}
    /**
	 * 中成药功效类别重复(脑心通胶囊 with 血塞通软胶囊)
	 */
		@Test
		public void ChinesePatentMedicineDuplicate_02(){
			OrderInfoRuleModel orderInfoRuleModel = new OrderInfoRuleModel();
			PatientInfoRuleModel patientInfoRuleModel = new PatientInfoRuleModel();
			patientInfoRuleModel.setAge(65);
			patientInfoRuleModel.setPatientId("abc123");
			orderInfoRuleModel.setPatientInfoRuleModel(patientInfoRuleModel);
			orderInfoRuleModel.setCycle(80);
			List<DrugInfoRuleModel> drugInfoRuleModels = Lists.newArrayList();
			DrugInfoRuleModel drugInfoRuleModel1 = new DrugInfoRuleModel();
			drugInfoRuleModel1.setDrugId(1L);
			drugInfoRuleModel1.setCommonName("脑心通胶囊");
			drugInfoRuleModel1.setPrice(1.00);
			drugInfoRuleModel1.setBuyAmount(3.0);
			drugInfoRuleModels.add(drugInfoRuleModel1);
			DrugInfoRuleModel drugInfoRuleModel2 = new DrugInfoRuleModel();
			drugInfoRuleModel2.setDrugId(2L);
			drugInfoRuleModel2.setCommonName("血塞通软胶囊");
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
					return match.getRule().getName().startsWith("ChinesePatentMedicineDuplicate");
				}
			});
			kieSession.dispose();
			assert (orderInfoRuleModel.getDrugInfoList().get(1).getBuyAmount() == 0.0);
		}
    /**
	 * 中成药功效类别重复(脑心通胶囊 with 血塞通片)
	 */
		@Test
		public void ChinesePatentMedicineDuplicate_03(){
			OrderInfoRuleModel orderInfoRuleModel = new OrderInfoRuleModel();
			PatientInfoRuleModel patientInfoRuleModel = new PatientInfoRuleModel();
			patientInfoRuleModel.setAge(65);
			patientInfoRuleModel.setPatientId("abc123");
			orderInfoRuleModel.setPatientInfoRuleModel(patientInfoRuleModel);
			orderInfoRuleModel.setCycle(80);
			List<DrugInfoRuleModel> drugInfoRuleModels = Lists.newArrayList();
			DrugInfoRuleModel drugInfoRuleModel1 = new DrugInfoRuleModel();
			drugInfoRuleModel1.setDrugId(1L);
			drugInfoRuleModel1.setCommonName("脑心通胶囊");
			drugInfoRuleModel1.setPrice(1.00);
			drugInfoRuleModel1.setBuyAmount(3.0);
			drugInfoRuleModels.add(drugInfoRuleModel1);
			DrugInfoRuleModel drugInfoRuleModel2 = new DrugInfoRuleModel();
			drugInfoRuleModel2.setDrugId(2L);
			drugInfoRuleModel2.setCommonName("血塞通片");
			drugInfoRuleModel2.setPrice(2.00);
			drugInfoRuleModel2.setBuyAmount(4.00);
			drugInfoRuleModels.add(drugInfoRuleModel2);
			orderInfoRuleModel.setDrugInfoList(drugInfoRuleModels);
			ResResult resResult = new ResResult();
			KieSession kieSession = KieServices.Factory.get().getKieClasspathContainer().newKieSession("HL");
			kieSession.insert(orderInfoRuleModel);
			kieSession.setGlobal("resResult", resResult);
			kieSession.fireAllRules(new AgendaFilter() {
				@Override
				public boolean accept(Match match) {
					return match.getRule().getName().startsWith("ChinesePatentMedicineDuplicate");
				}
			});
			kieSession.dispose();
			assert (orderInfoRuleModel.getDrugInfoList().get(1).getBuyAmount() == 0.0);
		}

    /**
     * 中成药功效类别重复(脑心通胶囊 with 通心络胶囊)
     */
    @Test
    public void ChinesePatentMedicineDuplicate_04(){
        OrderInfoRuleModel orderInfoRuleModel = new OrderInfoRuleModel();
        PatientInfoRuleModel patientInfoRuleModel = new PatientInfoRuleModel();
        patientInfoRuleModel.setAge(65);
        patientInfoRuleModel.setPatientId("abc123");
        orderInfoRuleModel.setPatientInfoRuleModel(patientInfoRuleModel);
        orderInfoRuleModel.setCycle(80);
        List<DrugInfoRuleModel> drugInfoRuleModels = Lists.newArrayList();
        DrugInfoRuleModel drugInfoRuleModel1 = new DrugInfoRuleModel();
        drugInfoRuleModel1.setDrugId(1L);
        drugInfoRuleModel1.setCommonName("脑心通胶囊");
        drugInfoRuleModel1.setPrice(1.00);
        drugInfoRuleModel1.setBuyAmount(3.0);
        drugInfoRuleModels.add(drugInfoRuleModel1);
        DrugInfoRuleModel drugInfoRuleModel2 = new DrugInfoRuleModel();
        drugInfoRuleModel2.setDrugId(2L);
        drugInfoRuleModel2.setCommonName("血塞通片");
        drugInfoRuleModel2.setPrice(2.00);
        drugInfoRuleModel2.setBuyAmount(5.00);
        drugInfoRuleModels.add(drugInfoRuleModel2);
        orderInfoRuleModel.setDrugInfoList(drugInfoRuleModels);
        ResResult resResult = new ResResult();
        KieSession kieSession = KieServices.Factory.get().getKieClasspathContainer().newKieSession("HL");
        kieSession.insert(orderInfoRuleModel);
        kieSession.setGlobal("resResult", resResult);
        kieSession.fireAllRules(new AgendaFilter() {
            @Override
            public boolean accept(Match match) {
                return match.getRule().getName().startsWith("ChinesePatentMedicineDuplicate");
            }
        });
        kieSession.dispose();
        assert (orderInfoRuleModel.getDrugInfoList().get(1).getBuyAmount() == 0.0);
    }

    /**
     * 中成药功效类别重复(复方丹参滴丸 with 心元胶囊)"
     */
    @Test
    public void ChinesePatentMedicineDuplicate_05(){
        OrderInfoRuleModel orderInfoRuleModel = new OrderInfoRuleModel();
        PatientInfoRuleModel patientInfoRuleModel = new PatientInfoRuleModel();
        patientInfoRuleModel.setAge(65);
        patientInfoRuleModel.setPatientId("abc123");
        orderInfoRuleModel.setPatientInfoRuleModel(patientInfoRuleModel);
        orderInfoRuleModel.setCycle(80);
        List<DrugInfoRuleModel> drugInfoRuleModels = Lists.newArrayList();
        DrugInfoRuleModel drugInfoRuleModel1 = new DrugInfoRuleModel();
        drugInfoRuleModel1.setDrugId(1L);
        drugInfoRuleModel1.setCommonName("复方丹参滴丸");
        drugInfoRuleModel1.setPrice(1.00);
        drugInfoRuleModel1.setBuyAmount(3.0);
        drugInfoRuleModels.add(drugInfoRuleModel1);
        DrugInfoRuleModel drugInfoRuleModel2 = new DrugInfoRuleModel();
        drugInfoRuleModel2.setDrugId(2L);
        drugInfoRuleModel2.setCommonName("心元胶囊");
        drugInfoRuleModel2.setPrice(3.00);
        drugInfoRuleModel2.setBuyAmount(6.00);
        drugInfoRuleModels.add(drugInfoRuleModel2);
        orderInfoRuleModel.setDrugInfoList(drugInfoRuleModels);
        ResResult resResult = new ResResult();
        KieSession kieSession = KieServices.Factory.get().getKieClasspathContainer().newKieSession("HL");
        kieSession.insert(orderInfoRuleModel);
        kieSession.setGlobal("resResult", resResult);
        kieSession.fireAllRules(new AgendaFilter() {
            @Override
            public boolean accept(Match match) {
                return match.getRule().getName().startsWith("ChinesePatentMedicineDuplicate");
            }
        });
        kieSession.dispose();
        assert (orderInfoRuleModel.getDrugInfoList().get(1).getBuyAmount() == 0.0);
    }

    /**
     * 中成药功效类别重复(复方丹参滴丸 with 芪参益气滴丸)
     */
    @Test
    public void ChinesePatentMedicineDuplicate_06(){
        OrderInfoRuleModel orderInfoRuleModel = new OrderInfoRuleModel();
        PatientInfoRuleModel patientInfoRuleModel = new PatientInfoRuleModel();
        patientInfoRuleModel.setAge(65);
        patientInfoRuleModel.setPatientId("abc123");
        orderInfoRuleModel.setPatientInfoRuleModel(patientInfoRuleModel);
        orderInfoRuleModel.setCycle(80);
        List<DrugInfoRuleModel> drugInfoRuleModels = Lists.newArrayList();
        DrugInfoRuleModel drugInfoRuleModel1 = new DrugInfoRuleModel();
        drugInfoRuleModel1.setDrugId(1L);
        drugInfoRuleModel1.setCommonName("复方丹参滴丸");
        drugInfoRuleModel1.setPrice(3.00);
        drugInfoRuleModel1.setBuyAmount(3.0);
        drugInfoRuleModels.add(drugInfoRuleModel1);
        DrugInfoRuleModel drugInfoRuleModel2 = new DrugInfoRuleModel();
        drugInfoRuleModel2.setDrugId(2L);
        drugInfoRuleModel2.setCommonName("芪参益气滴丸");
        drugInfoRuleModel2.setPrice(2.00);
        drugInfoRuleModel2.setBuyAmount(6.00);
        drugInfoRuleModels.add(drugInfoRuleModel2);
        orderInfoRuleModel.setDrugInfoList(drugInfoRuleModels);
        ResResult resResult = new ResResult();
        KieSession kieSession = KieServices.Factory.get().getKieClasspathContainer().newKieSession("HL");
        kieSession.insert(orderInfoRuleModel);
        kieSession.setGlobal("resResult", resResult);
        kieSession.fireAllRules(new AgendaFilter() {
            @Override
            public boolean accept(Match match) {
                return match.getRule().getName().startsWith("ChinesePatentMedicineDuplicate");
            }
        });
        kieSession.dispose();
        assert (orderInfoRuleModel.getDrugInfoList().get(1).getBuyAmount() == 0.0);
    }

    /**
     * 中成药功效类别重复(复方丹参滴丸 with 复方丹参片)
     */
    @Test
    public void ChinesePatentMedicineDuplicate_07(){
        OrderInfoRuleModel orderInfoRuleModel = new OrderInfoRuleModel();
        PatientInfoRuleModel patientInfoRuleModel = new PatientInfoRuleModel();
        patientInfoRuleModel.setAge(65);
        patientInfoRuleModel.setPatientId("abc123");
        orderInfoRuleModel.setPatientInfoRuleModel(patientInfoRuleModel);
        orderInfoRuleModel.setCycle(80);
        List<DrugInfoRuleModel> drugInfoRuleModels = Lists.newArrayList();
        DrugInfoRuleModel drugInfoRuleModel1 = new DrugInfoRuleModel();
        drugInfoRuleModel1.setDrugId(1L);
        drugInfoRuleModel1.setCommonName("复方丹参滴丸");
        drugInfoRuleModel1.setPrice(1.00);
        drugInfoRuleModel1.setBuyAmount(3.0);
        drugInfoRuleModels.add(drugInfoRuleModel1);
        DrugInfoRuleModel drugInfoRuleModel2 = new DrugInfoRuleModel();
        drugInfoRuleModel2.setDrugId(2L);
        drugInfoRuleModel2.setCommonName("复方丹参片");
        drugInfoRuleModel2.setPrice(4.00);
        drugInfoRuleModel2.setBuyAmount(6.00);
        drugInfoRuleModels.add(drugInfoRuleModel2);
        orderInfoRuleModel.setDrugInfoList(drugInfoRuleModels);
        ResResult resResult = new ResResult();
        KieSession kieSession = KieServices.Factory.get().getKieClasspathContainer().newKieSession("HL");
        kieSession.insert(orderInfoRuleModel);
        kieSession.setGlobal("resResult", resResult);
        kieSession.fireAllRules(new AgendaFilter() {
            @Override
            public boolean accept(Match match) {
                return match.getRule().getName().startsWith("ChinesePatentMedicineDuplicate");
            }
        });
        kieSession.dispose();
        assert (orderInfoRuleModel.getDrugInfoList().get(1).getBuyAmount() == 0.0);
    }

    /**
     * 中成药功效类别重复(复方丹参滴丸 with 心可舒片)
     */
    @Test
    public void ChinesePatentMedicineDuplicate_08(){
        OrderInfoRuleModel orderInfoRuleModel = new OrderInfoRuleModel();
        PatientInfoRuleModel patientInfoRuleModel = new PatientInfoRuleModel();
        patientInfoRuleModel.setAge(65);
        patientInfoRuleModel.setPatientId("abc123");
        orderInfoRuleModel.setPatientInfoRuleModel(patientInfoRuleModel);
        orderInfoRuleModel.setCycle(80);
        List<DrugInfoRuleModel> drugInfoRuleModels = Lists.newArrayList();
        DrugInfoRuleModel drugInfoRuleModel1 = new DrugInfoRuleModel();
        drugInfoRuleModel1.setDrugId(1L);
        drugInfoRuleModel1.setCommonName("复方丹参滴丸");
        drugInfoRuleModel1.setPrice(1.00);
        drugInfoRuleModel1.setBuyAmount(3.0);
        drugInfoRuleModels.add(drugInfoRuleModel1);
        DrugInfoRuleModel drugInfoRuleModel2 = new DrugInfoRuleModel();
        drugInfoRuleModel2.setDrugId(2L);
        drugInfoRuleModel2.setCommonName("心可舒片");
        drugInfoRuleModel2.setPrice(4.00);
        drugInfoRuleModel2.setBuyAmount(4.00);
        drugInfoRuleModels.add(drugInfoRuleModel2);
        orderInfoRuleModel.setDrugInfoList(drugInfoRuleModels);
        ResResult resResult = new ResResult();
        KieSession kieSession = KieServices.Factory.get().getKieClasspathContainer().newKieSession("HL");
        kieSession.insert(orderInfoRuleModel);
        kieSession.setGlobal("resResult", resResult);
        kieSession.fireAllRules(new AgendaFilter() {
            @Override
            public boolean accept(Match match) {
                return match.getRule().getName().startsWith("ChinesePatentMedicineDuplicate");
            }
        });
        kieSession.dispose();
        assert (orderInfoRuleModel.getDrugInfoList().get(1).getBuyAmount() == 0.0);
    }

    /**
     * 中成药功效类别重复(稳心颗粒 with 参松养心胶囊)
     */
    @Test
    public void ChinesePatentMedicineDuplicate_09(){
        OrderInfoRuleModel orderInfoRuleModel = new OrderInfoRuleModel();
        PatientInfoRuleModel patientInfoRuleModel = new PatientInfoRuleModel();
        patientInfoRuleModel.setAge(65);
        patientInfoRuleModel.setPatientId("abc123");
        orderInfoRuleModel.setPatientInfoRuleModel(patientInfoRuleModel);
        orderInfoRuleModel.setCycle(80);
        List<DrugInfoRuleModel> drugInfoRuleModels = Lists.newArrayList();
        DrugInfoRuleModel drugInfoRuleModel1 = new DrugInfoRuleModel();
        drugInfoRuleModel1.setDrugId(1L);
        drugInfoRuleModel1.setCommonName("稳心颗粒");
        drugInfoRuleModel1.setPrice(1.00);
        drugInfoRuleModel1.setBuyAmount(3.0);
        drugInfoRuleModels.add(drugInfoRuleModel1);
        DrugInfoRuleModel drugInfoRuleModel2 = new DrugInfoRuleModel();
        drugInfoRuleModel2.setDrugId(2L);
        drugInfoRuleModel2.setCommonName("参松养心胶囊");
        drugInfoRuleModel2.setPrice(4.00);
        drugInfoRuleModel2.setBuyAmount(61.00);
        drugInfoRuleModels.add(drugInfoRuleModel2);
        orderInfoRuleModel.setDrugInfoList(drugInfoRuleModels);
        ResResult resResult = new ResResult();
        KieSession kieSession = KieServices.Factory.get().getKieClasspathContainer().newKieSession("HL");
        kieSession.insert(orderInfoRuleModel);
        kieSession.setGlobal("resResult", resResult);
        kieSession.fireAllRules(new AgendaFilter() {
            @Override
            public boolean accept(Match match) {
                return match.getRule().getName().startsWith("ChinesePatentMedicineDuplicate");
            }
        });
        kieSession.dispose();
        assert (orderInfoRuleModel.getDrugInfoList().get(1).getBuyAmount() == 0.0);
    }
}

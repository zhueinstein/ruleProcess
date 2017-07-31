package com.wanhuhealth.rules.batch.rest;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.wanhuhealth.rules.batch.drools.*;
import com.wanhuhealth.rules.batch.export.OutFormatMessage;
import com.wanhuhealth.rules.batch.mapper.DiseaseInfoMapper;
import com.wanhuhealth.rules.batch.mapper.DrugInformationMapper;
import com.wanhuhealth.rules.batch.mapper.OrderInfoMapper;
import com.wanhuhealth.rules.batch.model.DiseaseInfo;
import com.wanhuhealth.rules.batch.model.DrugInformation;
import com.wanhuhealth.rules.batch.model.OrderInfo;
import com.wanhuhealth.rules.utils.CountClick;
import com.wanhuhealth.rules.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by admin on 2017/7/17.
 */
@Component
public class DroolsProcessTaskUnit {
    private static Logger logger = Logger.getLogger(DroolsProcessTaskUnit.class);

    @Autowired
    OrderInfoMapper orderInfoMapper;
    @Autowired
    DiseaseInfoMapper diseaseInfoMapper;
    @Autowired
    DrugInformationMapper drugInformationMapper;
    @Autowired
    ComputeSalience computeSalience;
    DecimalFormat df   = new DecimalFormat("######0.00");
    final String sessionName = "wanHu";
    final String hlSessionName = "HL";
    static FileWriter fw = null;
    static FileWriter modelFw = null;
    static KieContainer kieContainer;
    static String direction = "d://drools_data//"; // window
//    static String direction = "/Users/zcx/drools_data/"; // mac
    static{
        try {
            File  fileD = new File(direction);
            if(fileD.exists()){
                if(!fileD.isDirectory()){
                    System.out.println("drools_data的文件已经存在");
                }
            }else{
                fileD.mkdir();
            }
            String tail = DateUtils.formatDate(new Date(), "yyyyMMddHHmm");
            File file = new File(String.format(direction + "DroolsResult_%s.txt", tail));
            fw = new FileWriter(file, true);
            File modelFile = new File(String.format(direction + "DroolsModels_%s.txt", tail));
            modelFw = new FileWriter(modelFile, true);
            KieServices kieService = KieServices.Factory.get();
            kieContainer = kieService.getKieClasspathContainer();
        }catch (Exception ex){
            System.out.println(ex);
        }
    }

    public void batchRun(String start, String end) throws Exception {
        String threadName = Thread.currentThread().getName();
        CountClick click = new CountClick();
        click.setThreadName(threadName);
        try {
            System.out.println(String.format("线程: %s is processing %s", threadName, " loading data ……"));

            List<OrderInfo> orderInfoList = mergeOrderInfo(orderInfoMapper.findBetween2Date(start, end));
            System.out.println(String.format("线程: %s is processing %s", threadName, " loading data complete!"));
            click.setTotal(orderInfoList.size()* 1.0);
            KieSession kieSession;

            for (OrderInfo orderInfo : orderInfoList) {
                click.setCount(click.getCount() + 1);
                OrderInfoRuleModel orderInfoRuleModel = buildOrderInfoRuleModel(orderInfo);
                modelFw.write(JSON.toJSONString(orderInfoRuleModel));
                modelFw.write("\n");
                modelFw.flush();
                ResResult resResult = new ResResult();
                kieSession = kieContainer.newKieSession(hlSessionName);
                kieSession.setGlobal("resResult", resResult);
                kieSession.setGlobal("computeSalience", computeSalience);
                kieSession.insert(orderInfoRuleModel);
                kieSession.fireAllRules();
                kieSession.dispose();
                kieSession = kieContainer.newKieSession(sessionName);
                kieSession.setGlobal("resResult", resResult);
                kieSession.insert(orderInfoRuleModel);
                kieSession.fireAllRules();
                kieSession.dispose();
                orderInfoRuleModel.setRuleResultList(resResult.getRuleResultList());
                handleMessage(orderInfo, orderInfoRuleModel);
                click.processing(click);
            }

        }catch (Exception e){
            logger.error(e);
        }finally {
        }
    }

    private OrderInfoRuleModel buildOrderInfoRuleModel(OrderInfo orderInfo) throws Exception{
        OrderInfoRuleModel orderInfoRuleModel = new OrderInfoRuleModel();
        orderInfoRuleModel.setCode(0);
        orderInfoRuleModel.setCycle(Integer.valueOf(orderInfo.getCycle()));
        List<DrugInformation> drugInformationList = orderInfo.getDrugInformationList();
        orderInfoRuleModel.setMessage(orderInfo.getId());
        try {
            if (null != drugInformationList && drugInformationList.size() > 0) {
                List<DrugInfoRuleModel> drugInfoRuleModels = Lists.newArrayList();
                DrugInfoRuleModel drugInfoRuleModel;
                for (DrugInformation drugInformation: drugInformationList) {
                    // 创建一个药品的数据模型
                    drugInfoRuleModel = buildDrugInfoRuleModel(drugInformation);
                    orderInfoRuleModel.setDrugInfoList(drugInfoRuleModels);
                    // 使用频次
                    drugInfoRuleModel.setFrequency(Integer.valueOf(drugInformation.getFrequency()));
                    // 购买数量
                    drugInfoRuleModel.setBuyAmount(drugInformation.getAmount());

                    drugInfoRuleModels.add(drugInfoRuleModel);
                    // 开方单次使用最大量
                    drugInfoRuleModel.setUseAmount(Double.valueOf(drugInformation.getUseAmount()));

                    //说明书单次使用最大量
                    drugInfoRuleModel.setMaximumDose(Double.valueOf(drugInformation.getMaximumDose()));
                    // 需求日使用
                    drugInfoRuleModel.setUseAmountDaily(getDailyAmount(Double.valueOf(drugInformation.getUseAmount()), drugInformation.getFrequency()));

                    //说明书日使用最大量
                    Double maximumDoseDailyInUnit = Double.valueOf(drugInformation.getMaximumDoseDaily());
                    drugInfoRuleModel.setMaximumDoseDaily(maximumDoseDailyInUnit);

                    // 说明书药品规格
                    int packageSize = 0;
                    if (null != drugInformation.getPackageSize()) {
                        packageSize = Integer.valueOf(drugInformation.getPackageSize());
                    }
                    drugInfoRuleModel.setPackageSize(packageSize);
                    // 开方疗程
                    drugInfoRuleModel.setPeriod(Integer.valueOf(orderInfo.getCycle()));

                }
            }

            // 疾病列表
            List<DiseaseInfo> diseaseInfos = orderInfo.getDiseaseInfoList();
            List<DiseaseInfoRuleModel> diseaseInfoRuleModels = Lists.newArrayList();
            List<String> diseaseIdList = Lists.newArrayList();
            DiseaseInfoRuleModel diseaseInfoRuleModel;
            for (DiseaseInfo diseaseInfo : diseaseInfos) {
                diseaseInfoRuleModel = new DiseaseInfoRuleModel();
                diseaseInfoRuleModel.setId(diseaseInfo.getId());
                diseaseInfoRuleModel.setName(diseaseInfo.getName());
                diseaseIdList.add(diseaseInfo.getId());

            }
            // 患者开方时疾病
            orderInfoRuleModel.setDiseaseInfoRuleModels(diseaseInfoRuleModels);
            orderInfoRuleModel.setDiseaseInfoList(diseaseIdList);
            // 病人信息
            PatientInfoRuleModel patientInfoRuleModel = new PatientInfoRuleModel();
            patientInfoRuleModel.setAge(StringUtils.isNotBlank(orderInfo.getPatientAge())?Integer.valueOf(orderInfo.getPatientAge()):0);
            patientInfoRuleModel.setGender("男".equals(orderInfo.getPatientSex())?1:0);
            orderInfoRuleModel.setPatientInfoRuleModel(patientInfoRuleModel);
        }catch (Exception e) {
            logger.error("开方组装数据出现异常", e);
            orderInfoRuleModel.setMessage(e.getMessage());
        }
        return orderInfoRuleModel;
    }

    /**
     * 合并订单数据
     * @param orderInfos
     * @return
     */
    private List<OrderInfo> mergeOrderInfo(List<OrderInfo> orderInfos) throws Exception{
        Hashtable<String, OrderInfo> ht = new Hashtable<String, OrderInfo>();
        String key;
        // 合并订单数据
        for (OrderInfo orderInfo : orderInfos) {
            key = String.format("%s#%s", orderInfo.getPatientId(), DateUtils.formatDate(orderInfo.getOrderDate()));
            if (ht.keySet().contains(key)) {
                // 使用药周期大的
                if (ht.get(key).getCycle().compareTo(orderInfo.getCycle()) < 0) {
                    ht.get(key).setCycle(orderInfo.getCycle());
                }
//                // 合并订单内药品数据
                ht.get(key).setDrugInformationList(mergeDrugDetail(ht.get(key).getDrugInformationList(), orderInfo.getDrugInformationList()));
                ht.get(key).setCount(ht.get(key).getCount() + 1);
            } else {
                ht.put(key, orderInfo);
            }
        }
        return Lists.newArrayList(ht.values());
    }

    /**
     * 合并药品数据
     * @param drugInformationList
     * @param addList
     * @return
     */
    private List<DrugInformation> mergeDrugDetail(List<DrugInformation> drugInformationList, List<DrugInformation> addList) throws Exception{
        Hashtable<Long, DrugInformation> ha = new Hashtable<Long, DrugInformation>();
        for (DrugInformation drugInformation : drugInformationList) {
            ha.put(drugInformation.getDrugId(), drugInformation);
        }
        Long key;
        for (DrugInformation drugInformation : addList) {
            key = drugInformation.getDrugId();
            if(ha.keySet().contains(key)){
                ha.get(key).setAmount(ha.get(key).getAmount() + drugInformation.getAmount());
                if(getDailyAmount(Double.valueOf(drugInformation.getUseAmount()), drugInformation.getFrequency()) >
                        getDailyAmount(Double.valueOf(ha.get(key).getAmount()), ha.get(key).getFrequency())){
                    ha.get(key).setUseAmount(drugInformation.getUseAmount());
                    ha.get(key).setFrequency(drugInformation.getFrequency());
                }
            }else{
                ha.put(drugInformation.getDrugId(), drugInformation);
            }
        }
        return Lists.newArrayList(ha.values());
    }

    /**
     * Json 文件写出
     * @param orderInfo
     * @param orderInfoRuleModel
     * @throws Exception
     */
    private void handleMessage(OrderInfo orderInfo, OrderInfoRuleModel orderInfoRuleModel) throws Exception {

        try {
            OutFormatMessage msg;
            List<DrugInformation> drugDetails = orderInfo.getDrugInformationList();
            if(null != drugDetails && drugDetails.size() > 0){
                for (DrugInformation drugInformation : drugDetails) {
                    msg = new OutFormatMessage();
                    msg.setOrderNO(orderInfo.getOrderNo());
                    msg.setCount(orderInfo.getCount().toString());
                    msg.setOrderDate(DateUtils.formatDate(orderInfo.getOrderDate(), "yyyy-MM-dd"));
                    msg.setHospital(orderInfo.getHospitalName());
                    msg.setPatientName(orderInfo.getPatientName());
                    msg.setWanhuCard(orderInfo.getPatientWanHuCard());
                    msg.setSex(orderInfo.getPatientSex());
                    msg.setAge(orderInfo.getPatientAge());
                    List<DiseaseInfo> diseaseInfos = orderInfo.getDiseaseInfoList();
                    String diseases = "";
                    for (DiseaseInfo diseaseInfo : diseaseInfos) {
                        diseases += diseaseInfo.getName() + "  ";
                    }
                    msg.setDiseases(diseases);
                    msg.setCycle(orderInfo.getCycle());
                    // 药品名称
                    String productName;
                    if (StringUtils.isNotBlank(drugInformation.getProductName())) {
                        productName = drugInformation.getCommonName() + "(" + drugInformation.getProductName() + ")";
                    } else {
                        productName = drugInformation.getCommonName();
                    }
                    msg.setDrugName(productName);
                    msg.setPackageSpecification(drugInformation.getPreparationUnit() + "*" + drugInformation.getPackageSize() + drugInformation.getMinimumUnit());
                    msg.setUseAmount(drugInformation.getUseAmount());
                    msg.setFrequency(drugInformation.getFrequency());
                    msg.setPrice(drugInformation.getPrice() + "");
                    msg.setNumber(drugInformation.getAmount() + "");
                    msg.setTotal(df.format(drugInformation.getAmount() * drugInformation.getPrice()));
                    String auditNumber = "";
                    for (DrugInfoRuleModel model : orderInfoRuleModel.getDrugInfoList()) {
                        if(model.getDrugId() == drugInformation.getDrugId()){
                            auditNumber = model.getBuyAmount() + "";
                        }
                    }
                    String ruleViolates = "";
                    for (RuleResult ruleResult : orderInfoRuleModel.getRuleResultList()) {
                        if(ruleResult.getDrugId() == drugInformation.getDrugId()){
                            ruleViolates += ruleResult.getRuleNo() + " ";
                        }
                    }
                    msg.setRuleViolates(ruleViolates);
                    if(StringUtils.isNotBlank(auditNumber)){
                        msg.setAuditTotal(df.format(Double.valueOf(auditNumber) * drugInformation.getPrice()));
                    }else{
                        auditNumber = drugInformation.getAmount() + "";
                        msg.setAuditTotal(msg.getTotal());
                    }

                    msg.setAuditNumber(auditNumber);
                    msg.setPatientIdCard(orderInfo.getPatientIdCard());
                    fw.write(JSON.toJSONString(msg));
                    fw.write("\n");
                    fw.flush();
                }
            }
        }catch (Exception ex){
            logger.error("出现错误", ex);
        }
    }

    /**
     * 组装药品信息
     * @param  drugInformation
     *
     * @return
     */
    private DrugInfoRuleModel buildDrugInfoRuleModel(DrugInformation drugInformation) {
        DrugInfoRuleModel drugInfoRuleModel = new DrugInfoRuleModel();
        drugInfoRuleModel.setDrugId(drugInformation.getDrugId());
        drugInfoRuleModel.setSmallClass(drugInformation.getSmallClass());
        drugInfoRuleModel.setLargeClass(drugInformation.getLargeClass());
        drugInfoRuleModel.setMediumClass(drugInformation.getMediumClass());
        // 根据韩旭要求，屏蔽这几类药理学分类
        if(drugInformation.getPharmacologyClass() != null){
            if(!Lists.newArrayList("传统固定复方降压制剂","抗血小板药","心肌营养类").contains(drugInformation.getPharmacologyClass())){
                drugInfoRuleModel.setPharmacologyClass(drugInformation.getPharmacologyClass());
            }
        }
        drugInfoRuleModel.setPrice(drugInformation.getPrice());
        //药品适应症
        List<String> indicationList = Lists.newArrayList();
        for (DiseaseInfo diseaseInfo : drugInformation.getIndicationList()) {
            indicationList.add(diseaseInfo.getId());
        }
        drugInfoRuleModel.setIndicationList(indicationList);
        // 药品名称
        String productName;
        if (StringUtils.isNotBlank(drugInformation.getProductName())) {
            productName = drugInformation.getCommonName() + "(" + drugInformation.getProductName() + ")";
        } else {
            productName = drugInformation.getCommonName();
        }
        drugInfoRuleModel.setProductName(productName);
        drugInfoRuleModel.setCommonName(drugInformation.getCommonName());

        //日用量的计算
        drugInfoRuleModel.setDailyAmount(getDailyAmount(Double.valueOf(drugInformation.getUseAmount()), drugInformation.getFrequency()));

        drugInfoRuleModel.setFirstCategory(drugInformation.getFirstCategory());
        drugInfoRuleModel.setSecCategory(drugInformation.getSecCategory());
        drugInfoRuleModel.setThirCategory(drugInformation.getThirCategory());
        if(StringUtils.isNotBlank(drugInformation.getOtherCategory())) {
            drugInfoRuleModel.setOtherCategory(Arrays.asList(drugInformation.getOtherCategory().split("，")));
        }
        if(StringUtils.isNotBlank(drugInformation.getMainIngredients())){
            drugInfoRuleModel.setMainIngredients(Arrays.asList(drugInformation.getMainIngredients().split("，")));
        }
        drugInfoRuleModel.setDiseases(Arrays.asList(drugInformation.getDiseases().split("s,")));
        return drugInfoRuleModel;
    }

    /**
     * 计算日用量
     * @param useAmount
     * @param frequency
     * @return
     */

    public Double getDailyAmount(Double useAmount, String frequency) {
        if(useAmount == null || frequency == null){
            return 0.0;
        }
        if ("1".equals(frequency) || "5".equals(frequency)) {//每日或每晚
            return useAmount * 1.00;
        } else if ("2".equals(frequency)) {//每日两次
            return useAmount * 2.00;
        } else if ("3".equals(frequency)) {//每日三次
            return useAmount * 3.00;
        } else if ("4".equals(frequency)) {//每日四次
            return useAmount * 4.00;
        }else if("6".equals(frequency)){
           return  (useAmount / 7) * 1.00;
        }
        else
            return 0.0;
    }

}

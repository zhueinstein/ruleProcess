package com.wanhuhealth.rules.batch.model;

import java.util.Date;
import java.util.List;

/**
 * Created by admin on 2017/7/19.
 */
public class OrderInfo {
    private static final long serialVersionUID = 1L;

    private String id;
    // 处方编号
    private String orderNo;
    // 开具机构
    private String  hospitalName;

    // 登记时间
    private Date orderDate;

    // 处方疗程
    private String cycle;
    //处方用药信息
    private List<DrugInformation> drugInformationList;

    //开方疾病诊断
    private List<DiseaseInfo> diseaseInfoList;

    // 患者姓名
    private String patientId;
    // 患者姓名
    private String patientName;
    // 患者性别
    private String patientSex;
    // 患者年龄
    private String patientAge;
    // 患者身份证号
    private String patientIdCard;
    // 万户卡号
    private String patientWanHuCard;
    //违规规则代号
    private String ruleViolates;
    //审核后数量
    private String auditNumber;
    //审核后总价
    private String auditTotal;
    // 合并处方数量
    private Integer count = 1;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public List<DrugInformation> getDrugInformationList() {
        return drugInformationList;
    }

    public void setDrugInformationList(List<DrugInformation> drugInformationList) {
        this.drugInformationList = drugInformationList;
    }

    public List<DiseaseInfo> getDiseaseInfoList() {
        return diseaseInfoList;
    }

    public void setDiseaseInfoList(List<DiseaseInfo> diseaseInfoList) {
        this.diseaseInfoList = diseaseInfoList;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientSex() {
        return patientSex;
    }

    public void setPatientSex(String patientSex) {
        this.patientSex = patientSex;
    }

    public String getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(String patientAge) {
        this.patientAge = patientAge;
    }

    public String getRuleViolates() {
        return ruleViolates;
    }

    public void setRuleViolates(String ruleViolates) {
        this.ruleViolates = ruleViolates;
    }

    public String getAuditNumber() {
        return auditNumber;
    }

    public void setAuditNumber(String auditNumber) {
        this.auditNumber = auditNumber;
    }

    public String getAuditTotal() {
        return auditTotal;
    }

    public void setAuditTotal(String auditTotal) {
        this.auditTotal = auditTotal;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getPatientWanHuCard() {
        return patientWanHuCard;
    }

    public void setPatientWanHuCard(String patientWanHuCard) {
        this.patientWanHuCard = patientWanHuCard;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientIdCard() {
        return patientIdCard;
    }

    public void setPatientIdCard(String patientIdCard) {
        this.patientIdCard = patientIdCard;
    }
}

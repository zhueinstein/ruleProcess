package com.wanhuhealth.rules.batch.excel;

import java.util.Hashtable;

/**
 * Created by admin on 2017/7/17.
 */
public class OutFormatMessage {
 private String orderNO;    //   订单号
 private String count; //合并前订单数
 private String orderDate; //日期
 private String hospital; //社区医院
 private String patientName; //患者姓名
 private String wanhuCard; //万户卡号
 private String patientIdCard; // 身份证号
 private String sex; //性别
 private String age; //年龄
 private String diseases; //疾病
 private String cycle; //用药周期
 private String drugName; //药品名称
 private String packageSpecification; //规格
 private String useAmount; //单次用量
 private String frequency; //频次
 private String price; //单价
 private String number; //数量
 private String total; //总价
 private String orderTotal; // 订单总价
 private String ruleViolates; //违规规则代号w
 private String auditNumber; //审核后数量
 private String auditTotal; //审核后总价
 private String orderAuditTotal; // 审核后订单总价
 private Boolean legal = true; // 是否违规 true 合规 false 违规
 private Boolean show = false;
 private Hashtable<String, Double> complexSave = new Hashtable<String, Double>(); // 组合扣费详情
    public String getOrderNO() {
        return orderNO;
    }

    public void setOrderNO(String orderNO) {
        this.orderNO = orderNO;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getWanhuCard() {
        return wanhuCard;
    }

    public void setWanhuCard(String wanhuCard) {
        this.wanhuCard = wanhuCard;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDiseases() {
        return diseases;
    }

    public void setDiseases(String diseases) {
        this.diseases = diseases;
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getPackageSpecification() {
        return packageSpecification;
    }

    public void setPackageSpecification(String packageSpecification) {
        this.packageSpecification = packageSpecification;
    }

    public String getUseAmount() {
        return useAmount;
    }

    public void setUseAmount(String useAmount) {
        this.useAmount = useAmount;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
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

    public String getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(String orderTotal) {
        this.orderTotal = orderTotal;
    }

    public String getOrderAuditTotal() {
        return orderAuditTotal;
    }

    public void setOrderAuditTotal(String orderAuditTotal) {
        this.orderAuditTotal = orderAuditTotal;
    }

    public String getPatientIdCard() {
        return patientIdCard;
    }

    public void setPatientIdCard(String patientIdCard) {
        this.patientIdCard = patientIdCard;
    }

    public Hashtable<String, Double> getComplexSave() {
        return complexSave;
    }

    public void setComplexSave(Hashtable<String, Double> complexSave) {
        this.complexSave = complexSave;
    }

    public Boolean getLegal() {
        return legal;
    }

    public void setLegal(Boolean legal) {
        this.legal = legal;
    }

    public Boolean getShow() {
        return show;
    }

    public void setShow(Boolean show) {
        this.show = show;
    }
}

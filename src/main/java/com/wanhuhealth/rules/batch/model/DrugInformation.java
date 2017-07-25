package com.wanhuhealth.rules.batch.model;

import java.util.List;

/**
 * Created by admin on 2017/7/21.
 */
public class DrugInformation {
    private static final long serialVersionUID = 1L;
    private Long drugId;
    private Long baseDrugId;
    /* 价格 */
    private Double price;
    /* 数量 */
    private Double amount;
    /* 用量 */
    private String useAmount;
    /* 频率 */
    private String frequency;

    // 通用名称
    private String commonName;
    //大类（中药、西药）-关联字典表(type=medi_large_class )
    private String largeClass;

    //中类---关联字典表(type=medi_medium_class )
    private String mediumClass;

    //小类---关联字典表(type=medi_small_class )
    private String smallClass;

    //药理学分类 ----关联字典表（type= pharmacology）
    private String pharmacologyClass;

    //产品名
    private String productName;
    //产品编码
	private String productCode;

    //包装规格量
    private String packageSize;
    //制剂规格
    private String preparationUnit;
    //包装单位package_unit_type
    private String packageUnit;
    //最小使用单位minimum_unit_type
    private String minimumUnit;
    //单次最大剂量
    private String maximumDose;
    //日最大剂量
    private String maximumDoseDaily;

    //适应症（万户表疾病）
    private List<DiseaseInfo> indicationList;

    //推荐用量
    private String recommendUseAmount;
    //推荐频率
    private String recommendFrequency;

    // 第一分类(医学部提供)
    private String firstCategory;
    // 第二分类(医学部提供)
    private String secCategory;
    // 第三分类(医学部提供)
    private String thirCategory;
    // 其他分类 (4 - 6)(医学部提供)
    private String otherCategory;
    // 主要成分(医学部提供)
    private String mainIngredients;
    // 适应症(医学部提供)
    private String diseases;

    public Long getDrugId() {
        return drugId;
    }

    public void setDrugId(Long drugId) {
        this.drugId = drugId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
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

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getLargeClass() {
        return largeClass;
    }

    public void setLargeClass(String largeClass) {
        this.largeClass = largeClass;
    }

    public String getMediumClass() {
        return mediumClass;
    }

    public void setMediumClass(String mediumClass) {
        this.mediumClass = mediumClass;
    }

    public String getSmallClass() {
        return smallClass;
    }

    public void setSmallClass(String smallClass) {
        this.smallClass = smallClass;
    }

    public String getPharmacologyClass() {
        return pharmacologyClass;
    }

    public void setPharmacologyClass(String pharmacologyClass) {
        this.pharmacologyClass = pharmacologyClass;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getPackageSize() {
        return packageSize;
    }

    public void setPackageSize(String packageSize) {
        this.packageSize = packageSize;
    }

    public String getMaximumDose() {
        return maximumDose;
    }

    public void setMaximumDose(String maximumDose) {
        this.maximumDose = maximumDose;
    }

    public String getMaximumDoseDaily() {
        return maximumDoseDaily;
    }

    public void setMaximumDoseDaily(String maximumDoseDaily) {
        this.maximumDoseDaily = maximumDoseDaily;
    }

    public List<DiseaseInfo> getIndicationList() {
        return indicationList;
    }

    public void setIndicationList(List<DiseaseInfo> indicationList) {
        this.indicationList = indicationList;
    }

    public String getRecommendUseAmount() {
        return recommendUseAmount;
    }

    public void setRecommendUseAmount(String recommendUseAmount) {
        this.recommendUseAmount = recommendUseAmount;
    }

    public String getRecommendFrequency() {
        return recommendFrequency;
    }

    public void setRecommendFrequency(String recommendFrequency) {
        this.recommendFrequency = recommendFrequency;
    }

    public String getFirstCategory() {
        return firstCategory;
    }

    public void setFirstCategory(String firstCategory) {
        this.firstCategory = firstCategory;
    }

    public String getSecCategory() {
        return secCategory;
    }

    public void setSecCategory(String secCategory) {
        this.secCategory = secCategory;
    }

    public String getThirCategory() {
        return thirCategory;
    }

    public void setThirCategory(String thirCategory) {
        this.thirCategory = thirCategory;
    }

    public String getOtherCategory() {
        return otherCategory;
    }

    public void setOtherCategory(String otherCategory) {
        this.otherCategory = otherCategory;
    }

    public String getMainIngredients() {
        return mainIngredients;
    }

    public void setMainIngredients(String mainIngredients) {
        this.mainIngredients = mainIngredients;
    }

    public String getDiseases() {
        return diseases;
    }

    public void setDiseases(String diseases) {
        this.diseases = diseases;
    }

    public String getPreparationUnit() {
        return preparationUnit;
    }

    public void setPreparationUnit(String preparationUnit) {
        this.preparationUnit = preparationUnit;
    }

    public String getPackageUnit() {
        return packageUnit;
    }

    public void setPackageUnit(String packageUnit) {
        this.packageUnit = packageUnit;
    }

    public String getMinimumUnit() {
        return minimumUnit;
    }

    public void setMinimumUnit(String minimumUnit) {
        this.minimumUnit = minimumUnit;
    }

    public Long getBaseDrugId() {
        return baseDrugId;
    }

    public void setBaseDrugId(Long baseDrugId) {
        this.baseDrugId = baseDrugId;
    }
}

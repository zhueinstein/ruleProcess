package com.wanhuhealth.rules.drools;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by admin on 2017/1/10.
 * 药品: { ID, 购买量, 单次用量, 频次, 规格, 剂型, 适应症 [ ID ], 推荐用量, 推荐单次剂量, 推荐频次, 单次使用最大量, 日使用最大量,
 * 单次最大剂量, 日最大剂量, 通用名, 商品名, 药品分类-大类, 药品分类-中类, 药品分类-小类, 药理学分类, 历史药品余量 }
 */
public class DrugInfoRuleModel{
	private Long drugId; // ID
	private Integer frequency;	// 频次
	private Integer packageSize;	//包装规格量
	private List<String> indicationList = Lists.newArrayList(); //  适应症
	private Integer recommendAmount;	//推荐用量
	private Double recommendDose;		//推荐剂量
	private Double recommendFrequency;	//推荐频率
	private Double maximumDose;		//单次最大剂量
	private Double maximumDoseDaily;	//日最大剂量
	private String commonName;		// 通用名
	private String productName;	//产品名
	private String largeClass;	//大类（中药、西药）-关联字典表(type=medi_large_class )
	private String mediumClass;	//中类---关联字典表(type=medi_medium_class )
	private String smallClass;	//小类---关联字典表(type=medi_small_class )
	private String pharmacologyClass;	//药理学分类 ----关联字典表（type= pharmacology）
	private Double dailyAmount; //日用量（需求用量）
	private Double buyAmount; 	// 购买量
	private Double useAmount; 	// 单次用量
	private Double maxBuyAmountOfRequired; // 最大用药需求购买量
	private Double maxBuyAmountOfIntruduction; // 最大说明书购买量
	private Integer period;
	private Double useAmountDaily; // 需求日使用量
	private Double price;
	private Double maxAmount;
	private boolean omit = false;
	private String firstCategory;
	private String secCategory;
	private String thirCategory;
	private List<String> otherCategory = Lists.newArrayList();
	private List<String> mainIngredients = Lists.newArrayList();
	private List<String> diseases = Lists.newArrayList(); //


	public Long getDrugId() {
		return drugId;
	}

	public void setDrugId(Long drugId) {
		this.drugId = drugId;
	}

	public Integer getFrequency() {
		return frequency;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}

	public Integer getPackageSize() {
		return packageSize;
	}

	public void setPackageSize(Integer packageSize) {
		this.packageSize = packageSize;
	}

	public List<String> getIndicationList() {
		return indicationList;
	}

	public void setIndicationList(List<String> indicationList) {
		this.indicationList = indicationList;
	}

	public Integer getRecommendAmount() {
		return recommendAmount;
	}

	public void setRecommendAmount(Integer recommendAmount) {
		this.recommendAmount = recommendAmount;
	}

	public Double getRecommendDose() {
		return recommendDose;
	}

	public void setRecommendDose(Double recommendDose) {
		this.recommendDose = recommendDose;
	}

	public Double getRecommendFrequency() {
		return recommendFrequency;
	}

	public void setRecommendFrequency(Double recommendFrequency) {
		this.recommendFrequency = recommendFrequency;
	}

	public Double getMaximumDose() {
		return maximumDose;
	}

	public void setMaximumDose(Double maximumDose) {
		this.maximumDose = maximumDose;
	}

	public Double getMaximumDoseDaily() {
		return maximumDoseDaily;
	}

	public void setMaximumDoseDaily(Double maximumDoseDaily) {
		this.maximumDoseDaily = maximumDoseDaily;
	}

	public String getCommonName() {
		return commonName;
	}

	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
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

	public Double getDailyAmount() {
		return dailyAmount;
	}

	public void setDailyAmount(Double dailyAmount) {
		this.dailyAmount = dailyAmount;
	}

	public Double getBuyAmount() {
		return buyAmount;
	}

	public void setBuyAmount(Double buyAmount) {
		this.buyAmount = buyAmount;
	}

	public Double getUseAmount() {
		return useAmount;
	}

	public void setUseAmount(Double useAmount) {
		this.useAmount = useAmount;
	}

	public Double getMaxBuyAmountOfRequired() {
		return maxBuyAmountOfRequired;
	}

	public void setMaxBuyAmountOfRequired(Double maxBuyAmountOfRequired) {
		this.maxBuyAmountOfRequired = maxBuyAmountOfRequired;
	}

	public Double getMaxBuyAmountOfIntruduction() {
		return maxBuyAmountOfIntruduction;
	}

	public void setMaxBuyAmountOfIntruduction(Double maxBuyAmountOfIntruduction) {
		this.maxBuyAmountOfIntruduction = maxBuyAmountOfIntruduction;
	}

	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	public Double getUseAmountDaily() {
		return useAmountDaily;
	}

	public void setUseAmountDaily(Double useAmountDaily) {
		this.useAmountDaily = useAmountDaily;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(Double maxAmount) {
		this.maxAmount = maxAmount;
	}

	public boolean isOmit() {
		return omit;
	}

	public void setOmit(boolean omit) {
		this.omit = omit;
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

	public List<String> getOtherCategory() {
		return otherCategory;
	}

	public void setOtherCategory(List<String> otherCategory) {
		this.otherCategory = otherCategory;
	}

	public List<String> getMainIngredients() {
		return mainIngredients;
	}

	public void setMainIngredients(List<String> mainIngredients) {
		this.mainIngredients = mainIngredients;
	}

	public List<String> getDiseases() {
		return diseases;
	}

	public void setDiseases(List<String> diseases) {
		this.diseases = diseases;
	}
}
